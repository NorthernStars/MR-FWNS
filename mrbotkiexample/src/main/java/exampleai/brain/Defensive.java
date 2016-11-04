package exampleai.brain;




import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PlayersLib;
import mrlib.core.PositionLib;
import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.PlayMode;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;


// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

public class Defensive extends AiTemplate {

	@Override
	public Action startWorking( RawWorldData aWorldData ){
    	
		Action vBotAction = null;
                    
        // Getting current play mode
        PlayMode vPlayMode = aWorldData.getPlayMode();
        
        // Check for kick off
        if( vPlayMode == PlayMode.KickOff
        		|| (vPlayMode == PlayMode.KickOffYellow && mSelf.getTeam() == Teams.Yellow)
        		|| (vPlayMode == PlayMode.KickOffBlue && mSelf.getTeam() == Teams.Blue) ){
        	
        	// --------------- KICK OFF ---------------
        	vBotAction = MoveLib.runTo( getDMFposition(aWorldData, mSelf.getTeam()) );                	
        	// --------------- KICK OFF END ---------------
        	
        } else {
        
            // --------------- START AI -------------------
            
            // get width of field
        	double fieldWidth = PositionLib.getDistanceBetweenTwoRefPoints(
        			PositionLib.getMiddleOfGoal(aWorldData, mSelf.getTeam()),
        			PositionLib.getMiddleOfOwnGoal(aWorldData, mSelf.getTeam()));
        	
        	// set distances
        	double defenseRange = fieldWidth * 0.2;			// 20% defense range
        	double positionThreshold = fieldWidth * 0.05;	// 5% position tolerance
            
        	// check if ball is available
            if( aWorldData.getBallPosition() != null ){
            	
            	// get ball and dmf position	                    	
            	BallPosition ballPos = aWorldData.getBallPosition();
            	ReferencePoint dmfPos = getDMFposition(aWorldData, mSelf.getTeam());
            	
            	// check if bot can kick and is nearest team mate to ball
            	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange )
            			&& PlayersLib.amINearestToBall(aWorldData, ballPos, mSelf) ){
            		
            		/*
            		 * Get nearest team mate.
            		 * First try to get one without enemy in twice of kick range around.
            		 * If that fails, simply get the nearest team mate
            		 */
            		FellowPlayer nearestTeamMate = PlayersLib.getNearestMateWithoutEnemyAround(aWorldData, mSelf);
            		if( nearestTeamMate == null ){
            			nearestTeamMate = PlayersLib.getNearestMate(aWorldData, mSelf);
            		}
            		
            		// check if enemy is around and in twice of kick range and it is possible to kick to team mate
            		if( PlayersLib.isEnemyAround(aWorldData, mSelf) && nearestTeamMate != null ){	                    			
            			// kick to nearest team mate
            			vBotAction = KickLib.kickTo(nearestTeamMate);
                	}
            		// kick to enemies goal middle
            		else{
                		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( aWorldData, mSelf.getTeam() );
                		vBotAction = KickLib.kickTo( goalMid );  
            		} 	                        	
            	
            	}
            	
            	// can not kick and ball is inside defense range around dmf position
            	else if( PositionLib.isBallInRangeOfRefPoint(ballPos, dmfPos, defenseRange) ){
            		// move to ball
            		vBotAction = MoveLib.runTo( ballPos );
            	} 
            	
            	// ball not in defense range and not kickable
            	else {
            		// move to dmf position
            		if(dmfPos.getDistanceToPoint() > positionThreshold){
            			vBotAction = MoveLib.runTo(dmfPos);
            		}
            		else{
            			vBotAction = (Action) Movement.NO_MOVEMENT;
            		}
            	}
        	
            }  
            
            // ---------------- END AI --------------------
	        
        }
        
        return vBotAction;
        
    }
    
    /**
     * Calculates position for bot in the middle of the defense field side.
     * @param aWorldData	{@link WorldData}
     * @param aTeam			{@link Teams} own team
     * @return				{@link ReferencePoint} of dmf position
     */
    public static ReferencePoint getDMFposition( RawWorldData aWorldData, Teams aTeam ){
    	ReferencePoint penaltyTop;
    	ReferencePoint penaltyBottom;
    	ReferencePoint penaltyMid;
    	
    	// get penalty top and bottom of own goal
    	if ( aTeam == Teams.Blue){
    		penaltyTop = aWorldData.getBluePenaltyAreaFrontTop();
    		penaltyBottom = aWorldData.getBluePenaltyAreaFrontBottom();
    	}
    	else{
    		penaltyTop = aWorldData.getYellowPenaltyAreaFrontTop();
    		penaltyBottom = aWorldData.getYellowPenaltyAreaFrontBottom();
    	}
    	
    	// get position in middle between penalty top and bottom
    	penaltyMid = PositionLib.getMiddleOfTwoReferencePoints(penaltyTop, penaltyBottom);
    	
    	// set dmf position to middle of penaltyMid and field center
    	ReferencePoint dmfPoint = PositionLib.getMiddleOfTwoReferencePoints(penaltyMid, aWorldData.getFieldCenter());
    	    	
    	return dmfPoint;
    }

}
