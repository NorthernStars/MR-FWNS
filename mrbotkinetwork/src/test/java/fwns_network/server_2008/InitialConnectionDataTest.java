package fwns_network.server_2008;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import essentials.constants.Default;
import essentials.core.BotInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class InitialConnectionDataTest {

	BotInformation botInformationMock = mock(BotInformation.class);
	final int mOwnId = 20;

	@Before
	public void setUp() throws Exception
	{
		when(botInformationMock.getRcId()).thenReturn(mOwnId);
		when(botInformationMock.getVtId()).thenReturn(mOwnId);
		when(botInformationMock.getBotname()).thenReturn("MyTestBot");
	}

	@Test
	public void testInitialConnectionData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetXMLString() {
		InitialConnectionData initialConnectionData = new InitialConnectionData(botInformationMock);
		//assertThat(initialConnectionData.getXMLString(),);
		fail("Not yet implemented");
	}

}
