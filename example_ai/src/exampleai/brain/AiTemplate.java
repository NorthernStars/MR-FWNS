package exampleai.brain;

import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.PlayMode;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;

public class AiTemplate extends Thread implements ArtificialIntelligence {

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
    
    /**
     * Main function of AI
     */
    public void run(){
        
        RawWorldData vWorldState = null;
        Action vBotAction = null;
        
        while ( mIsStarted ){
            
            while ( mIsPaused ){ try { this.wait( 10 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }

            try {             
                if ( mNeedNewAction && mWorldState != null  ){
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }
                    
                    /**
                     * ------------ HELP --------------
                     * Variable vWorldState contains synchronized copy of current enviroment.
                     * Use the vWorldState to get soccer field positions or players positions.
                     * 
                     * Every position is stored as ReferencePoint. There are also subtypes like BallPosition,
                     * FellowPlayer, .. You can use all of them like a ReferencePoint for calculation.
                     * Every ReferencePoint contains polar coordinates (distance and angle to point).
                     * The current agent is the center of coordinate space and always views into direction of 0 degree.
                     * So the soccer field is turning around the agent! Try to use polar coordinates as often as possible.
                     * It's also possible to calculate the carthesian coordinates (x and y) of a ReferencePoint,
                     * but this is not recommended, because it needs some time and more mathematics to do this.
                     * So it's more efficient to use polar coordinate space.
                     * 
                     * You can also use static function from bot_mrlib library to access high level functions
                     * for generating Actions you want to do or for calculation of several positions on the field.
                     * Take a look at https://northernstars.github.io/MR-FWNS/userguide/04_bot_mrlib.html for more details.
                     * 
                     * This functions needs calculate an Action for the agent that could be either a Movement or a Kick.
                     * Save the calculated Action using vAction to synchronize your action with global mAction
                     * that is is used by the framework to send your Action to the server.
                     * 
                     * You can also use the BotINformation object mSelf that contains information about the
                     * current agent, like Team or IDs. It also contains some game values like the range around the ball,
                     * within the agent can kick the ball (called kick range).
                     * 
                     * For more details about how to implement an artifical intelligence, refer to the other examples or
                     * take a look at https://northernstars.github.io/MR-FWNS/tutorials/createai.html
                     */
                    
                    // Getting current play mode
                    PlayMode vPlayMode = mWorldState.getPlayMode();
                    
                    // Check for kick off
                    if ( vPlayMode == PlayMode.KickOff
                    		|| (vPlayMode == PlayMode.KickOffYellow && mSelf.getTeam() == Teams.Yellow)
                    		|| (vPlayMode == PlayMode.KickOffBlue && mSelf.getTeam() == Teams.Blue) ){
                    	
                    	// --------------- KICK OFF ---------------
                    	// --------------- KICK OFF END ---------------
                    	
                    }
                    // No kick off
                    else {
                    	
                    	// --------------- START AI -------------------	                    
                    	// ---------------- END AI --------------------
                    
                    }
                    
                    // Set action
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
    public void putWorldState( WorldData aWorldState) {
        synchronized ( this ) {
        	if( aWorldState instanceof RawWorldData ){
        		mWorldState = (RawWorldData) aWorldState;
        	}
            if( aWorldState != null && mWorldState.getReferencePoints() != null && !mWorldState.getReferencePoints().isEmpty() ){            
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