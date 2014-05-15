package exampleai.brain;


import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PositionLib;
import essentials.communication.Action;
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
public class GoalKeeper extends Thread implements ArtificialIntelligence {

    BotInformation mSelf = null;
    RawWorldData mWorldState = null;
    Action mAction = null;
    
    boolean mNeedNewAction = true;    
    boolean mIsStarted = false;
    boolean mIsPaused = false;
    
    @Override
    public void initializeAI( BotInformation aOneSelf ) {        
        mSelf = aOneSelf; 
        mIsStarted = true;
        start();        
    }

    @Override
    public void resumeAI() {        
        mIsPaused = false;        
    }
    
    @Override
    public void suspendAI() {        
        mIsPaused = true;        
    }
    
    public void run(){
        
        RawWorldData vWorldState = null;
        Action vBotAction = null;
        
        while ( mIsStarted ){
            
            while( mIsPaused ){ try { this.wait( 10 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }

            try {            
                if( mNeedNewAction && mWorldState != null  ){
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }
                    
                    // Getting current play mode
                    PlayMode vPlayMode = mWorldState.getPlayMode();
                    
                    // Check for kick off
                    if( vPlayMode == PlayMode.KickOff
                    		|| (vPlayMode == PlayMode.KickOffYellow && mSelf.getTeam() == Teams.Yellow)
                    		|| (vPlayMode == PlayMode.KickOffBlue && mSelf.getTeam() == Teams.Blue) ){
                    	
                    	// --------------- KICK OFF ---------------
                    	vBotAction = MoveLib.runTo( PositionLib.getMiddleOfOwnGoal(vWorldState, mSelf.getTeam()) );                	
                    	// --------------- KICK OFF END ---------------
                    	
                    }
                    // No kick off
                    else{

	                    // --------------- START AI -------------------
	                    
                    	// get width of field
                    	double fieldWidth = PositionLib.getDistanceBetweenTwoRefPoints(
                    			PositionLib.getMiddleOfGoal(vWorldState, mSelf.getTeam()),
                    			PositionLib.getMiddleOfOwnGoal(vWorldState, mSelf.getTeam()));
                    	
                    	// set distances
                    	double defenseRange = fieldWidth * 0.2;			// 20% defense range
                    	double positionThreshold = fieldWidth * 0.05;	// 5% position tolerance
                    	
                    	
                    	// check if ball is available
	                    if( vWorldState.getBallPosition() != null ){
	                    	
	                    	// get ball position and middle of goal
	                    	BallPosition ballPos = vWorldState.getBallPosition();
	                    	ReferencePoint ownGoalMiddle = PositionLib.getMiddleOfOwnGoal(vWorldState, mSelf.getTeam());
	                    	
	                    	// check if bot can kick
	                    	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange )){                 
	                    		// kick to enemies goal
	                    		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( vWorldState, mSelf.getTeam() );
	                    		vBotAction = KickLib.kickTo( goalMid );  
	                    	
	                    	// check if ball is withing defense range around own goal
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
            if( mWorldState.getReferencePoints() != null || !mWorldState.getReferencePoints().isEmpty() ){            
            	mNeedNewAction = true;
            } else {
            	mNeedNewAction = false;
            }
        }        
    }

    @Override
    public void disposeAI() {        
        mIsStarted = false;
        mIsPaused = false;        
    }
    
    @Override
    public boolean isRunning() {
        return mIsStarted && !mIsPaused;        
    }

	@Override
	public boolean wantRestart() {
		return false;
	}

    @Override
    public void executeCommand( String arg0 ) {
    }

}
