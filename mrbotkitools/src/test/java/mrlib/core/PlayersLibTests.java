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
		//fail("Not yet implemented");
		//Test not yet completed
		FellowPlayer chosenOne = PlayersLib.getNearestMateWithoutEnemyAround(worldModel, vBotInformation);
		
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(PlayersLib.getNearestMate(worldModel, vBotInformation).getDistanceToPlayer(), withinPercentage(0.1));
		//TODO: Test without players
		//TODO: Continue tests when function is fixed
	}

	@Test
	public void testGetMateWithEnemyNearButFurthestAway() {
		fail("Not yet implemented");
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
		
		
	}

	@Test
	public void testNearestOpponentToBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testNearestPlayerToBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnemyOnWayToRefPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsSpecificEnemyInAngleBetweenTwoRefPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasMateTheBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnemyNearBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDistanceBetweenPlayerAndPoint() {
		fail("Not yet implemented");
	}



}
