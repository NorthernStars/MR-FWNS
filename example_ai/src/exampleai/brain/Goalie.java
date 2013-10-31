package exampleai.brain;


import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PositionLib;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;


// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

public class Goalie extends Thread implements ArtificialIntelligence {

    BotInformation mSelf = null;
    RawWorldData mWorldState = null;
    Action mAction = null;
    
    boolean mNeedNewAction = true;    
    boolean mIsRunning = false;
    
    @Override
    public void initializeAI( BotInformation aOneSelf ) {
        
        mSelf = aOneSelf;     
        
    }

    @Override
    public void startAI() {
        
        mIsRunning = true;
        start();
        
    }
    
    @Override
    public void pauseAI() {
        
        mIsRunning = false;   
        
    }
    
    public void run(){
        
        RawWorldData vWorldState = null;
        Action vBotAction = null;
        
        while ( mIsRunning ){

            try {             
                if( mNeedNewAction && mWorldState != null  ){
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }

                    // --------------- START AI -------------------
                    
                    if( vWorldState.getBallPosition() != null ){
                    	
                    	// get ball position
                    	BallPosition ballPos = vWorldState.getBallPosition();
                    	ReferencePoint GoalMid = PositionLib.getMiddleOfOwnGoal(vWorldState, mSelf.getTeam());
                    	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange ) ){                 
                    		// kick
                    		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( vWorldState, Teams.Blue );
                    		vBotAction = KickLib.kickTo( goalMid );                    		
                    	} else if(PositionLib.isBallInRangeOfRefPoint(ballPos, GoalMid, 50)){
                    		// move to ball
                    		vBotAction = MoveLib.runTo( ballPos );
                    	} 
                    	else {
                    		if(GoalMid.getDistanceToPoint() > 10){
                    			vBotAction = MoveLib.runTo(GoalMid);
                    		}
                    		else{
                    			vBotAction = null;
                    		}
                    	}
                    	
                    }
                    
                    // ---------------- END AI --------------------
                    
                    synchronized ( this ) {
                        mAction = vBotAction;
                        mNeedNewAction = false;
                    }                  
                }
                Thread.sleep( 1 );                
            } catch ( Exception e ) {
                e.printStackTrace();
            }            
            
        }
        
    }

    
    @Override
    public synchronized Action getAction() {

        synchronized ( this ) {
            if( mAction != null)
                return mAction;
        }
        return (Action) Movement.NO_MOVEMENT;
        
    }

    @Override
    public void putWorldState(RawWorldData aWorldState) {

        synchronized ( this ) {
            mWorldState = aWorldState;
            mNeedNewAction = true;
        }
        
    }

    @Override
    public void disposeAI() {
        
        mIsRunning = false;
        
    }
    
    @Override
    public boolean isRunning() {

        return mIsRunning;
        
    }

	@Override
	public boolean wantRestart() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public void executeCommand( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

}
