package mrlib.core;

import java.util.ArrayList;

import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation.Teams;

/**
 * Includes static functions to get or calculate positions
 * @author Hannes Eilers
 *
 */
public class PositionLib {

	/**
	 * Returns the middle of a goal
	 * @param aWorldData
	 * @param aTeam Team color of the goal
	 * @return ReferencePoint
	 */
	public static ReferencePoint getMiddleOfGoal( RawWorldData aWorldData, Teams aTeam ){		
		ReferencePoint rGoalMiddle = new ReferencePoint();
		ReferencePoint vGoalTop = null;
		ReferencePoint vGoalBottom = null;
		
		if( aTeam == Teams.Blue ) {
			vGoalTop = aWorldData.getBlueGoalCornerTop();
			vGoalBottom = aWorldData.getBlueGoalCornerBottom();
		}
		else{
			vGoalTop = aWorldData.getYellowGoalCornerTop();
			vGoalBottom = aWorldData.getYellowGoalCornerBottom();
		}
		
		// TODO: Calculate distance to goal
		
		rGoalMiddle.setAngelToPoint( vGoalTop.getAngleToPoint() - vGoalBottom.getAngleToPoint() );		
		return rGoalMiddle;
	}
	
	/**
	 * Get's the field position that's furthest away from ball
	 * @param aWorldData
	 * @return ReferencePoint
	 */
	public static ReferencePoint getBestPointAwayFromBall( RawWorldData aWorldData ) {        
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
        
        return vBestPoint;        
    }
	
}
