package mrlib.core;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;

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
            
            testReferencePointA = new ReferencePoint(-3, 5, false);
            testReferencePointB = new ReferencePoint(-4, 2, false);
            returnReferencePoint = PositionLib.getMiddleOfTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(returnReferencePoint).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnReferencePoint).getXOfPoint()).isCloseTo(-3.5, withinPercentage(1));
            assertThat(((ReferencePoint) returnReferencePoint).getYOfPoint()).isCloseTo(3.5, withinPercentage(1));
	}

	@Test
	public void testGetMiddleOfGoal() {
            RawWorldData testWorldData = TestScenario.getExampleWorldModel(
                        TestScenario.xmlExampleWorldData
            );
            ReferencePoint returnValue;
            
            returnValue = PositionLib.getMiddleOfGoal(testWorldData, Teams.Blue);
            assertThat(returnValue).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnValue).getXOfPoint()).isCloseTo(-128.0, withinPercentage(1));
            assertThat(((ReferencePoint) returnValue).getYOfPoint()).isCloseTo(-35.0, withinPercentage(1));
            
            returnValue = PositionLib.getMiddleOfGoal(testWorldData, Teams.Yellow);
            assertThat(returnValue).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnValue).getXOfPoint()).isCloseTo(653.0, withinPercentage(1));
            assertThat(((ReferencePoint) returnValue).getYOfPoint()).isCloseTo(-35.0, withinPercentage(1));
                
	}

	@Test
	public void testGetMiddleOfOwnGoal() {
            RawWorldData testWorldData = TestScenario.getExampleWorldModel(
                    TestScenario.xmlExampleWorldData
            );
            ReferencePoint returnValue;
            
            returnValue = PositionLib.getMiddleOfOwnGoal(testWorldData, Teams.Yellow);
            assertThat(returnValue).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnValue).getXOfPoint()).isCloseTo(-128.0, withinPercentage(1));
            assertThat(((ReferencePoint) returnValue).getYOfPoint()).isCloseTo(-35.0, withinPercentage(1));
            
            returnValue = PositionLib.getMiddleOfOwnGoal(testWorldData, Teams.Blue);
            assertThat(returnValue).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) returnValue).getXOfPoint()).isCloseTo(653.0, withinPercentage(1));
            assertThat(((ReferencePoint) returnValue).getYOfPoint()).isCloseTo(-35.0, withinPercentage(1));
	}

	@Test
	public void testIsBallInRangeOfRefPoint() {
                BallPosition testBallPos;
                ReferencePoint testReferencePoint;
                Boolean returnValue;
                
                testReferencePoint = new ReferencePoint(2, 6, false);
                testBallPos = new BallPosition(1.5, 6, false);
                returnValue = PositionLib.isBallInRangeOfRefPoint(testBallPos, testReferencePoint, 1);
                assertThat(returnValue).isExactlyInstanceOf(Boolean.class);
                assertThat(((Boolean) returnValue).booleanValue()).isEqualTo(true);
                
                testReferencePoint = new ReferencePoint(3, 4, false);
                testBallPos = new BallPosition(0.5, 6, false);
                returnValue = PositionLib.isBallInRangeOfRefPoint(testBallPos, testReferencePoint, 2);
                assertThat(returnValue).isExactlyInstanceOf(Boolean.class);
                assertThat(((Boolean) returnValue).booleanValue()).isEqualTo(false);
                
                testReferencePoint = new ReferencePoint(0.5, 7, false);
                testBallPos = new BallPosition(1.5, 6, false);
                returnValue = PositionLib.isBallInRangeOfRefPoint(testBallPos, testReferencePoint, 4);
                assertThat(returnValue).isExactlyInstanceOf(Boolean.class);
                assertThat(((Boolean) returnValue).booleanValue()).isEqualTo(true);
                
	}

	@Test
	public void testGetDistanceBetweenTwoRefPoints() {
            ReferencePoint testReferencePointA;
            ReferencePoint testReferencePointB;
            double distance;
            
            testReferencePointA = new ReferencePoint(-2.5, 7, false);
            testReferencePointB = new ReferencePoint(1.5,4, false);
            distance = PositionLib.getDistanceBetweenTwoRefPoints(testReferencePointA, testReferencePointB);
            assertThat(distance).isExactlyInstanceOf(Double.class);
            assertThat(((Double) distance).doubleValue()).isCloseTo(5, withinPercentage(1));
	}

	@Test
	public void testGetBestPointAwayFromBall() {
            RawWorldData rawWorldData = TestScenario.getExampleWorldModel(TestScenario.xmlExampleWorldData);
            ReferencePoint result;
            ReferencePoint ballPos;
            
            result = PositionLib.getBestPointAwayFromBall(rawWorldData);
            assertThat(result).isExactlyInstanceOf(ReferencePoint.class);
            assertThat(((ReferencePoint) result).getXOfPoint()).isCloseTo(-59.0, withinPercentage(1));
            assertThat(((ReferencePoint) result).getYOfPoint()).isCloseTo(44.0, withinPercentage(1));
            
            //TODO: Add more TestCases when setBallPosition is fixed
//            ballPos = new BallPosition(141.42, 225, true);
//            rawWorldData.setBallPosition(ballPos);
//            result = PositionLib.getBestPointAwayFromBall(rawWorldData);
//            assertThat(result).isExactlyInstanceOf(ReferencePoint.class);
//            assertThat(((ReferencePoint) result).getDistanceToPoint()).isCloseTo(585.66, withinPercentage(1));
//            assertThat(((ReferencePoint) result).getAngleToPoint()).isCloseTo(4.31, withinPercentage(1));
	}

	@Test
	public void testGetAngleBetweenTwoReferencePoints() {
            ReferencePoint testReferencePointA;
            ReferencePoint testReferencePointB;
            double angle;
            
            testReferencePointA = new ReferencePoint(-1.5,6,false);
            testReferencePointB = new ReferencePoint(1.5,7,false);
            angle = PositionLib.getAngleBetweenTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(angle).isExactlyInstanceOf(Double.class);
            assertThat(((Double) angle).doubleValue()).isCloseTo(26.13, withinPercentage(1));
            
            testReferencePointA = new ReferencePoint(1.5,5,false);
            testReferencePointB = new ReferencePoint(-2,4,false);
            angle = PositionLib.getAngleBetweenTwoReferencePoints(testReferencePointA, testReferencePointB);
            assertThat(angle).isExactlyInstanceOf(Double.class);
            assertThat(((Double) angle).doubleValue()).isCloseTo(43.26, withinPercentage(1));
	}

	@Test
	public void testIsBotInQuadrangle() {
            ReferencePoint testReferencePointA;
            ReferencePoint testReferencePointB;
            ReferencePoint testReferencePointC;
            ReferencePoint testReferencePointD;
            Boolean result;
            
            testReferencePointA = new ReferencePoint(1,6,false);
            testReferencePointB = new ReferencePoint(-2,4,false);
            testReferencePointC = new ReferencePoint(-2.5,-1,false);
            testReferencePointD = new ReferencePoint(1.5,-2,false);
            result = PositionLib.isBotInQuadrangle(testReferencePointA, testReferencePointB, testReferencePointC, testReferencePointD);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(true);
            
            testReferencePointA = new ReferencePoint(1,6,false);
            testReferencePointB = new ReferencePoint(-2,4,false);
            testReferencePointC = new ReferencePoint(-2,2,false);
            testReferencePointD = new ReferencePoint(-0.5,2,false);
            result = PositionLib.isBotInQuadrangle(testReferencePointA, testReferencePointB, testReferencePointC, testReferencePointD);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
	}

	@Test
	public void testIsMeselfOutOfBounds() {
            RawWorldData testWorldData = TestScenario.getExampleWorldModel(
                    TestScenario.xmlExampleWorldData
            );
            Boolean result;
            
            result = PositionLib.isMeselfOutOfBounds(testWorldData);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(true);
	}

	@Test
	public void testIsBallInQuadrangle() {
		fail("Not yet implemented");
	}

	@Test
	public void testAmINearestMateToPoint() {
            BotInformation testBotInfo = new BotInformation();
            testBotInfo.setVtId(20);
            testBotInfo.setRcId(20);
            RawWorldData testWorldData = TestScenario.getExampleWorldModel();
            ReferencePoint testReferencePoint;
            Boolean result;
           
            testReferencePoint = new ReferencePoint(6.0,7.0, false);
            result = PositionLib.amINearestMateToPoint(testWorldData, testReferencePoint, testBotInfo);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(true);
            
            testWorldData = TestScenario.getExampleWorldModel();
            FellowPlayer testFellowPlayer = new FellowPlayer(21, "Nearest Mate", true, 5.83, 30.96, 0.0);
            testWorldData.setFellowPlayer(testFellowPlayer);
            result = PositionLib.amINearestMateToPoint(testWorldData, testReferencePoint, testBotInfo);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
            
	}

	@Test
	public void testLawOfCosine() {
            double testSideA;
            double testSideB;
            double testSideC;
            double angle;

            testSideA = 15.9;
            testSideB = 14.5;
            testSideC = 22.5;
            angle = PositionLib.lawOfCosine(testSideA, testSideB, testSideC);
            assertThat(angle).isExactlyInstanceOf(Double.class);
            assertThat(((Double) angle).doubleValue()).isCloseTo(39.9, withinPercentage(1));
            
            testSideA = 12.1;
            testSideB = 10.4;
            testSideC = 22.5;
            angle = PositionLib.lawOfCosine(testSideA, testSideB, testSideC);
            assertThat(angle).isExactlyInstanceOf(Double.class);
            assertThat(((Double) angle).doubleValue()).isCloseTo(0.0, withinPercentage(1));
            
            testSideA = 10.81;
            testSideB = 37.04;
            testSideC = 31.0;
            angle = PositionLib.lawOfCosine(testSideA, testSideB, testSideC);
            assertThat(angle).isExactlyInstanceOf(Double.class);
            assertThat(((Double) angle).doubleValue()).isCloseTo(116.0, withinPercentage(1));
            
//            testSideA = -44.5;
//            testSideB = 55.3;
//            testSideC = 22.3;
//            angle = PositionLib.lawOfCosine(testSideA, testSideB, testSideC);
//            assertThat(angle).isExactlyInstanceOf(Double.class);
//            assertThat(((Double) angle).doubleValue()).isCloseTo(0.0, withinPercentage(1));
                
        }

	@Test
	public void testIsBallInTriangle() {
            ReferencePoint testRefPointA;
            ReferencePoint testRefPointB;
            ReferencePoint testRefPointC;
            BallPosition testBallPos;
            Boolean result;
            
            testBallPos = new BallPosition(-0.5, 1, false);
            testRefPointA = new ReferencePoint(0.5, -1, false);
            testRefPointB = new ReferencePoint(-0.5, 3, false);
            testRefPointC = new ReferencePoint(-1.5, -1, false);
            result = PositionLib.isBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(true);
            
            testBallPos = new BallPosition(0, 1, false);
            testRefPointA = new ReferencePoint(1,1,false);
            testRefPointB = new ReferencePoint(0,3, false);
            testRefPointC = new ReferencePoint(1,3, false);
            result = PositionLib.isBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
            
            testBallPos = new BallPosition(0.5, 4, false);
            result = PositionLib.isBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
            
            testBallPos = new BallPosition(1.5, 2, false);
            result = PositionLib.isBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
	}

}
