package mrlib.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.communication.action_server2008.Kick;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.constants.Default;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;

public class PlayersLibTests {

	RawWorldData worldModel;
	BotInformation vBotInformation = new BotInformation();
	
	
	
	
	@Before
	public void setUp() throws Exception {
		worldModel = TestScenario.getExampleWorldModel();
		vBotInformation.setVtId(100);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNearestMate() {

		FellowPlayer returnPlayer = PlayersLib.getNearestMate(worldModel, new BotInformation());
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
				
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformation() {

		
		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2 (barely out of range)
		FellowPlayer nearestOpponent = PlayersLib.getNearestOpponent(worldModel);
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2+0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3 (exact in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4 (barely in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2-0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformationDouble() {

		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel,vBotInformation, 700.0);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2: Barely out of range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 799.99);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3: Exact in range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 800.0);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4: Barely in range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 802.0);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundMate() {

		FellowPlayer testMate = new FellowPlayer();
		testMate.set(new ReferencePoint(700, 55, true));
		
		//Case 1: 100mm away
		boolean testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(false);
		
		//Case 2: 51mm away (barely out of range)
		testMate.setDistanceToPoint(749);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(false);

		//Case 2: exact in range
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(true);

		//Case 2: 49mm away (barely in range)
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(true);

		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testGetDistanceBetweenPlayerAndBall() {

		worldModel = TestScenario.getExampleWorldModel();
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setFellowPlayer(P1);
						
		double testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo(20, withinPercentage(0.1));
		
		P1.setAngleToPoint(-135.0); //opposite angle to 45°
		testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo((2*TestScenario.ballDistance + 20), withinPercentage(0.1));
		
		P1.set(654, 123, true);
		
		double testDistance2 = PositionLib.getDistanceBetweenTwoRefPoints(P1, worldModel.getBallPosition()); 
		testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo((testDistance2), withinPercentage(0.1));
		

		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAround() {

		FellowPlayer chosenOne = PlayersLib.getNearestMateWithoutEnemyAround(worldModel, vBotInformation);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(PlayersLib.getNearestMate(worldModel, vBotInformation).getDistanceToPlayer(), withinPercentage(0.1));
		FellowPlayer oldChosenOne = chosenOne;
		
		//P1 should not become nearest as opponent1 is around
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.opponent1Distance - 5, TestScenario.opponent1Angle, true));
		chosenOne = PlayersLib.getNearestMateWithoutEnemyAround(worldModel, vBotInformation);
		
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(oldChosenOne.getDistanceToPlayer(), withinPercentage(0.1));

	}

	@Test
	public void testGetMateWithEnemyNearButFurthestAway() {
		fail("Function is deprecated, status unclear");

		FellowPlayer chosenOne = PlayersLib.getMateWithEnemyNearButFurthestAway(worldModel, vBotInformation);
	}

	@Test
	public void testAmINearestToBall() {
		List<FellowPlayer> allPlayers = worldModel.getListOfTeamMates();
		allPlayers.addAll(worldModel.getListOfOpponents());
		
		Boolean amINear = PlayersLib.amINearestToBall(worldModel, worldModel.getBallPosition(), vBotInformation);
		assertThat(amINear).isEqualTo(true);
		
		//TODO: Add more TestCases when setBallPosition is fixed
		
	}

	@Test
	public void testNearestMateToBall() {
		//fail("Not yet implemented");
		FellowPlayer mateWithBalls = PlayersLib.nearestMateToBall(worldModel);
		
		assertThat(mateWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(0.1));
		assertThat(mateWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(0.1));
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setFellowPlayer(P1);
		
		mateWithBalls = PlayersLib.nearestMateToBall(worldModel);
		
		assertThat(mateWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(mateWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		worldModel = TestScenario.getExampleWorldModel();
		
		
	}

	@Test
	public void testNearestOpponentToBall() {
		FellowPlayer opponentWithBalls = PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.opponent1Distance, withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.opponent1Angle, withinPercentage(0.1));
		
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setOpponentPlayer(P1);
		
		opponentWithBalls=PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testNearestPlayerToBall() {
		FellowPlayer opponentWithBalls = PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.opponent1Distance, withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.opponent1Angle, withinPercentage(0.1));

		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setOpponentPlayer(P1);
		
		opponentWithBalls=PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));

		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testIsEnemyOnWayToRefPoint() {
		
		//Case 1: Enemy direct between refpoint and Player
		ReferencePoint testRefPoint = new ReferencePoint(TestScenario.opponent1Distance, TestScenario.opponent1Angle, true);
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance + 30);
		Boolean testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(true);

		//Case 2: Enemy behind refpoint
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance - 20);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(false);
		
		//Case 3: Enemy slightly in way, function call without tolerance
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance + 30);
		testRefPoint.setAngleToPoint(testRefPoint.getAngleToPoint() + 0.5);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(false);
		
		//Case 4: Enemy slightly in way, function call without tolerance
		testRefPoint.setAngleToPoint(testRefPoint.getAngleToPoint() + 0.5);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 1.2);
		
		assertThat(testBool).isEqualTo(true);
		
		
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPoints() {
		//Case 1: Player in small corridor angle
		ReferencePoint testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle -10, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle +10, true);

		Boolean testBool = PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(worldModel, testRefPoint1, testRefPoint2);
		assertThat(testBool).isEqualTo(true);
		
		//Case 2: Player borderline inside corridor angle
		testRefPoint1 = new ReferencePoint(7500, TestScenario.opponent1Angle, true);
		testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle, true);

		testBool = PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(worldModel, testRefPoint1, testRefPoint2);
		assertThat(testBool).isEqualTo(true);
		
		//Case 3: No player inside corridor
		testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle-20, true);
		testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle-10, true);

		testBool = PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(worldModel, testRefPoint1, testRefPoint2);
		assertThat(testBool).isEqualTo(false);
		
	}

	@Test
	public void testIsSpecificEnemyInAngleBetweenTwoRefPoints() {
		//Case 1: Player in small corridor angle
		ReferencePoint testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle -10, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle +10, true);
		FellowPlayer o1 = new FellowPlayer();
		o1 = worldModel.getListOfOpponents().get(0);

		Boolean testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1, testRefPoint2);
		assertThat(testBool).isEqualTo(true);
		
		testRefPoint1.set(new ReferencePoint(0, TestScenario.opponent1Angle -20, true));
		testRefPoint2.set(new ReferencePoint(0, TestScenario.opponent1Angle -30, true));
		
		testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1, testRefPoint2);
		assertThat(testBool).isEqualTo(false);
	}

	@Test
	public void testHasMateTheBall() {

		Boolean testTeamBall = PlayersLib.hasMateTheBall(worldModel, vBotInformation);
		assertThat(testTeamBall).isEqualTo(false);
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(20, 45,true));
		worldModel.setFellowPlayer(P1);
		
		testTeamBall = PlayersLib.hasMateTheBall(worldModel, vBotInformation);
		assertThat(testTeamBall).isEqualTo(true);
		
	}

	@Test
	public void testIsEnemyNearBall() {
		Boolean testEnemyBall = PlayersLib.isEnemyNearBall(worldModel, vBotInformation);
		assertThat(testEnemyBall).isEqualTo(false);
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(20, 45,true));
		worldModel.setOpponentPlayer(P1);
		
		testEnemyBall = PlayersLib.isEnemyNearBall(worldModel, vBotInformation);
		assertThat(testEnemyBall).isEqualTo(true);
		
	}

	@Test
	public void testGetDistanceBetweenPlayerAndPoint() {
		fail("Function is deprecated, status unclear");
//		FellowPlayer P1 = new FellowPlayer();
//		P1.set(new ReferencePoint(77,66,true));
//		ReferencePoint P2 = new ReferencePoint(66, 66, true);
//		double testDistance = PlayersLib.getDistanceBetweenPlayerAndPoint(P1, P2);
	}



}
