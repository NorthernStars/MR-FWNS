package mrlib.core;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
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
            
            FellowPlayer testFellowPlayer;
            final int testAId = 0;
            final String testANickname = "Test Fellow Player";
            final Boolean testAStatus = true;
            final double testADistanceToPlayer = 10.0;
            double testAAngleToPlayer;
            double testAOrientation = 0.0;
            
            // no turn, moving fwd
            testAAngleToPlayer = .5;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            Action returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAAngleToPlayer = -50.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAAngleToPlayer);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAAngleToPlayer = 40.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAAngleToPlayer);

            // turn on place left
            testAAngleToPlayer = -80.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            
            // turn on place right
            testAAngleToPlayer = 80.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAAngleToPlayer = -150.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAAngleToPlayer);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAAngleToPlayer = 150.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAAngleToPlayer);
            
            // no turn, moving bwd
            testAAngleToPlayer = 175.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // no turn, moving bwd
            testAAngleToPlayer = -175.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // Wrong value
            testAAngleToPlayer = 190.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.runTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
                
	}

	@Test
	public void testRunToBallPosition() {
            final double testDistance = 10.0;
            double testAngle;
            BallPosition testBallPosition;
          
            // no turn, moving fwd
            testAngle = .5;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            Action returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = -50.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);

            // turn depending on angle, moving fwd
            testAngle = 40.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);

            // turn on place left
            testAngle = -80.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            
            // turn on place right
            testAngle = 80.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = -150.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100+(int)testAngle);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // turn depending on angle, moving bwd
            testAngle = 150.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100-(int)testAngle);
            
            // no turn, moving bwd
            testAngle = 175.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // no turn, moving bwd
            testAngle = -175.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            
            // Wrong value
            testAngle = 190.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.runTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
                
                
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
            final double testDistance = 10.0;
            
            // no turn
            double testAngle = 5.0;
            ReferencePoint testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            Action returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // no turn
            testAngle = 175.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // 100% speed turn left
            testAngle = 80.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            
            // 100% speed turn right
            testAngle = -80.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);

            // 50% speed turn left
            testAngle = 40.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn left
            testAngle = -140.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn right
            testAngle = -40.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 50% speed turn right
            testAngle = 140.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 25% speed turn left
            testAngle = 15.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn left
            testAngle = -165.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn right
            testAngle = -15.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // 25% speed turn right
            testAngle = 165.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // Wrong value
            testAngle = 190.0;
            testRefPoint = new ReferencePoint(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testRefPoint);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
	}

	@Test
	public void testTurnToFellowPlayer() {
            FellowPlayer testFellowPlayer;
            final int testAId = 0;
            final String testANickname = "Test Fellow Player";
            final Boolean testAStatus = true;
            final double testADistanceToPlayer = 10.0;
            double testAAngleToPlayer;
            double testAOrientation = 0.0;
            
            // no turn
            testAAngleToPlayer = 5.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            Action returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // no turn
            testAAngleToPlayer = 175.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // 100% speed turn left
            testAAngleToPlayer = 80.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            
            // 100% speed turn right
            testAAngleToPlayer = -80.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);

            // 50% speed turn left
            testAAngleToPlayer = 40.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn left
            testAAngleToPlayer = -140.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn right
            testAAngleToPlayer = -40.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 50% speed turn right
            testAAngleToPlayer = 140.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 25% speed turn left
            testAAngleToPlayer = 15.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn left
            testAAngleToPlayer = -165.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn right
            testAAngleToPlayer = -15.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // 25% speed turn right
            testAAngleToPlayer = 165.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // Wrong value
            testAAngleToPlayer = 190.0;
            testFellowPlayer = new FellowPlayer(testAId, testANickname, testAStatus, testADistanceToPlayer, testAAngleToPlayer, testAOrientation);
            returnAction = MoveLib.turnTo(testFellowPlayer);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
	}

	@Test
	public void testTurnToBallPosition() {
            final double testDistance = 10.0;
            double testAngle;
            BallPosition testBallPosition;
            
            // no turn
            testAngle = 5.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            Action returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // no turn
            testAngle = 175.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // 100% speed turn left
            testAngle = 80.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            
            // 100% speed turn right
            testAngle = -80.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);

            // 50% speed turn left
            testAngle = 40.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn left
            testAngle = -140.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn right
            testAngle = -40.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 50% speed turn right
            testAngle = 140.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 25% speed turn left
            testAngle = 15.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn left
            testAngle = -165.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn right
            testAngle = -15.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // 25% speed turn right
            testAngle = 165.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // Wrong value
            testAngle = 190.0;
            testBallPosition = new BallPosition(testDistance, testAngle, true);
            returnAction = MoveLib.turnTo(testBallPosition);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
	}

	@Test
	public void testTurnToDouble() {
            // no turn
            double testAngle = 5.0;
            Action returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // no turn
            testAngle = 175.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
            
            // 100% speed turn left
            testAngle = 80.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(100);
            
            // 100% speed turn right
            testAngle = -80.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(100);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-100);

            // 50% speed turn left
            testAngle = 40.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn left
            testAngle = -140.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(50);
            
            // 50% speed turn right
            testAngle = -40.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 50% speed turn right
            testAngle = 140.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(50);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-50);
            
            // 25% speed turn left
            testAngle = 15.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn left
            testAngle = -165.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(-25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(25);
            
            // 25% speed turn right
            testAngle = -15.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // 25% speed turn right
            testAngle = 165.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat(((Movement) returnAction).getmRightWheelVelocity()).isEqualTo(25);
            assertThat(((Movement) returnAction).getmLeftWheelVelocity()).isEqualTo(-25);
            
            // Wrong value
            testAngle = 190.0;
            returnAction = MoveLib.turnTo(testAngle);
            assertThat(returnAction).isExactlyInstanceOf(Movement.class);
            assertThat((Movement) returnAction).isEqualTo(Movement.NO_MOVEMENT);
	}
        
}
