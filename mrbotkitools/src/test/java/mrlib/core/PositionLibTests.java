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
                        "<rawWorldData>\n" +
                        "    <time>0</time>\n" +
                        "    <agent_id>20</agent_id>\n" +
                        "    <nickname>TestBot</nickname>\n" +
                        "    <status>found</status>\n" +
                        "    <max_agent>22</max_agent>\n" +
                        "	<playMode>kick off</playMode>\n" +
                        "    <score>\n" +
                        "        <yellow>0</yellow>\n" +
                        "        <blue>0</blue>\n" +
                        "    </score>\n" +
                        "    <ball>\n" +
                        "        <dist>264.6</dist>\n" +
                        "        <angle>-8.03823</angle>\n" +
                        "    </ball>\n" +
                        "    <teamMate>\n" +
                        "        <id>0</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>1</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>2</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>3</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>4</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>5</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>6</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>7</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>18</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>0.0</dist>\n" +
                        "        <angle>0.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>19</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>0.0</dist>\n" +
                        "        <angle>0.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <opponent>\n" +
                        "        <id>8</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>9</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>10</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>11</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>12</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>13</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>14</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>15</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>16</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>17</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>509.902</dist>\n" +
                        "        <angle>-11.3099</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_center</id>\n" +
                        "        <dist>335.932</dist>\n" +
                        "        <angle>38.4734</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_corner</id>\n" +
                        "        <dist>245.082</dist>\n" +
                        "        <angle>121.485</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_goal</id>\n" +
                        "        <dist>132.563</dist>\n" +
                        "        <angle>73.3422</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_pole</id>\n" +
                        "        <dist>135.351</dist>\n" +
                        "        <angle>161.03</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_small_area</id>\n" +
                        "        <dist>73.6003</dist>\n" +
                        "        <angle>143.286</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_corner</id>\n" +
                        "        <dist>685.631</dist>\n" +
                        "        <angle>17.7479</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_goal</id>\n" +
                        "        <dist>503.287</dist>\n" +
                        "        <angle>14.6161</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_pole</id>\n" +
                        "        <dist>654.481</dist>\n" +
                        "        <angle>3.85484</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_small_area</id>\n" +
                        "        <dist>585.655</dist>\n" +
                        "        <angle>4.30866</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>middle_center</id>\n" +
                        "        <dist>265.452</dist>\n" +
                        "        <angle>-7.79433</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_center</id>\n" +
                        "        <dist>383.419</dist>\n" +
                        "        <angle>-46.6909</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_corner</id>\n" +
                        "        <dist>306.961</dist>\n" +
                        "        <angle>-114.645</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_goal</id>\n" +
                        "        <dist>203.578</dist>\n" +
                        "        <angle>-79.242</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_pole</id>\n" +
                        "        <dist>171.406</dist>\n" +
                        "        <angle>-138.311</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_small_area</id>\n" +
                        "        <dist>128.363</dist>\n" +
                        "        <angle>-117.364</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_corner</id>\n" +
                        "        <dist>710.106</dist>\n" +
                        "        <angle>-23.135</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_goal</id>\n" +
                        "        <dist>526.468</dist>\n" +
                        "        <angle>-22.3269</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_pole</id>\n" +
                        "        <dist>662.876</dist>\n" +
                        "        <angle>-9.90283</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_small_area</id>\n" +
                        "        <dist>595.023</dist>\n" +
                        "        <angle>-11.0456</angle>\n" +
                        "    </flag>\n" +
                        "</rawWorldData>"
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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
            result = PositionLib.IsBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(true);
            
            testBallPos = new BallPosition(0, 1, false);
            testRefPointA = new ReferencePoint(1,1,false);
            testRefPointB = new ReferencePoint(0,3, false);
            testRefPointC = new ReferencePoint(1,3, false);
            result = PositionLib.IsBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
            
            testBallPos = new BallPosition(0.5, 4, false);
            result = PositionLib.IsBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
            
            testBallPos = new BallPosition(1.5, 2, false);
            result = PositionLib.IsBallInTriangle(testRefPointA, testRefPointB, testRefPointC, testBallPos);
            assertThat(result).isExactlyInstanceOf(Boolean.class);
            assertThat(((Boolean) result).booleanValue()).isEqualTo(false);
	}

}
