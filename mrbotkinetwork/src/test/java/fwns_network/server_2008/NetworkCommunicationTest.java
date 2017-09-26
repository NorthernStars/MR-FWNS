package fwns_network.server_2008;

import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.core.BotInformation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
            networkCommunication.setmToServerSocket(null);
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
            networkCommunication.setmDataPaket(null);
            networkCommunication.connectToServer(mockBotInformation);

            assertThat(networkCommunication.ismEstablishedServerConnection()).isFalse();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//    Needed?
    @Test
    public void testConnectToServerWithMToServerSocketIsNotNull()
    {

    }

//    Needed?
    @Test
    public void testConnectToServerWithMDataPaketIsNotNull()
    {

    }

    @Test
    public void testConnectToServerWithMToServerSocketAndMDataPaketAreNull()
    {

    }

    @Test
    public void testConnectToServerWithMToServerSocketAndMDataPaketAreNotNull()
    {

    }

}
