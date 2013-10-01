package exampleai.brain;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import conditions.StateLib;

import core.KickLib;
import core.MoveLib;
import core.PositionLib;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;


// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

public class AI extends Thread implements ArtificialIntelligence {

    BotInformation mSelf = null;
    
    enum Getaggt { IstEs, IstEsNicht; }
    
    Getaggt mStatus = Getaggt.IstEsNicht;

    RawWorldData mWorldState = null;
    Action mAction = null;
    boolean mNeedNewAction = true;
    
    boolean mIsRunning = false;
    
    @Override
    public void initializeAI( BotInformation aOneSelf ) {
        
        mSelf = aOneSelf;
        
        if( mSelf != null ){
            int AiPara = 0;
            try{
                AiPara = Integer.parseInt( mSelf.getAIArgs() );
            } catch ( Exception e ) {
                
            }
            if ( AiPara == 1) {

                mStatus = Getaggt.IstEs;
                
            }
            
            
        }
        
        mIsRunning = true;
        start();
        
    }
    
    public void run(){
        
        RawWorldData vWorldState = null;
        Action vBotAction = null;
        
        int vWasOutOfBounds = 0;
        
        while ( mIsRunning ){

            try {
             
                if( mNeedNewAction && mWorldState != null && mWorldState.getBallPosition() != null ){
                    
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }
                    
                    if( mStatus == Getaggt.IstEs ){
                        
                        if( vWorldState.getBallPosition().getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange ) ){

                            vBotAction = KickLib.kickToNearest( vWorldState );

                        } else {
                            
                            vBotAction = MoveLib.runTo( vWorldState.getBallPosition() );
                            
                        }
                        
                    } else {
                        
                        if( StateLib.isMeselfOutOfBounds( vWorldState )  || vWasOutOfBounds > 0){
                            
                            if( vWasOutOfBounds == 0){
                                
                                vWasOutOfBounds = 20;
                                
                            }
                            vBotAction = MoveLib.runTo( PositionLib.getBestPointAwayFromBall( vWorldState ) );
                            vWasOutOfBounds--;
                            
                        } else {
                            
                            vBotAction = MoveLib.runTo( vWorldState.getBallPosition().getAngleToBall() + 180 > 180? vWorldState.getBallPosition().getAngleToBall() - 180 : vWorldState.getBallPosition().getAngleToBall() + 180 );
                            
                        }
                        
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
        
        mIsRunning = false;
        
    }

    @Override
    public Shell getConfigurationWindow( Display aDisplay ) {
        
        return null;
        
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

}
