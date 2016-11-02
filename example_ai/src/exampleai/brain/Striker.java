package exampleai.brain;


import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PositionLib;
import essentials.communication.Action;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.PlayMode;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;

/**
 * Example striker AI
 * Simply runs to the ball and tries to kick into the middle of the goal.
 * @author Hannes Eilers
 *
 */
public class Striker extends AiTemplate {

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
        	if( aWorldData.getBallPosition() != null ){
        		vBotAction = MoveLib.runTo( aWorldData.getBallPosition()  );
        	}
        	else{
        		vBotAction = MoveLib.runTo( aWorldData.getFieldCenter() );
        	}
        	// --------------- KICK OFF END ---------------
        	
        }
        // No kick off
        else{

            // --------------- START AI -------------------
            
        	// check if ball is available
            if( aWorldData.getBallPosition() != null ){
            	
            	// get ball position
            	BallPosition ballPos = aWorldData.getBallPosition();
            	
            	// check if bot can kick
            	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange ) ){                 
            		// kick
            		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( aWorldData, mSelf.getTeam() );
            		vBotAction = KickLib.kickTo( goalMid ); 
            		
            	} else {
            		// move to ball
            		vBotAction = MoveLib.runTo( ballPos );
            	}	 
            	
            }
            
            // ---------------- END AI --------------------
        
        }
        
        return vBotAction;
    	
    }
	

}
