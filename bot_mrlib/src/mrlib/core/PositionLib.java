package mrlib.core;

import java.util.ArrayList;

import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation.Teams;

/**
 * Includes static functions to get or calculate positions on the playing field.
 * Diese Funktionen sind in den beiliegenden UnitTests verifiziert worden.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.5
 * @version 0.9
 *
 */
public class PositionLib {

    /**
     * Gibt den Punkt in der Mitte von zwei Referenzpunkten als neuen Referenzpunkt zurueck.
     * 
     * Dabei wird zwischen den beiden Punkten eine Line errechnet und 
     * in der Mitte dieser ein neuer Refenzpunkt erstellt. 
     * 
     * @since 0.9
     * @param aFirstReferencePoint der erste Referenzpunkt
     * @param aSecondReferencePoint der zweite Referenzpunkt
     * 
     * @return einen ReferencePoint mit den Koordinaten des Mittelpunkts der Parameter
     */
    public static ReferencePoint getMiddleOfTwoReferencePoints( ReferencePoint aFirstReferencePoint, ReferencePoint aSecondReferencePoint ){
       
        // Merke Seitenhalbierende hat nix! mit Winkelhalbiernder zu tun! Geogebra nutzen...
        
        //c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos( wa - wb )
        //sc = Math.sqrt( 2 ( a*a + b*b ) - c*c ) / 2;
                
        return null;
     
    }
    
    
    /**
     * Returns the middle of a goal
     * @param aWorldData
     * @param aTeam Team color of the goal
     * @return ReferencePoint
     */
    public static ReferencePoint getMiddleOfGoal( RawWorldData aWorldData, Teams aTeam ){       
        ReferencePoint rGoalMiddle = new ReferencePoint( 0.0 , 0.0 );
        ReferencePoint vGoalTop = null;
        ReferencePoint vGoalBottom = null;
        ReferencePoint vGoalMax = null;
        ReferencePoint vGoalMin = null;
        
        // get goal
        if( aTeam == Teams.Blue ) {
            vGoalTop = aWorldData.getBlueGoalCornerTop();
            vGoalBottom = aWorldData.getBlueGoalCornerBottom();
        }
        else{
            vGoalTop = aWorldData.getYellowGoalCornerTop();
            vGoalBottom = aWorldData.getYellowGoalCornerBottom();
        }
        
        // get min und max angle
        if( Math.abs(vGoalTop.getAngleToPoint())
                > Math.abs(vGoalBottom.getAngleToPoint()) ){
            vGoalMax = vGoalTop;
            vGoalMin = vGoalBottom;
        }
        else{
            vGoalMax = vGoalBottom;
            vGoalMin = vGoalTop;
        }
        
        // TODO: Calculate distance to goal
        
        rGoalMiddle.setAngelToPoint( vGoalMin.getAngleToPoint()
                + (vGoalMax.getAngleToPoint() - vGoalMin.getAngleToPoint()) );      
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
