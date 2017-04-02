package mrlib.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.constants.Default;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;

public class PlayersLibTests {

	RawWorldData worldModel;
	BotInformation vBotInformation;
	
	
	
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

		
		BotInformation vSelf = new BotInformation();
		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel, vSelf);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2 (barely out of range)
		FellowPlayer nearestOpponent = PlayersLib.getNearestOpponent(worldModel);
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2+0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3 (exact in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2);
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4 (barely in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2-0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformationDouble() {

		BotInformation vSelf = new BotInformation();
		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel,vSelf, 700.0);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2: Barely out of range
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf, 799.99);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3: Exact in range
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf, 800.0);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4: Barely in range
		testAround = PlayersLib.isEnemyAround(worldModel, vSelf, 802.0);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundMate() {

		BotInformation vSelf = new BotInformation();
		FellowPlayer testMate = new FellowPlayer();
		testMate.set(new ReferencePoint(700, 55, true));
		
		//Case 1: 100mm away
		boolean testAround = PlayersLib.isEnemyAroundMate(worldModel, vSelf, testMate);
		assertThat(testAround).isEqualTo(false);
		
		//Case 2: 51mm away (barely out of range)
		testMate.setDistanceToPoint(749);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vSelf, testMate);
		assertThat(testAround).isEqualTo(false);

		//Case 2: exact in range
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vSelf, testMate);
		assertThat(testAround).isEqualTo(true);

		//Case 2: 49mm away (barely in range)
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vSelf, testMate);
		assertThat(testAround).isEqualTo(true);

		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testGetDistanceBetweenPlayerAndBall() {

		/*worldModel = TestScenario.getExampleWorldModel();
		BotInformation vSelf = new BotInformation();
		
		FellowPlayer P1 = new FellowPlayer(7, "Bob", true, TestScenario.ballDistance + 20, TestScenario.ballAngle, 0);
		P1 = PlayersLib.getNearestMate(worldModel, vSelf);
		double testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo(20, withinPercentage(0.1));*/
		fail("Not yet implemented");
	}

	@Test
	public void testGetNearestMateWithoutEnemyAround() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMateWithEnemyNearButFurthestAway() {
		fail("Not yet implemented");
	}

	@Test
	public void testAmINearestToBall() {
		fail("Not yet implemented");
	}

	@Test
	public void testNearestMateToBall() {
		fail("Not yet implemented");
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
