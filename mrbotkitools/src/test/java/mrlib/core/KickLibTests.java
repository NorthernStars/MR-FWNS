package mrlib.core;


import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;

public class KickLibTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testKickToWithAngleAndForce() {
		final double testAngle = 10.0;
		final float testForce = 50.0f;
		Action returnAction = KickLib.kickTo(testAngle, testForce);
		
		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(testForce, withinPercentage(1));		
	}

	@Test
	public void testKickToDouble() {
		final double testAngle = 10.0;
		Action returnAction = KickLib.kickTo(testAngle);
		
		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));		
	}

	@Test
	public void testKickToReferencePoint() {
		final double testAngle = .5;
		final double testDistance = 10.0;
		final ReferencePoint testRefPoint = new ReferencePoint(testDistance, testAngle, true);
		Action returnAction = KickLib.kickTo(testRefPoint);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));		
				
	}

	@Test
	public void testKickToReferencePointFloat() {
		final double testAngle = .5;
		final double testDistance = 10.0f;
		final float testForce = .7f;
		final ReferencePoint testRefPoint = new ReferencePoint(testDistance, testAngle, true);
		Action returnAction = KickLib.kickTo(testRefPoint, testForce);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(testForce, withinPercentage(1));		
		
		
	}

	@Test
	public void testKickToFellowPlayer() {
		FellowPlayer testPlayer = new FellowPlayer();
		double testAngle = 40.0;
		testPlayer.set(0, testAngle, true);
		Action returnAction = KickLib.kickTo(testPlayer);
		
		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1, withinPercentage(1));
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
	}

	@Test
	public void testKickToFellowPlayerFloat() {
		FellowPlayer testPlayer = new FellowPlayer();
		double testAngle = 40.0;
		float testForce = 0.7f;
		testPlayer.set(0, testAngle, true);
		Action returnAction = KickLib.kickTo(testPlayer, testForce);
		
		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getForce()).isCloseTo(testForce, withinPercentage(1));
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(testAngle, withinPercentage(1));
		
		
	}

	@Test
	public void testKickToNearestTeamMate() {
		RawWorldData rawWorldData = setScenario();
				
		Action returnAction = KickLib.kickToNearestTeamMate(rawWorldData);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(-90.0, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
		
		
	}

	@Test
	public void testKickToNearestOpponent() {
		RawWorldData rawWorldData = setScenario();
		
		Action returnAction = KickLib.kickToNearestOpponent(rawWorldData);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(55.0, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
	}

	@Test
	public void testKickToNearest() {
		RawWorldData rawWorldData = setScenario();
		
		Action returnAction = KickLib.kickToNearest(rawWorldData);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(-90.0, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
	}
	
	private RawWorldData setScenario(){
		
		RawWorldData rawWorldData = new RawWorldData();
		FellowPlayer player1 = new FellowPlayer(0, "TestBot", true, 200.0, -90.0, 0.0);
		FellowPlayer player2 = new FellowPlayer(1, "TestBot2", true, 300.0, -80.0, 0.0);
		FellowPlayer oPlayer1 = new FellowPlayer(1, "OpponentBot2", true, 800.0, 55.0, 0.0);
		FellowPlayer oPlayer2 = new FellowPlayer(1, "OpponentBot2", true, 900.0, 66.0, 0.0);
		rawWorldData.setFellowPlayer(player1);
		rawWorldData.setFellowPlayer(player2);
		rawWorldData.setOpponentPlayer(oPlayer1);
		rawWorldData.setOpponentPlayer(oPlayer2);
		
		return rawWorldData;
	}

}
