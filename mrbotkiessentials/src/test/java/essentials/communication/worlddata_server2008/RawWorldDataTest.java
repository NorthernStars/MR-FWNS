package essentials.communication.worlddata_server2008;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

public class RawWorldDataTest {
	
	RawWorldData rawWorldData = new RawWorldData();

	@Before
	public void setUp() throws Exception 
	{
	}

	@Test
	public void testSetOpponentPlayerWhenListEmpty() 
	{
		
		FellowPlayer fp1 = new FellowPlayer(1, "", true, 200, 100, 0);
		
		rawWorldData.setOpponentPlayer(fp1);

		assertThat(rawWorldData.getListOfOpponents().size() == 1).isTrue();
	
	}
}
