package mrlib.core;


import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;

public class KickLibTests {
	
	public RawWorldData worldModel;

	@Before
	public void setUp() throws Exception {
		worldModel = TestScenario.getExampleWorldModel();
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
				
		Action returnAction = KickLib.kickToNearestTeamMate(worldModel);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
		
		
	}

	@Test
	public void testKickToNearestOpponent() {
		
		Action returnAction = KickLib.kickToNearestOpponent(worldModel);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(TestScenario.opponent1Angle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
	}

	@Test
	public void testKickToNearest() {
		
		Action returnAction = KickLib.kickToNearest(worldModel);

		assertThat(returnAction).isExactlyInstanceOf(Kick.class);
		assertThat(((Kick) returnAction).getAngle()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		assertThat(((Kick) returnAction).getForce()).isCloseTo(1.0f, withinPercentage(1));
		
		worldModel = new RawWorldData();

		returnAction = KickLib.kickToNearest(worldModel);

		assertThat(returnAction).isEqualTo(Movement.NO_MOVEMENT);

	}
	
	@Test
	public void testKickToNearestNoPlayers()
	{
		worldModel = new RawWorldData();
		Action returnAction = KickLib.kickToNearest(worldModel);
		
		assertThat(returnAction).isEqualTo(Movement.NO_MOVEMENT);
		
		worldModel = TestScenario.getExampleWorldModel();
	
	}
}
