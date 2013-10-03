package mrlib.core;

import java.util.ArrayList;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static functions to kick somewhere
 * @author Hannes Eilers
 *
 */
public class KickLib {

	/**
	 * Kick into a direction of an angle in degree with a force (0.0 to 1.0)
	 * @param aAngle
	 * @param aForce
	 * @return Action
	 */
	public static Action kickTo( double aAngle, float aForce ){
		return (Action) new Kick( aAngle, aForce );
	}
	
	/**
	 * Kick into a direction of an angle in degree with maximum force
	 * @param aAngle
	 * @return Action
	 */
	public static Action kickTo( double aAngle ){
		return kickTo( aAngle, 1.0f );
	}
	
	/**
	 * Kicks to reference point with maximum force
	 * @param aRefPoint
	 * @return Action
	 */
	public static Action kickTo( ReferencePoint aRefPoint ){
		return kickTo( aRefPoint, 1.0f );
	}
	
	/**
	 * Kicks to a reference point with specified force
	 * @param aRefPoint
	 * @param aForce
	 * @return
	 */
	public static Action kickTo( ReferencePoint aRefPoint, float aForce ){
		return kickTo( aRefPoint.getAngleToPoint(), aForce );
	}
	
	/**
	 * Kicks to a fellow player with maxmimum force
	 * @param aFellowPlayer
	 * @return Action
	 */
	public static Action kickTo( FellowPlayer aFellowPlayer ){
		return kickTo( aFellowPlayer, 1.0f );
	}
	
	/**
	 * Kicks to a fellow player with specified force
	 * @param aFellowPlayer
	 * @param aForce
	 * @return Action
	 */
	public static Action kickTo( FellowPlayer aFellowPlayer, float aForce ){
		return kickTo( aFellowPlayer.getAngleToPlayer(), aForce );
	}
	
	
	/**
	 * Kicks to nearest teammate
	 * @param aWorldData
	 * @return Action
	 */
	public static Action kickToNearestTeamMate( RawWorldData aWorldData ){
		return kickToNearest( aWorldData, FellowPlayers.TeamMates );
	}
	
	/**
	 * Kicks to nearest opponent
	 * @param aWorldData
	 * @return Action
	 */
	public static Action kickToNearestOpponent( RawWorldData aWorldData ){
		return kickToNearest( aWorldData, FellowPlayers.Opponents );
	}
	
	/**
	 * Kicks to nearest player (teammate or opponent)
	 * @param aWorldData
	 * @return Action
	 */
	public static Action kickToNearest( RawWorldData aWorldData ){
		return kickToNearest( aWorldData, FellowPlayers.AllPlayers );
	}
	
	/**
	 * Kicks to nearest player
	 * @param aWorldData
	 * @param aFellowPlayers Value of FellowPlayer enum, that speicifes if to kick to nearest opponent teammate or both.
	 * @return Action
	 */
	private static Action kickToNearest( RawWorldData aWorldData, FellowPlayers aFellowPlayers ){		
        ArrayList<FellowPlayer> vSpieler = new ArrayList<FellowPlayer>();
        
        // get a list of players
        if( aWorldData.getListOfOpponents() != null
        		&& (aFellowPlayers == FellowPlayers.Opponents || aFellowPlayers ==FellowPlayers.AllPlayers)  ){
            vSpieler.addAll( aWorldData.getListOfOpponents() );
        }
        if( aWorldData.getListOfTeamMates() != null
        		&& (aFellowPlayers == FellowPlayers.TeamMates || aFellowPlayers ==FellowPlayers.AllPlayers)){
            vSpieler.addAll( aWorldData.getListOfTeamMates() );
        }
        
        FellowPlayer vNearestPlayer = null;
        
        // get the nearest player
        for ( FellowPlayer vAPlayer : vSpieler ){
      
            if( vNearestPlayer == null || vNearestPlayer.getDistanceToPlayer() > vAPlayer.getDistanceToPlayer() ){
                
                vNearestPlayer = vAPlayer;
                
            }
            
        }
        
        if( vNearestPlayer != null ){
            
            return kickTo( vNearestPlayer );
            
        }

        return (Action) Movement.NO_MOVEMENT;        
    }
	
}
