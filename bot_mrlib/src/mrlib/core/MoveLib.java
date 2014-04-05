package mrlib.core;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static function to let the agent move somewhere.
 * @author Hannes Eilers
 * @version 1.0
 *
 */
public class MoveLib {
	
	/**
	 * Angle to {@link ReferencePoint} wihtout to turn during movement.
	 */
	public static double moveAngleNoTurn = 10.0;
	
	/**
	 * Angle to {@link ReferencePoint} below that the agent should mvoe and turn,
	 * depending on the value of the angel to the {@link ReferencePoint}.
	 * This value has to bee higher than {@link #moveAngleNoTurn}.
	 */
	public static double moveAngleTurnAndMove = 60.0;
	
	/**
	 * Angle to below that agent should turn with 25% speed only.
	 */
	public static double turnAngleSlowSpeed = 25.0;
	
	

	/**
	 * Runs to a reference point.
	 * @param aRefPoint	{@link ReferencePoint}
	 * @return Action	{@link Action}
	 */
	public static Action runTo( ReferencePoint aRefPoint ){
		return runTo( aRefPoint.getAngleToPoint() );
	}
	
	/**
	 * Runs to a fellow player.
	 * @param aFellowPlayer	{@link FellowPlayer}
	 * @return Action		{@link Action}
	 */
	public static Action runTo( FellowPlayer aFellowPlayer ) {
		return runTo( aFellowPlayer.getAngleToPlayer() );
	}
	
	/**
	 * Runs to a ball position.
	 * @param aBallPosition	{@link BallPosition}
	 * @return Action		{@link Action}
	 */
	public static Action runTo( BallPosition aBallPosition ) {
		return runTo( aBallPosition.getAngleToBall() );
	}
	
	/**
	 * Lets the agent run into an specific direction.
	 * @param vAngle	{@link Double} direction angle to run to in degree
	 * @return Action	{@link Action}
	 */
	public static Action runTo( double vAngle ) {

		// no turn fwd
        if( vAngle >= -moveAngleNoTurn && vAngle <= moveAngleNoTurn ){            
            return (Action) new Movement( 100, 100 );            
        }
        
        // turn depending on angle fwd
        if( vAngle > -moveAngleTurnAndMove && vAngle < -moveAngleNoTurn ){            
            return (Action) new Movement( 100, 100 + (int) vAngle );            
        }
        
        // turn depending on angle fwd
        if( vAngle > moveAngleNoTurn && vAngle < moveAngleTurnAndMove ){            
            return (Action) new Movement( 100 - (int) vAngle, 100 );            
        }
        
        // turn on place left
        if( vAngle >= -(180-moveAngleTurnAndMove) && vAngle <= -moveAngleTurnAndMove ){            
            return (Action) new Movement( 100, -100 );            
        }
        
        // turn on place right
        if( vAngle >= moveAngleTurnAndMove && vAngle <= (180-moveAngleTurnAndMove) ){            
            return (Action) new Movement( -100, 100 );            
        }
        
        // turn depending on angle bwd
        if( vAngle > -(180-moveAngleNoTurn) && vAngle < -(180-moveAngleTurnAndMove) ){            
            return (Action) new Movement( -100, 100 + (int) vAngle );            
        }
        
        // turn depending on angle bwd
        if( vAngle > (180-moveAngleTurnAndMove) && vAngle < (180-moveAngleNoTurn) ){            
            return (Action) new Movement( 100 - (int) vAngle, -100 );            
        }
        
        // no turn bwd
        if( ( vAngle >= (180-moveAngleNoTurn) && vAngle <= 180 )
        		|| ( vAngle <= -(180-moveAngleNoTurn) && vAngle >= -180 ) ){            
            return (Action) new Movement( -100, -100 );            
        }

        return (Action) Movement.NO_MOVEMENT;
    }
	
	/**
	 * Lets the agent run to a {@link ReferencePoint}.
	 * @param vRefPoint	{@link ReferencePoint}
	 * @return			{@link Action}
	 */
	public static Action turnTo(ReferencePoint vRefPoint){
		return turnTo(vRefPoint.getAngleToPoint());
	}
	
	/**
	 * Lets agent run to {@link FellowPlayer}
	 * @param vFellowPlayer	{@link FellowPlayer}
	 * @return				{@link Action}
	 */
	public static Action turnTo(FellowPlayer vFellowPlayer){
		return turnTo(vFellowPlayer.getAngleToPlayer());
	}
	
	/**
	 * Lets agent run to {@link BallPosition}.
	 * @param vBallPos	{@link BallPosition}
	 * @return			{@link Action}
	 */
	public static Action turnTo(BallPosition vBallPos){
		return turnTo(vBallPos.getAngleToBall());
	}
	
	/**
	 * Lets agent turn towards an agnle (no movement).
	 * @param vAngle	{@link Double} angle to turn to.
	 * @return			{@link Action}
	 */
	public static Action turnTo(double vAngle){
		
		// no turn
		if(vAngle < moveAngleNoTurn && vAngle > -moveAngleNoTurn
				|| vAngle > (180-moveAngleNoTurn) && vAngle < -(180-moveAngleNoTurn)){
			return (Action) Movement.NO_MOVEMENT;
		}
		
		// 100% speed turn left
		if(vAngle >= moveAngleTurnAndMove && vAngle <= (180-moveAngleTurnAndMove)){
			return (Action) new Movement(-100,100);
		}
		
		// 50% speed turn right
		if(vAngle <= -moveAngleTurnAndMove && vAngle >= -(180-moveAngleTurnAndMove)){
			return (Action) new Movement(100,-100);
		}
		
		// 50% speed turn left
		if((vAngle >= turnAngleSlowSpeed && vAngle <= moveAngleTurnAndMove)
				|| vAngle < -(180-moveAngleTurnAndMove) && vAngle > -(180-turnAngleSlowSpeed)){
			return (Action) new Movement(-50,50);
		}
		
		// 50% speed turn right
		if((vAngle < -turnAngleSlowSpeed && vAngle >= -moveAngleTurnAndMove)
				|| vAngle > (180-moveAngleTurnAndMove) && vAngle < (180-turnAngleSlowSpeed)){
			return (Action) new Movement(50,-50);
		}
		
		// 25% speed turn left
		if((vAngle >= moveAngleNoTurn && vAngle <= turnAngleSlowSpeed)
				|| vAngle < -(180-turnAngleSlowSpeed) && vAngle > -(180-moveAngleNoTurn)){
			return (Action) new Movement(-25,25);
		}
		
		// 25% speed turn right
		if((vAngle < -moveAngleNoTurn && vAngle >= -turnAngleSlowSpeed)
				|| vAngle > (180-turnAngleSlowSpeed) && vAngle < (180-moveAngleNoTurn)){
			return (Action) new Movement(25,-25);
		}
		
		return (Action) Movement.NO_MOVEMENT;
		
	}
	
}
