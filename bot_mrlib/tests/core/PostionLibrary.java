package core;
import static org.junit.Assert.*;

import org.junit.Test;

import essentials.communication.worlddata_server2008.ReferencePoint;


/**
 * @author Eike Petersen, Louis Jorswieck
 *
 */
public class PostionLibrary {

    @Test
    public void testGetMiddleOfTwoReferencePoints() {

        ReferencePoint vFirstPoint = new ReferencePoint( 6 , 30.0 );       
        ReferencePoint vSecondPoint = new ReferencePoint( 4.5 , 60.0 );
        
        ReferencePoint vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,07 betragen.", 5.07 , vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 42,81 Grad betragen.", 42.81 , vResultingPoint.getAngleToPoint(), 0.1);
        
        
        vFirstPoint = new ReferencePoint( 5 , -30 );       
        vSecondPoint = new ReferencePoint( 11 , 45 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,6 betragen.", 6.6, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 23,55 Grad betragen.", 23.55, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 8 , -45 );       
        vSecondPoint = new ReferencePoint( 7 , 30 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,96 betragen.", 5.96, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -10,43 Grad betragen.", -10.43, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , -60 );       
        vSecondPoint = new ReferencePoint( 10 , -15 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 7,43 betragen.", 7.43, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -31,59 Grad betragen.", -31.59, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 5 , -105 );       
        vSecondPoint = new ReferencePoint( 9 , -45 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,14 betragen.", 6.14, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -65,63 Grad betragen.", -65.63, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 4 , -165 );       
        vSecondPoint = new ReferencePoint( 8 , -135 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,82 betragen.", 5.82, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -144,9 Grad betragen.", -144.9, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 135 );       
        vSecondPoint = new ReferencePoint( 11 , -150 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,91 betragen.", 6.91, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss -174,78 Grad betragen.", -174.78, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 105 );       
        vSecondPoint = new ReferencePoint( 9 , 165 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,45 betragen.", 6.45, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 141,59 Grad betragen.", 141.59, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 7 , 75 );       
        vSecondPoint = new ReferencePoint( 6 , 150 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 5,17 betragen.", 5.17, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 109,12 Grad betragen.", 109.12, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 5.01 , -89.51 );       
        vSecondPoint = new ReferencePoint( 8 , 89.08 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,5 betragen.", 1.5, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 86,72 Grad betragen.", 86.72, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 7 , 120 );       
        vSecondPoint = new ReferencePoint( 11 , -150 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 6,52 betragen.", 6.52, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 177,53 Grad betragen.", 177.53, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 9 , 45 );       
        vSecondPoint = new ReferencePoint( 11 , -150 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,64 betragen.", 1.64, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 164,72 Grad betragen.", 164.72, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 4 , -45 );       
        vSecondPoint = new ReferencePoint( 10 , 150 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 3,11 betragen.", 3.11, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 159,58 Grad betragen.", 159.58, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 6 , 120 );       
        vSecondPoint = new ReferencePoint( 5 , -30 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 1,5 betragen.", 1.5, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 63,74 Grad betragen.", 63.74, vResultingPoint.getAngleToPoint(), 0.1);
        
        vFirstPoint = new ReferencePoint( 8 , 45 );       
        vSecondPoint = new ReferencePoint( 5 , -105 );
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );
        
        assertEquals(" Die Distanz muss 2,22 betragen.", 2.22, vResultingPoint.getDistanceToPoint(), 0.1 );
        assertEquals(" Der Winkel muss 10,74 Grad betragen.", 10.74, vResultingPoint.getAngleToPoint(), 0.1);

            
    } 
    

}
