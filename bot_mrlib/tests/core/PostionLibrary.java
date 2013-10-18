package core;
import static org.junit.Assert.*;

import org.junit.Test;

import essentials.communication.worlddata_server2008.ReferencePoint;


/**
 * @author Eike Petersen
 *
 */
public class PostionLibrary {

    @Test
    public void testGetMiddleOfTwoReferencePoints() {

        ReferencePoint vFirstPoint = new ReferencePoint( 15.0 , 20.0 );       
        ReferencePoint vSecondPoint = new ReferencePoint( 10.0 , -30.0 );;
        
        ReferencePoint vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss -5 Grad betragen.", -5, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );
        
        vFirstPoint = new ReferencePoint( 55.0 , 132.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , -156.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss 168 Grad betragen.", 168, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );

        vFirstPoint = new ReferencePoint( 55.0 , 156.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , -132.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss -168 Grad betragen.", -168, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );

        vFirstPoint = new ReferencePoint( 55.0 , 140.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , -30.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss 90 Grad betragen.", 90, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );

        vFirstPoint = new ReferencePoint( 55.0 , 90.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , -90.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss 90 Grad betragen.", 90, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );

        vFirstPoint = new ReferencePoint( 55.0 , 90.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , 90.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss 90 Grad betragen.", -90, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );
        
        vFirstPoint = new ReferencePoint( 55.0 , -90.0 );       
        vSecondPoint = new ReferencePoint( 10.0 , -90.0 );;
        
        vResultingPoint = mrlib.core.PositionLib.getMiddleOfTwoReferencePoints( vFirstPoint, vSecondPoint );

        assertEquals(" Der Winkel muss 90 Grad betragen.", -90, vResultingPoint.getAngleToPoint(), 0.1);
        assertEquals(" Die Distanz muss 10 betragen.", 10, vResultingPoint.getDistanceToPoint(), 0.1 );
                
    } 
    

}
