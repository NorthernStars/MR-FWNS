package exampleai.brain;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;


// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

// -jar "FWNS_Bot.jar"

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

                            vBotAction = kickToNearest( vWorldState );

                        } else {
                            
                            vBotAction = runTo( vWorldState.getBallPosition().getAngleToBall() );
                            
                        }
                        
                    } else {
                        
                        if( isMeselfOutOfBounds( vWorldState )  || vWasOutOfBounds > 0){
                            
                            if( vWasOutOfBounds == 0){
                                
                                vWasOutOfBounds = 20;
                                
                            }
                            vBotAction = runTo( getBestPointAwayFromBall( vWorldState ) );
                            vWasOutOfBounds--;
                            
                        } else {
                            
                            vBotAction = runTo( vWorldState.getBallPosition().getAngleToBall() + 180 > 180? vWorldState.getBallPosition().getAngleToBall() - 180 : vWorldState.getBallPosition().getAngleToBall() + 180 );
                            
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
    
    private double getBestPointAwayFromBall( RawWorldData aWorldData ) {
        
        ReferencePoint vBestPoint = null;
        double vBestAngle = 0;
        
        ArrayList<ReferencePoint> vEligiblePoints = new ArrayList<ReferencePoint>();

        vEligiblePoints.add( aWorldData.getFieldCenter() );
        vEligiblePoints.add( aWorldData.getCenterLineTop() );
        vEligiblePoints.add( aWorldData.getCenterLineBottom() );
        vEligiblePoints.add( aWorldData.getBluePenaltyAreaFrontTop() );
        vEligiblePoints.add( aWorldData.getBluePenaltyAreaFrontBottom() );
        vEligiblePoints.add( aWorldData.getBlueGoalAreaFrontBottom() );
        vEligiblePoints.add( aWorldData.getBlueGoalAreaFrontTop() );
        vEligiblePoints.add( aWorldData.getYellowPenaltyAreaFrontTop() );
        vEligiblePoints.add( aWorldData.getYellowPenaltyAreaFrontBottom() );
        vEligiblePoints.add( aWorldData.getYellowGoalAreaFrontBottom() );
        vEligiblePoints.add( aWorldData.getYellowGoalAreaFrontTop() );
        
        double vPointToBallAngle = 0;
        
        for( ReferencePoint vPoint : vEligiblePoints ){
            
            vPointToBallAngle = Math.max( vPoint.getAngleToPoint(), aWorldData.getBallPosition().getAngleToBall() ) - Math.min( vPoint.getAngleToPoint(), aWorldData.getBallPosition().getAngleToBall() );
            if( vPointToBallAngle > 180 ){
                
                vPointToBallAngle = 360 - vPointToBallAngle;
                
            }
            if( vPointToBallAngle > vBestAngle ){
                
                vBestPoint = vPoint;
                vBestAngle = vPointToBallAngle;
                
            }
            
            
        }
        
        return vBestPoint.getAngleToPoint();
        
    }

    private boolean isMeselfOutOfBounds( RawWorldData aWorldData ) {

        double vTop = Math.max( aWorldData.getBlueFieldCornerTop().getAngleToPoint(), aWorldData.getYellowFieldCornerTop().getAngleToPoint() ) - Math.min( aWorldData.getBlueFieldCornerTop().getAngleToPoint(), aWorldData.getYellowFieldCornerTop().getAngleToPoint() );
        if( vTop > 180 ){
            
            vTop = 360 - vTop;
            
        }
        double vRight = Math.max( aWorldData.getBlueFieldCornerTop().getAngleToPoint(), aWorldData.getBlueFieldCornerBottom().getAngleToPoint() ) - Math.min( aWorldData.getBlueFieldCornerTop().getAngleToPoint(), aWorldData.getBlueFieldCornerBottom().getAngleToPoint() );
        if( vRight > 180 ){
            
            vRight = 360 - vRight;
            
        }
        double vBottom = Math.max( aWorldData.getBlueFieldCornerBottom().getAngleToPoint(), aWorldData.getYellowFieldCornerBottom().getAngleToPoint() ) - Math.min( aWorldData.getBlueFieldCornerBottom().getAngleToPoint(), aWorldData.getYellowFieldCornerBottom().getAngleToPoint() );
        if( vBottom > 180 ){
            
            vBottom = 360 - vBottom;
            
        }
        double vLeft = Math.max( aWorldData.getYellowFieldCornerTop().getAngleToPoint(), aWorldData.getYellowFieldCornerBottom().getAngleToPoint() ) - Math.min( aWorldData.getYellowFieldCornerTop().getAngleToPoint(), aWorldData.getYellowFieldCornerBottom().getAngleToPoint() );
        if( vLeft > 180 ){
            
            vLeft = 360 - vLeft;
            
        }
        
        return vTop + vRight + vBottom + vLeft < 360;
        
    }

    private Action runTo( double vAngle ) {

        if( vAngle >= -10 && vAngle <= 10 ){
            
            return (Action) new Movement( 100, 100 );
            
        }
        if( vAngle > -80 && vAngle < -10 ){
            
            return (Action) new Movement( 100, 100 + (int) vAngle );
            
        }
        if( vAngle > 10 && vAngle < 80 ){
            
            return (Action) new Movement( 100 - (int) vAngle, 100 );
            
        }
        if( vAngle >= -110 && vAngle <= -80 ){
            
            return (Action) new Movement( 100, -100 );
            
        }
        if( vAngle >= 80 && vAngle <= 110 ){
            
            return (Action) new Movement( -100, 100 );
            
        }
        if( vAngle > -170 && vAngle < -110 ){
            
            return (Action) new Movement( -100, 100 + (int) vAngle );
            
        }
        if( vAngle > 110 && vAngle < 170 ){
            
            return (Action) new Movement( 100 - (int) vAngle, -100 );
            
        }
        if( ( vAngle >= 170 && vAngle <= 180 ) || ( vAngle <= -170 && vAngle >= -180 ) ){
            
            return (Action) new Movement( -100, -100 );
            
        }

        return (Action) Movement.NO_MOVEMENT;
    }

    private Action kickToNearest( RawWorldData aWorldData ){
        
        ArrayList<FellowPlayer> vSpieler = new ArrayList<FellowPlayer>();
        if( aWorldData.getListOfOpponents() != null ){
            vSpieler.addAll( aWorldData.getListOfOpponents() );
        }
        if( aWorldData.getListOfOpponents() != null ){
            vSpieler.addAll( aWorldData.getListOfTeamMates() );
        }
        
        FellowPlayer vNearestPlayer = null;
        
        for ( FellowPlayer vAPlayer : vSpieler ){
      
            if( vNearestPlayer == null || vNearestPlayer.getDistanceToPlayer() > vAPlayer.getDistanceToPlayer() ){
                
                vNearestPlayer = vAPlayer;
                
            }
            
        }
        
        if( vNearestPlayer != null ){
            
            return (Action) new Kick( vNearestPlayer.getAngleToPlayer(), 100 );
            
        }

        return (Action) Movement.NO_MOVEMENT;
        
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
