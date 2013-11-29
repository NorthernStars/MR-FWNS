package mrlib.core;

import java.util.ArrayList;
import java.util.List;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation;
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
    	
    	    	
        //TODO: vieleicht noch mehr testen!
        //TODO: schoener machen
        //TODO: Funktionen auslagern
        // Merke Seitenhalbierende hat nix! mit Winkelhalbiernder zu tun! Geogebra nutzen...
        
        double a, b, wa, wb;
        
        if( aFirstReferencePoint.getAngleToPoint() > aSecondReferencePoint.getAngleToPoint() ){
            
            a = aFirstReferencePoint.getDistanceToPoint();
            wa = aFirstReferencePoint.getAngleToPoint();
            b = aSecondReferencePoint.getDistanceToPoint();
            wb =aSecondReferencePoint.getAngleToPoint();
            
        } else {

            a = aSecondReferencePoint.getDistanceToPoint();
            wa =aSecondReferencePoint.getAngleToPoint(); 
            b = aFirstReferencePoint.getDistanceToPoint();
            wb = aFirstReferencePoint.getAngleToPoint();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
        double sc = Math.sqrt( (2 * ( a*a + b*b )) - c*c ) / 2;
        
        double gamma = Math.abs( Math.toDegrees(Math.acos((sc*sc + b*b - 0.25*c*c) / (2*sc*b))));
        
        if ( wa < wb + 180){
            
            gamma = wb + gamma;
            
        } else {
            
            gamma = wb - gamma;
            
        }
       
        if (gamma > 180 ){
        	gamma = gamma - 360;
        }
        if (gamma < -180){
        	gamma = gamma + 360;
        }

        return new ReferencePoint(sc,gamma, true);
     
    }
    
    
    /**
     * Returns the middle of enemy goal
     * @param aWorldData
     * @param aTeam own team color BotInformation -> getTeam()
     * @return ReferencePoint
     */
    public static ReferencePoint getMiddleOfGoal( RawWorldData aWorldData, Teams aTeam ){       
        ReferencePoint rGoalMiddle = new ReferencePoint( 0.0 , 0.0, true );
        ReferencePoint vGoalTop = null;
        ReferencePoint vGoalBottom = null;
        
        // get goal
        if( aTeam == Teams.Yellow ) {
            vGoalTop = aWorldData.getBlueGoalCornerTop();
            vGoalBottom = aWorldData.getBlueGoalCornerBottom();
        }
        else{
            vGoalTop = aWorldData.getYellowGoalCornerTop();
            vGoalBottom = aWorldData.getYellowGoalCornerBottom();
        }
        
        rGoalMiddle = PositionLib.getMiddleOfTwoReferencePoints(vGoalTop, vGoalBottom);     
        return rGoalMiddle;
    }
    /**
     * Returns the middle of own goal
     * @param aWorldData
     * @param aTeam own team color BotInformation -> getTeam()
     * @return ReferencePoint
     */
    public static ReferencePoint getMiddleOfOwnGoal( RawWorldData aWorldData, Teams aTeam ){       
        ReferencePoint rOwnGoalMiddle = new ReferencePoint( 0.0 , 0.0, true );
        ReferencePoint vGoalTop = null;
        ReferencePoint vGoalBottom = null;
        
        // get goal
        if( aTeam == Teams.Blue ) {
            vGoalTop = aWorldData.getBlueGoalCornerTop();
            vGoalBottom = aWorldData.getBlueGoalCornerBottom();
        }
        else{
            vGoalTop = aWorldData.getYellowGoalCornerTop();
            vGoalBottom = aWorldData.getYellowGoalCornerBottom();
        }
        
        rOwnGoalMiddle = PositionLib.getMiddleOfTwoReferencePoints(vGoalTop, vGoalBottom);     
        return rOwnGoalMiddle;
    }
    
    
    public static boolean isBallInRangeOfRefPoint(BallPosition ballPos, ReferencePoint RefPoint,double range){
    	
    	double a, b, wa, wb;
        
        if( ballPos.getAngleToBall() > RefPoint.getAngleToPoint() ){
            
            a = ballPos.getDistanceToBall();
            wa = ballPos.getAngleToBall();
            b = RefPoint.getDistanceToPoint();
            wb = RefPoint.getAngleToPoint();
            
        } else {

            a = RefPoint.getDistanceToPoint();
            wa = RefPoint.getAngleToPoint(); 
            b = ballPos.getDistanceToBall();
            wb = ballPos.getAngleToBall();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
        if(c < range){
        	return true;
        }
        else{
        	return false;
        }
    }
    
    public static double getDistanceBetweenTwoRefPoints(ReferencePoint RefPoint0, ReferencePoint RefPoint){
    	
    	double a, b, wa, wb;
        
            
        a = RefPoint0.getDistanceToPoint();
        wa = RefPoint0.getAngleToPoint();
        b = RefPoint.getDistanceToPoint();
        wb = RefPoint.getAngleToPoint();
         
       
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
       return c;
    }

    /**
     * Get's the Defensive Midfielder position
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
    

	public static double getAngleOfTwoReferencePoints( ReferencePoint aFirstReferencePoint, ReferencePoint aSecondReferencePoint ){
		   
	    double wa, wb;
	    
	    if( aFirstReferencePoint.getAngleToPoint() > aSecondReferencePoint.getAngleToPoint() ){
	        
	        
	        wa = aFirstReferencePoint.getAngleToPoint();
	       
	        wb =aSecondReferencePoint.getAngleToPoint();
	        
	    } else {
	
	        
	        wa =aSecondReferencePoint.getAngleToPoint(); 
	       
	        wb = aFirstReferencePoint.getAngleToPoint();
	        
	    }
	    double gamma = 0;
	    
	    gamma = Math.abs(wa - wb);
	    if (gamma > 180)
	    	gamma = 360 - gamma;
	    
	    return gamma;
		}
	
	
	public static boolean isBotInFieldOfFourReferencePoints(ReferencePoint aFirstReferencePoint, ReferencePoint aSecondReferencePoint, ReferencePoint aThirdReferencePoint, ReferencePoint aFourthReferencePoint){
			double angle1, angle2, angle3, angle4;
			angle1= PositionLib.getAngleOfTwoReferencePoints(aFirstReferencePoint, aSecondReferencePoint);
			angle2= PositionLib.getAngleOfTwoReferencePoints(aSecondReferencePoint, aThirdReferencePoint);
			angle3= PositionLib.getAngleOfTwoReferencePoints(aThirdReferencePoint, aFourthReferencePoint);
			angle4= PositionLib.getAngleOfTwoReferencePoints(aFourthReferencePoint, aFirstReferencePoint);
			if(angle1+angle2+angle3+angle4 < 361 && angle1+angle2+angle3+angle4 > 359){
				return true;
			}else {
				return false;
			}
		}
	
	/*
	* TODO: Complete function to get if the Ball is in an area of 4 ReferencePoints
	* */
	
	public static boolean isBallinAreaOfFourRefPoints(ReferencePoint aFirstPoint, ReferencePoint aSecondPoint, ReferencePoint aThirdPoint, ReferencePoint aFourthPoint, BallPosition ballPos){
		
		double smallestX = 0, secondsmallestX = 0;
	//	double biggestX = 0;
		smallestX = aFirstPoint.getXOfPoint();
		if(aSecondPoint.getXOfPoint() < smallestX)
			secondsmallestX = smallestX;
			smallestX = aSecondPoint.getXOfPoint();
		if(aThirdPoint.getXOfPoint() < smallestX)
			secondsmallestX = smallestX;
			smallestX = aThirdPoint.getXOfPoint();
		if(aFourthPoint.getXOfPoint() < smallestX)
			secondsmallestX = smallestX;
			smallestX = aFourthPoint.getXOfPoint();
		
		
		
		
		return true;
		
	}
	public static boolean amINearestToPoint(RawWorldData vWorldState, ReferencePoint RefPoint, BotInformation aSelf){
		List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		vTeamMates.add(new FellowPlayer(aSelf.getVtId(),"me", true,0,0,0) );
		FellowPlayer closest_player = null;
		for ( FellowPlayer a: vTeamMates){
			if(closest_player == null || PlayersLib.getDistanceBetweenPlayerAndPoint(a, RefPoint) < PlayersLib.getDistanceBetweenPlayerAndPoint(closest_player, RefPoint)){
				closest_player = a;
			}
		}
		if(closest_player.getId() == aSelf.getVtId()){
			return true;
		}else{
			return false;
		}
	}
}