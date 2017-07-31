package essentials.communication.worlddata_server2008;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.worlddata_server2008.FellowPlayer;

public class FellowPlayerTest {
	
	FellowPlayer fellowPlayerOriginal;
	FellowPlayer fellowPlayerCompare;
	
	@Before
	public void setUp() throws Exception 
	{
		fellowPlayerOriginal = new FellowPlayer(5, "Bob", true, 130.7, 70.3, 0);
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testToString() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void equalsTestEqual() {
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", true, 130.7, 70.3, 0);
		assert(fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongID() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bob", true, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongName() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bill", true, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongStatus() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bill", false, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongDistance() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bill", true, 130.8, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongAngle() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bill", true, 130.7, 70.4, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongOrientation() {
		fellowPlayerCompare  = new FellowPlayer(6, "Bill", true, 130.7, 70.3, 0.2);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

}
