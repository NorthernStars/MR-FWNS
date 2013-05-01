package conditions;

import essentials.communication.worlddata_server2008.RawWorldData;

/**
 * Includes static function to calculate if an agent is in a specific state
 * @author Hannes Eilers
 *
 */
public class StateLib {

	/**
	 * Checks if agent is out of bounds
	 * @param aWorldData
	 * @return True, if agent is out of bounds. Otherwise false.
	 */
	public static boolean isMeselfOutOfBounds( RawWorldData aWorldData ) {
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
	
}
