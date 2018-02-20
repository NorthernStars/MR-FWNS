package exampleai.brain;

import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.communication.action_server2008.Movement;
import essentials.communication.Position;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;

public class MazeRunnerTemplate extends Thread implements ArtificialIntelligence {

    protected BotInformation mSelf = null;
    volatile private Position mWorldState = null;
    volatile private Action mAction = null;
    
    volatile private boolean mIsStarted = false;
    volatile private boolean mIsPaused = false;
    
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
    
    /**
     * Main function of AI
     */
    public void run(){
        
        Position vWorldState = null;
        Action vBotAction = null;
        
        while ( mIsStarted ){
            
            while ( mIsPaused ){ try { sleep( 2 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }

            try {             
                if ( mWorldState != null  ){
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }
                    
                    vBotAction = startWorking(vWorldState);
                    
                    // Set action
                    synchronized ( this ) {
                        mAction = vBotAction;
                    }                  
                }                
            } catch ( Exception e ) {
                e.printStackTrace();
            }            
            
        }
        
    }
    
    public Action startWorking( Position aWorldData ){
        
        return Movement.NO_MOVEMENT;
    	
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
    public void putWorldState( WorldData aWorldState) {
        synchronized ( this ) {
        	if( aWorldState instanceof Position ){
        		mWorldState = (Position) aWorldState;
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
