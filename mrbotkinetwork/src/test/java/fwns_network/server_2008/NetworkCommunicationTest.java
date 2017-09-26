package fwns_network.server_2008;

import essentials.communication.worlddata_server2008.RawWorldData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkCommunicationTest
{

    @Test
    public void testConnectToServerWithMToServerSocketIsNull()
    {
        try
        {
            NetworkCommunication networkCommunication = new NetworkCommunication(InetAddress.getLoopbackAddress(),42);
            networkCommunication.
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectToServerWithMDataPaketIsNull()
    {

    }

    @Test
    public void testConnectToServerWithMToServerSocketIsNotNull()
    {

    }

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
