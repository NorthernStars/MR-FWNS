package mrlib.core;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static function to let the agent move somewhere
 * @author Hannes Eilers
 *
 */
public class MoveLib {

	/**
	 * Runs to a reference point
	 * @param aRefPoint
	 * @return Action
	 */
	public static Action runTo( ReferencePoint aRefPoint ){
		return runTo( aRefPoint.getAngleToPoint() );
	}
	
	/**
	 * Runs to a fellow player
	 * @param aFellowPlayer
	 * @return Action
	 */
	public static Action runTo( FellowPlayer aFellowPlayer ) {
		return runTo( aFellowPlayer.getAngleToPlayer() );
	}
	
	/**
	 * Runs to a ball position
	 * @param aBallPosition
	 * @return Action
	 */
	public static Action runTo( BallPosition aBallPosition ) {
		return runTo( aBallPosition.getAngleToBall() );
	}
	
	/**
	 * Lets the agent run into an specific direction
	 * @param vAngle Angle in degree
	 * @return Action
	 */
	public static Action runTo( double vAngle ) {

        if( vAngle >= -10 && vAngle <= 10 ){            
            return (Action) new Movement( 100, 100 );            
        }
        
        if( vAngle > -60 && vAngle < -10 ){            
            return (Action) new Movement( 100, 100 + (int) vAngle );            
        }
        
        if( vAngle > 10 && vAngle < 60 ){            
            return (Action) new Movement( 100 - (int) vAngle, 100 );            
        }
        
        if( vAngle >= -130 && vAngle <= -60 ){            
            return (Action) new Movement( 100, -100 );            
        }
        
        if( vAngle >= 60 && vAngle <= 130 ){            
            return (Action) new Movement( -100, 100 );            
        }
        
        if( vAngle > -170 && vAngle < -130 ){            
            return (Action) new Movement( -100, 100 + (int) vAngle );            
        }
        
        if( vAngle > 130 && vAngle < 170 ){            
            return (Action) new Movement( 100 - (int) vAngle, -100 );            
        }
        
        if( ( vAngle >= 170 && vAngle <= 180 ) || ( vAngle <= -170 && vAngle >= -180 ) ){            
            return (Action) new Movement( -100, -100 );            
        }

        return (Action) Movement.NO_MOVEMENT;
    }
	
	public static Action turnTo(ReferencePoint vAngle){
		return turnTo(vAngle.getAngleToPoint());
	}
	
	public static Action turnTo(FellowPlayer vAngle){
		return turnTo(vAngle.getAngleToPlayer());
	}
	
	public static Action turnTo(BallPosition vAngle){
		return turnTo(vAngle.getAngleToBall());
	}
	
	public static Action turnTo(double vAngle){
		
		if(vAngle < 10 && vAngle >-10 || vAngle > 170 && vAngle < -170){
			return (Action) Movement.NO_MOVEMENT;
		}
		else if(vAngle >= 50 && vAngle <= 130){
			return (Action) new Movement(-100,100);
		}
		else if(vAngle <= -50 && vAngle >= -130){
			return (Action) new Movement(100,-100);
		}
		else if((vAngle >= 25 && vAngle <= 50) || vAngle < -130 && vAngle > -165){
			return (Action) new Movement(-50,50);
		}
		else if((vAngle < -25 && vAngle >= -50) || vAngle > 130 && vAngle < 165){
			return (Action) new Movement(50,-50);
		}
		else if((vAngle >= 10 && vAngle <= 25) || vAngle < -165 && vAngle > -170){
			return (Action) new Movement(-25,25);
		}
		else if((vAngle < -10 && vAngle >= -25) || vAngle > 165 && vAngle < 170){
			return (Action) new Movement(25,-25);
		}
		
		return (Action) Movement.NO_MOVEMENT;
		
	}
	
}
