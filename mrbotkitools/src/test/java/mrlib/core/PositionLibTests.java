package mrlib.core;

import essentials.communication.worlddata_server2008.ReferencePoint;

import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionLibTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMiddleOfTwoReferencePoints() {
            ReferencePoint testReferencePointA;
            ReferencePoint testReferencePointB;
            ReferencePoint returnReferencePoint;
            
            testReferencePointA = new ReferencePoint(-3, 5, false);
            testReferencePointB = new ReferencePoint(2, 5, false);
            returnReferencePoint = PositionLib.getMiddleOfTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(returnReferencePoint).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnReferencePoint).getXOfPoint()).isCloseTo(-0.5, withinPercentage(1));
            assertThat(((ReferencePoint) returnReferencePoint).getYOfPoint()).isCloseTo(5, withinPercentage(1));
            
            testReferencePointA = new ReferencePoint(-2, 5, false);
            testReferencePointB = new ReferencePoint(3, 5, false);
            returnReferencePoint = PositionLib.getMiddleOfTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(returnReferencePoint).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnReferencePoint).getXOfPoint()).isCloseTo(0.5, withinPercentage(1));
            assertThat(((ReferencePoint) returnReferencePoint).getYOfPoint()).isCloseTo(5, withinPercentage(1));
            
            testReferencePointA = new ReferencePoint(-3, 5, false);
            testReferencePointB = new ReferencePoint(2.5, -1, false);
            returnReferencePoint = PositionLib.getMiddleOfTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(returnReferencePoint).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnReferencePoint).getXOfPoint()).isCloseTo(-0.25, withinPercentage(1));
            assertThat(((ReferencePoint) returnReferencePoint).getYOfPoint()).isCloseTo(2, withinPercentage(1));
            
            testReferencePointA = new ReferencePoint(-2.5, 1, false);
            testReferencePointB = new ReferencePoint(2.5, -1, false);
            returnReferencePoint = PositionLib.getMiddleOfTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(returnReferencePoint).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnReferencePoint).getXOfPoint()).isCloseTo(-0.25, withinPercentage(1));
            assertThat(((ReferencePoint) returnReferencePoint).getYOfPoint()).isCloseTo(0, withinPercentage(1));         
	}

	@Test
	public void testGetMiddleOfGoal() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMiddleOfOwnGoal() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBallInRangeOfRefPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDistanceBetweenTwoRefPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBestPointAwayFromBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAngleBetweenTwoReferencePoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBotInQuadrangle() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsMeselfOutOfBounds() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBallInQuadrangle() {
		fail("Not yet implemented");
	}

	@Test
	public void testAmINearestMateToPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testLawOfCosine() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBallInTriangle() {
		fail("Not yet implemented");
	}

}
