package mrlib.core;


import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
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
		fail("Not yet implemented");
	}

	@Test
	public void testKickToFellowPlayerFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testKickToNearestTeamMate() {
		fail("Not yet implemented");
	}

	@Test
	public void testKickToNearestOpponent() {
		fail("Not yet implemented");
	}

	@Test
	public void testKickToNearest() {
		fail("Not yet implemented");
	}

}
