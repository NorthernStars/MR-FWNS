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
 * Functions tested using the included unit tests.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.5
 * @version 1.0
 *
 */
public class PositionLib {

    /**
     * Gets {@link ReferencePoint} in middle of two other {@link ReferencePoint}.
     * 
     * @since 0.9
     * @param aRefPoint0 	First {@link ReferencePoint}
     * @param aRefPoint1 	Second {@link ReferencePoint}
     * 
     * @return {@link ReferencePoint} in middle between {@code aRefPoint0} and {@code aRefPoint1}.
     */
    public static ReferencePoint getMiddleOfTwoReferencePoints( ReferencePoint aRefPoint0, ReferencePoint aRefPoint1 ){

        double a;
        double b;
        double wa;
        double wb;
        
        if( aRefPoint0.getAngleToPoint() > aRefPoint1.getAngleToPoint() ){
            
            a = aRefPoint0.getDistanceToPoint();
            wa = aRefPoint0.getAngleToPoint();
            b = aRefPoint1.getDistanceToPoint();
            wb =aRefPoint1.getAngleToPoint();
            
        } else {

            a = aRefPoint1.getDistanceToPoint();
            wa =aRefPoint1.getAngleToPoint(); 
            b = aRefPoint0.getDistanceToPoint();
            wb = aRefPoint0.getAngleToPoint();
            
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
     * Returns the middle of opponents goal.
     * @param aWorldData		{@link RawWorldData}
     * @param aTeam 			Own {@link Teams} information
     * @return ReferencePoint	{@link ReferencePoint} of middle of enemies goal.
     */
    public static ReferencePoint getMiddleOfGoal( RawWorldData aWorldData, Teams aTeam ){       
        ReferencePoint rGoalMiddle;
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
     * Returns the middle of own goal.
     * @param aWorldData		{@link RawWorldData}
     * @param aTeam 			Own {@link Teams} information
     * @return ReferencePoint	{@link ReferencePoint} of middle of own goal.
     */
    public static ReferencePoint getMiddleOfOwnGoal( RawWorldData aWorldData, Teams aTeam ){       
        ReferencePoint rOwnGoalMiddle;
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
    
    /**
     * Calculates if ball is in specific range around {@link ReferencePoint}.
     * @param ballPos	{@link BallPosition}
     * @param aRefPoint	{@link ReferencePoint}
     * @param range		{@link Double} range
     * @return			{@code true} if {@code ballPos} is in {@code range} around {@code aRefPoint}, {@code false} otherwise.
     */
    public static boolean isBallInRangeOfRefPoint(BallPosition ballPos, ReferencePoint aRefPoint, double range){
    	
    	double a;
        double b;
        double wa;
        double wb;
        
        if( ballPos.getAngleToBall() > aRefPoint.getAngleToPoint() ){
            
            a = ballPos.getDistanceToBall();
            wa = ballPos.getAngleToBall();
            b = aRefPoint.getDistanceToPoint();
            wb = aRefPoint.getAngleToPoint();
            
        } else {

            a = aRefPoint.getDistanceToPoint();
            wa = aRefPoint.getAngleToPoint(); 
            b = ballPos.getDistanceToBall();
            wb = ballPos.getAngleToBall();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
        return c < range;
    }
    
    /**
     * Calculates the distance between two {@link ReferencePoint}.
     * @param aRefPoint0	{@link ReferencePoint}
     * @param aRefPoint1	{@link ReferencePoint}
     * @return				{@link Double} of distance between {@code aRefPoint0} and {@code aRefPoint1}.
     */
    public static double getDistanceBetweenTwoRefPoints(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1){
    	
    	double a;
        double b;
        double wa;
        double wb;
        
            
        a = aRefPoint0.getDistanceToPoint();
        wa = aRefPoint0.getAngleToPoint();
        b = aRefPoint1.getDistanceToPoint();
        wb = aRefPoint1.getAngleToPoint();
         
       
        return Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    }

    /**
     * Get's the {@link ReferencePoint} farest away from ball.
     * @param aWorldData		{@link RawWorldData}
     * @return ReferencePoint	{@link ReferencePoint} farest away from ball.
     */
    public static ReferencePoint getBestPointAwayFromBall( RawWorldData aWorldData ) {        
        ReferencePoint vBestPoint = null;
        double vBestAngle = 0;
        
        ArrayList<ReferencePoint> vEligiblePoints = new ArrayList<>();

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
    

    /**
     * Calculates angle between two reference points
     * @param aRefPoint0	{@link ReferencePoint}
     * @param aRefPoint1	{@link ReferencePoint}
     * @return				{@link Double} angle between {@code aRefPoint0} and {@code aRefPoint1}.
     */
	public static double getAngleBetweenTwoReferencePoints( ReferencePoint aRefPoint0, ReferencePoint aRefPoint1 ){
		   
	    double wa;
            double wb;
	    
	    if( aRefPoint0.getAngleToPoint() > aRefPoint1.getAngleToPoint() ){
	        
	        
	        wa = aRefPoint0.getAngleToPoint();
	       
	        wb =aRefPoint1.getAngleToPoint();
	        
	    } else {
	
	        
	        wa =aRefPoint1.getAngleToPoint(); 
	       
	        wb = aRefPoint0.getAngleToPoint();
	        
	    }
	    double gamma = 0;
	    
	    gamma = Math.abs(wa - wb);
	    if (gamma > 180)
	    	gamma = 360 - gamma;
	    
	    return gamma;
	}
	
	/**
	 * Calculates if bot is inside quadrilateral of four {@link ReferencePoint}s.
	 * @param aRefPoint0	{@link ReferencePoint}
	 * @param aRefPoint1	{@link ReferencePoint}
	 * @param aRefPoint2	{@link ReferencePoint}
	 * @param aRefPoint3	{@link ReferencePoint}
	 * @return				{@code true} if bot is inside quadrilateral {@code aRefPoint0} to {@code aRefPoint3}, {@code false} otherwise.
	 */
	public static boolean isBotInQuadrangle(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1, ReferencePoint aRefPoint2, ReferencePoint aRefPoint3){
            double angle1;
            double angle2;
            double angle3;
            double angle4;

            angle1= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint0, aRefPoint1);
            angle2= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint1, aRefPoint2);
            angle3= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint2, aRefPoint3);
            angle4= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint3, aRefPoint0);
            return angle1+angle2+angle3+angle4 < 361 && angle1+angle2+angle3+angle4 > 359;
	}
	
	/**
	 * Calculates if bot is inside soccer field.
	 * @param aWorldData	{@link RawWorldData}
	 * @return				{@code true} if bot is inside soccer field, {@code false} othwerwise.
	 */
	public static boolean isMeselfOutOfBounds( RawWorldData aWorldData ){
            return isBotInQuadrangle(aWorldData.getBlueFieldCornerTop(),
                            aWorldData.getBlueFieldCornerBottom(),
                            aWorldData.getYellowFieldCornerBottom(),
                            aWorldData.getYellowFieldCornerTop());
	}
	
	
	/**
	 * Calculates if ball is inside qaudrilateral of four {@link ReferencePoint}s.
	 * ATTENTION: FUNCTION IS NOT WORKING! DON'T USE!
	 * @param aRefPoint0	{@link ReferencePoint}
	 * @param aRefPoint1	{@link ReferencePoint}
	 * @param aRefPoint2	{@link ReferencePoint}
	 * @param aRefPoint3	{@link ReferencePoint}
	 * @param ballPos		{@link BallPosition}
	 * @return				{@code true} if {@code ballPos} is inside quadrilateral {@code aRefPoint0} to {@code aRefPoint3}, {@code false} otherwise.
	 */
	public static boolean isBallInQuadrangle(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1, ReferencePoint aRefPoint2, ReferencePoint aRefPoint3, BallPosition ballPos){
		
            /*
            * TODO: Complete function to get if the Ball is in an area of 4 ReferencePoints
            * */
            
            return isBallInTriangle(aRefPoint0, aRefPoint1, aRefPoint2, ballPos) 
                    || isBallInTriangle(aRefPoint0, aRefPoint3, aRefPoint2, ballPos);
            
            
		
	}
	
	/**
	 * Calculate if agent is nearest team mate to a {@link ReferencePoint}.
	 * @param vWorldState	{@link RawWorldData}
	 * @param aRefPoint		{@link ReferencePoint}
	 * @param aSelf			{@link BotInformation}
	 * @return				{@code true} if bot is nearest team mate to {@code aRefPoint}.
	 */
	public static boolean amINearestMateToPoint(RawWorldData vWorldState, ReferencePoint aRefPoint, BotInformation aSelf){
		List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		vTeamMates.add(new FellowPlayer(aSelf.getVtId(),"me", true,0,0,0) );
		
		FellowPlayer closestPlayer = null;
		for ( FellowPlayer p: vTeamMates){
			
                    if(closestPlayer != null)
                    {
                        double distNew = aRefPoint.sub(p).getDistanceToPoint();
                        double distOld = aRefPoint.sub(closestPlayer).getDistanceToPoint();

                        if(distNew < distOld){
                            closestPlayer = p;
                        }
                    }
                    else
                    {
                        closestPlayer = p;
                    }
	
		}
		
		return closestPlayer != null && closestPlayer.getId() == aSelf.getVtId();
	}

	
	/**
	 * Calculates angle between side A and C (opposite side B) {@link double}.
	 * @param distA			{@link double}
	 * @param distB			{@link double}
	 * @param distC			{@link double}
	 * @return				{@code double} angle in degree opposing side {@code distB}.
	 */
	public static double lawOfCosine(double distA, double distB, double distC){
    	return Math.toDegrees(Math.acos((distA*distA+distC*distC-distB*distB)/(2*distA*distC)));
    }
    
	
	/**
	 * Checks if Ball is in Triangle of 3 ReferencePoints {@link ReferencePoint}.
	 * @param pointA			{@link ReferencePoint}
	 * @param pointB			{@link ReferencePoint}
	 * @param pointC			{@link ReferencePoint}
	 * @param ballpos			{@link ReferencePoint}
	 * @return				{@code true} if {@code ballpos} is in triangle of {@code pointA}, {@code pointB}, {@code pointC}.
	 */
    public static boolean isBallInTriangle(ReferencePoint pointA, ReferencePoint pointB, ReferencePoint pointC, ReferencePoint ballpos){  	
    	double distA = PositionLib.getDistanceBetweenTwoRefPoints(pointB, pointC);
    	double distB = PositionLib.getDistanceBetweenTwoRefPoints(pointA, pointC);
    	double distC = PositionLib.getDistanceBetweenTwoRefPoints(pointA, pointB);
    	
    	double distBallToA = PositionLib.getDistanceBetweenTwoRefPoints(pointA, ballpos);
    	double distBallToB = PositionLib.getDistanceBetweenTwoRefPoints(pointB, ballpos);
    	double distBallToC = PositionLib.getDistanceBetweenTwoRefPoints(pointC, ballpos);
        //Is Beta greater?
    	if(lawOfCosine(distB, distA, distC) > lawOfCosine(distBallToA, distBallToB, distC)){
    		//Is Epsilon greater?
    		
    		if(lawOfCosine(distA, distB, distC) > lawOfCosine(distBallToB, distBallToC, distA)){
                        //Is Alpha greater?
                        return lawOfCosine(distA, distC, distB) > lawOfCosine(distBallToC, distBallToA, distB);
    		} else return false;
    	} else return false;
    }
}
