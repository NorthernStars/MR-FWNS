package essentials.communication.worlddata_server2008;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.constants.Default;
import essentials.core.BotInformation.GamevalueNames;

public class FellowPlayerTest 
{
	
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
	public void equalsTestEqual() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", true, 130.7, 70.3, 0);
		assert(fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestSameInstance() 
	{
		assert(fellowPlayerOriginal.equals(fellowPlayerOriginal));
	}
	
	@Test
	public void equalsTestWrongClass() 
	{
		RawWorldData rwd = new RawWorldData();
		assert(!fellowPlayerOriginal.equals(rwd));
	}

	@Test
	public void equalsTestWrongID() 
	{
		fellowPlayerCompare  = new FellowPlayer(6, "Bob", true, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongName() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bill", true, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongStatus() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", false, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongDistance() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", true, 130.8, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongAngle() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", true, 130.7, 70.4, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestWrongOrientation() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", true, 130.7, 70.3, 0.2);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestNoNickname() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, null, true, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestNoNicknameSwitched()
	{
		fellowPlayerCompare  = new FellowPlayer(5, null, true, 130.7, 70.3, 0);
		assert(!fellowPlayerCompare.equals(fellowPlayerOriginal));
	}

	@Test
	public void equalsTestNoNicknameBoth() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, null, true, 130.7, 70.3, 0);
		FellowPlayer fellowPlayerOriginalLocal  = new FellowPlayer(5, null, true, 130.7, 70.3, 0);
		assert(fellowPlayerOriginalLocal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestNoStatus() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", null, 130.7, 70.3, 0);
		assert(!fellowPlayerOriginal.equals(fellowPlayerCompare));
	}

	@Test
	public void equalsTestNoStatusSwitched() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", null, 130.7, 70.3, 0);
		assert(!fellowPlayerCompare.equals(fellowPlayerOriginal));
	}

	@Test
	public void equalsTestNoStatusBoth() 
	{
		fellowPlayerCompare  = new FellowPlayer(5, "Bob", null, 130.7, 70.3, 0);
		FellowPlayer fellowPlayerOriginalLocal  = new FellowPlayer(5, "Bob", null, 130.7, 70.3, 0);
		assert(fellowPlayerCompare.equals(fellowPlayerOriginalLocal));
	}

	@Test
	public void setPointNameText()
	{
		FellowPlayer fellowPlayer = new FellowPlayer();
		int IDCountBefore = FellowPlayer.IDCOUNTER ;
		fellowPlayer.setPointName(ReferencePointName.Ball);
		int IDCountAfter = FellowPlayer.IDCOUNTER;
		assert(IDCountBefore+1==IDCountAfter);
		
	}

}
