package fwns_network.universal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static org.assertj.core.api.Assertions.assertThat;

@PowerMockIgnore("javax.management.*")
public class NetworkCommunicationTest
{

	/*
	Tests for NetworkCommuication
	 */
	@Test
	public void testNetworkCommunicationWithoutClientPort()
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

	@Test
	public void testNetworkCommunicationWithClientPort()
	{
		NetworkCommunication networkCommunication = null;

		try
		{

			networkCommunication = new NetworkCommunication(InetAddress.getByName("127.0.0.1"), 80,42);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		assertThat(networkCommunication).isNotNull();
		assertThat(networkCommunication).isInstanceOf(NetworkCommunication.class);
		assertThat(networkCommunication.getToServerSocket()).isNotNull();
		assertThat(networkCommunication.getToServerSocket()).isInstanceOf(DatagramSocket.class);
		assertThat(networkCommunication.getToServerSocket().getLocalPort()).isEqualTo(42);
		assertThat(networkCommunication.isConnected()).isTrue();

	}

	@Test
	public void testSendDatagramm()
	{

	}

}
