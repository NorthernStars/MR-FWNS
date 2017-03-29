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
import essentials.core.BotInformation;

public class PlayersLibTests {

	public RawWorldData worldModel;
	
	
	
	@Before
	public void setUp() throws Exception {
		worldModel = TestScenario.setScenario();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNearestMate() {


		FellowPlayer returnPlayer = PlayersLib.getNearestMate(worldModel, new BotInformation());
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(200.0, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(-90.0, withinPercentage(1));
		
		
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformation() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformationDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnemyAroundMate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDistanceBetweenPlayerAndBall() {
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
