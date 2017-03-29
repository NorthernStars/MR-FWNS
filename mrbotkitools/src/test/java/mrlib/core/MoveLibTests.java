package mrlib.core;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.ReferencePoint;
import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MoveLibTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
        /**
         * @todo Movement überprüfen
         */
	public void testRunToReferencePoint() {

            final double testDistance = 10.0;
   
            // no turn, moving fwd
            double testAngle = .5;
            ReferencePoint testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            Action returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = -50.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = 40.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);

            // turn on place left
            testAngle = -80.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            
            // turn on place right
            testAngle = 80.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = -150.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = 150.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);
            
            // no turn, moving bwd
            testAngle = 175.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // no turn, moving bwd
            testAngle = -175.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // Wrong value
            testAngle = 190.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
	}

	@Test
	public void testRunToFellowPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRunToBallPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testRunToDouble() {
	
            // no turn, moving fwd
            double testAngle = .5;
            Action returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = -50.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = 40.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);

            // turn on place left
            testAngle = -80.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            
            // turn on place right
            testAngle = 80.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = -150.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = 150.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);
            
            // no turn, moving bwd
            testAngle = 175.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // no turn, moving bwd
            testAngle = -175.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // Wrong value
            testAngle = 190.0;
            returnAction = MoveLib.runTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
         
	}
        
	@Test
	public void testTurnToReferencePoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testTurnToFellowPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testTurnToBallPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testTurnToDouble() {
		fail("Not yet implemented");
	}

}
