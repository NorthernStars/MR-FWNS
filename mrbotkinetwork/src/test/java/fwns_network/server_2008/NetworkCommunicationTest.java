package fwns_network.server_2008;

import essentials.core.BotInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class NetworkCommunicationTest
{
    BotInformation mockBotInformation = null;

    @Before
    public void setUp() throws Exception
    {
        mockBotInformation = mock(BotInformation.class);

    }

    @Test
    public void testConnectToServerWithMToServerSocketIsNull()
    {
        try
        {
            NetworkCommunication networkCommunication = new NetworkCommunication(InetAddress.getLoopbackAddress(),42);
            networkCommunication.setToServerSocket(null);
            networkCommunication.connectToServer(mockBotInformation);

            assertThat(networkCommunication.ismEstablishedServerConnection()).isFalse();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectToServerWithMDataPaketIsNull()
    {
        try
        {
            NetworkCommunication networkCommunication = new NetworkCommunication(InetAddress.getLoopbackAddress(),42);
            networkCommunication.setDataPaket(null);
            networkCommunication.connectToServer(mockBotInformation);

            assertThat(networkCommunication.ismEstablishedServerConnection()).isFalse();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectToServerWithMToServerSocketAndMDataPaketAreNull()
    {
        try
        {
            NetworkCommunication networkCommunication = new NetworkCommunication(InetAddress.getLoopbackAddress(),42);
            networkCommunication.setDataPaket(null);
            networkCommunication.setToServerSocket(null);
            networkCommunication.connectToServer(mockBotInformation);

            assertThat(networkCommunication.ismEstablishedServerConnection()).isFalse();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void testConnectToServerWithMToServerSocketAndMDataPaketAreNotNull()
    {
        // Todo: vHandshake.connect
        fail("Not yet implemented!");
    }

}
