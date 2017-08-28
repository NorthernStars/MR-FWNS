package fwns_network.universal;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static org.assertj.core.api.Assertions.assertThat;

public class NetworkCommunicationTest
{

	/*
	Tests for NetworkCommuication
	 */
	@Test
	public void testNetworkCommunication()
	{
		NetworkCommunication networkCommunication = null;

		try
		{

			networkCommunication = new NetworkCommunication(InetAddress.getByName("127.0.0.1"), 80);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		assertThat(networkCommunication).isNotNull();
		assertThat(networkCommunication).isInstanceOf(NetworkCommunication.class);
		assertThat(networkCommunication.getToServerSocket()).isNotNull();
		assertThat(networkCommunication.getToServerSocket()).isInstanceOf(DatagramSocket.class);
		assertThat(networkCommunication.isConnected()).isTrue();
	}


}
