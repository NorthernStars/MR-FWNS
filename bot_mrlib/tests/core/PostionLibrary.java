package core;
import static org.junit.Assert.*;

import mrlib.core.PositionLib;

import org.junit.Test;

import essentials.communication.worlddata_server2008.ReferencePoint;


/**
 * @author Eike Petersen, Louis Jorswieck
 *
 */
public class PostionLibrary {

    @Test
    public void testGetMiddleOfTwoReferencePoints() {
    	int flag = 0;
        ReferencePoint vFirstPoint = new ReferencePoint( 6 , 30.0, true );       
        ReferencePoint vSecondPoint = new ReferencePoint( 4.5 , 60.0, true );
        ReferencePoint vThirdPoint = new ReferencePoint( 4.46 , -159.94, true );       
        ReferencePoint vFourthPoint = new ReferencePoint( 3.89 , -108.5, true );
        
        ReferencePoint vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,07 betragen.", 5.07 , vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 42,81 Grad betragen.", 42.81 , vResultingPoint.getAngleToPoint(), 0.1);
        System.out.println("Winkel 1:"+vFirstPoint.getAngleToPoint());
        System.out.println("Winkel 2:"+vSecondPoint.getAngleToPoint());
        assertEquals("Der Winkel muss 30 Grad betragen.", 30, PositionLib.getAngleOfTwoReferencePoints(vFirstPoint, vSecondPoint) ,0.1);
        System.out.println("Winkel 1:"+vSecondPoint.getAngleToPoint());
        System.out.println("Winkel 2:"+vThirdPoint.getAngleToPoint());
        assertEquals("Der Winkel muss 140.06 Grad betragen.", 140.06, PositionLib.getAngleOfTwoReferencePoints(vSecondPoint, vThirdPoint) ,0.1);
        System.out.println("Winkel 1:"+vThirdPoint.getAngleToPoint());
        System.out.println("Winkel 2:"+vFourthPoint.getAngleToPoint());
        assertEquals("Der Winkel muss 51.44 Grad betragen.", 51.44, PositionLib.getAngleOfTwoReferencePoints(vThirdPoint, vFourthPoint) ,0.1);
        System.out.println("Winkel 1:"+vFourthPoint.getAngleToPoint());
        System.out.println("Winkel 2:"+vFirstPoint.getAngleToPoint());
        assertEquals("Der Winkel muss 138.5 Grad betragen.", 138.5, PositionLib.getAngleOfTwoReferencePoints(vFourthPoint, vFirstPoint) ,0.1);
        if(PositionLib.isBotInFieldOfFourReferencePoints(vFirstPoint, vSecondPoint, vThirdPoint, vFourthPoint))
        	flag = 1;
        else
        	flag = 0;
        assertEquals("Sollte true sein", 1, flag, 0);
        
        vFirstPoint = new ReferencePoint( 5 , -30, true );       
        vSecondPoint = new ReferencePoint( 11 , 45, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,6 betragen.", 6.6, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 23,55 Grad betragen.", 23.55, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 8 , -45, true );       
        vSecondPoint = new ReferencePoint( 7 , 30, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,96 betragen.", 5.96, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -10,43 Grad betragen.", -10.43, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , -60, true );       
        vSecondPoint = new ReferencePoint( 10 , -15, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 7,43 betragen.", 7.43, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -31,59 Grad betragen.", -31.59, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 5 , -105, true );       
        vSecondPoint = new ReferencePoint( 9 , -45, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,14 betragen.", 6.14, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -65,63 Grad betragen.", -65.63, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 4 , -165, true );       
        vSecondPoint = new ReferencePoint( 8 , -135, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,82 betragen.", 5.82, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -144,9 Grad betragen.", -144.9, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 135, true );       
        vSecondPoint = new ReferencePoint( 11 , -150, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,91 betragen.", 6.91, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -174,78 Grad betragen.", -174.78, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 105, true );       
        vSecondPoint = new ReferencePoint( 9 , 165, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,45 betragen.", 6.45, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 141,59 Grad betragen.", 141.59, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 7 , 75, true );       
        vSecondPoint = new ReferencePoint( 6 , 150, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,17 betragen.", 5.17, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 109,12 Grad betragen.", 109.12, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 5.01 , -89.51, true );       
        vSecondPoint = new ReferencePoint( 8 , 89.08, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,5 betragen.", 1.5, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 86,72 Grad betragen.", 86.72, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 7 , 120, true );       
        vSecondPoint = new ReferencePoint( 11 , -150, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,52 betragen.", 6.52, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 177,53 Grad betragen.", 177.53, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 9 , 45, true );       
        vSecondPoint = new ReferencePoint( 11 , -150, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,64 betragen.", 1.64, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 164,72 Grad betragen.", 164.72, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 4 , -45, true );       
        vSecondPoint = new ReferencePoint( 10 , 150, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 3,11 betragen.", 3.11, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 159,58 Grad betragen.", 159.58, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 120, true );       
        vSecondPoint = new ReferencePoint( 5 , -30, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,5 betragen.", 1.5, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 63,74 Grad betragen.", 63.74, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 8 , 45, true );       
        vSecondPoint = new ReferencePoint( 5 , -105, true );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 2,22 betragen.", 2.22, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 10,74 Grad betragen.", 10.74, vResultingPoint.getAngleToPoint(), 0.1);      
    } 
}
