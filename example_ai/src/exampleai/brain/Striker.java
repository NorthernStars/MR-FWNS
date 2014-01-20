package exampleai.brain;


import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PlayersLib;
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



// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

public class Striker extends Thread implements ArtificialIntelligence {

    BotInformation mSelf = null;
    RawWorldData mWorldState = null;
    Action mAction = null;
    
    boolean mNeedNewAction = true;    
    boolean mIsStarted = false;
    boolean mIsPaused = false;
	private boolean mRestart = false;
    
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
                    PlayMode mPlayMode = mWorldState.getPlayMode();
                    if( mPlayMode == PlayMode.KickOff
                    		|| (mPlayMode == PlayMode.KickOffYellow && mSelf.getTeam() == Teams.Yellow)
                    		|| (mPlayMode == PlayMode.KickOffBlue && mSelf.getTeam() == Teams.Blue) ){
                    	
                    	// --------------- KICK OFF ---------------
                    	vBotAction = MoveLib.runTo( vWorldState.getBallPosition()  );                   	
                    	// --------------- KICK OFF END ---------------
                    	
                    }
                    else{

	                    // --------------- START AI -------------------
	                    
	                    if( vWorldState.getBallPosition() != null ){
	                    	if(PositionLib.getDistanceBetweenTwoRefPoints(vWorldState.getFieldCenter(), new ReferencePoint(0,0,true))< 10 && !PlayersLib.amINearestToBall(vWorldState, vWorldState.getBallPosition(), mSelf)){
	                    		mSelf.setAIClassname("exampleai.brain.DefensiveMidfielder");
	                    		mRestart = true;
	                    		vBotAction = (Action) Movement.NO_MOVEMENT;
	                    	}
	                    	// get ball position
	                    	BallPosition ballPos = vWorldState.getBallPosition();
	                    	
	                    	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange ) ){                 
	                    		// kick
	                    		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( vWorldState, mSelf.getTeam() );
	                    		vBotAction = KickLib.kickTo( goalMid );                    		
	                    	} else {
	                    		// move to ball
	                    		vBotAction = MoveLib.runTo( ballPos );
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
            mNeedNewAction = true;
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
		// TODO Auto-generated method stub
		return mRestart ;
	}

    @Override
    public void executeCommand( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

}
