package exampleai.brain;


import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PositionLib;
import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.PlayMode;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;


/**
 * Simple goalkeeper example ai.
 * @author Hannes Eilers
 *
 */
public class GoalKeeper extends AiTemplate {
	
	@Override
	public Action startWorking( RawWorldData aWorldData ){
    	
		System.out.println("Ha");
		
		Action vBotAction = null;
                   
        // Getting current play mode
        PlayMode vPlayMode = aWorldData.getPlayMode();
        
        // Check for kick off
        if( vPlayMode == PlayMode.KickOff
        		|| (vPlayMode == PlayMode.KickOffYellow && mSelf.getTeam() == Teams.Yellow)
        		|| (vPlayMode == PlayMode.KickOffBlue && mSelf.getTeam() == Teams.Blue) ){
        	
        	// --------------- KICK OFF ---------------
        	vBotAction = MoveLib.runTo( PositionLib.getMiddleOfOwnGoal(aWorldData, mSelf.getTeam()) );                	
        	// --------------- KICK OFF END ---------------
        	
        }
        // No kick off
        else{

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
            	
            	// get ball position and middle of goal
            	BallPosition ballPos = aWorldData.getBallPosition();
            	ReferencePoint ownGoalMiddle = PositionLib.getMiddleOfOwnGoal(aWorldData, mSelf.getTeam());
            	
            	// check if bot can kick
            	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange )){                 
            		// kick to enemies goal
            		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( aWorldData, mSelf.getTeam() );
            		vBotAction = KickLib.kickTo( goalMid );  
            	
            	// check if ball is within defense range around own goal
            	} else if(PositionLib.isBallInRangeOfRefPoint(ballPos, ownGoalMiddle, defenseRange)){
            		// move to ball
            		vBotAction = MoveLib.runTo( ballPos );
            	} 
            	
            	// if can not kick or ball outside defense range
            	else {
            		if(ownGoalMiddle.getDistanceToPoint() > positionThreshold){
            			vBotAction = MoveLib.runTo(ownGoalMiddle);
            		}
            		else{
            			vBotAction = (Action) Movement.NO_MOVEMENT ;
            		}
            	}
            	
            }
            
            // ---------------- END AI --------------------
            
        }
                    
        return vBotAction;            
        
    }

}
