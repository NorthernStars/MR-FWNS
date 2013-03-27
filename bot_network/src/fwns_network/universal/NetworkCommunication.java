package fwns_network.universal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class NetworkCommunication {

    protected boolean mConnected = false;
    
	/**
	 * Maximale Länge eines UDP-Datagramms in Byte.
	 */
	private static int MAX_DATAGRAM_LENGTH = 16384;
	
	/**
	 * Datenpaket zum Versenden von Daten an den Server.
	 */
	protected DatagramPacket mDataPaket;
	/**
	 * UDP-Socket
	 */
	protected DatagramSocket mToServerSocket = null;

	/**
	 * Richtet eine UDP-Verbindung zu einem Server ein.
	 * 
	 * @param aServerAdress
	 * - Adresse des Servers oder "localhost", wenn sich der Server auf dem
	 * gleichen Rechner befindet.
	 * @param aServerPortNr
	 * - Portnummer über die der Server seine Datagramme versendet.
	 * @throws IOException
	 * Verbindung konnte nicht eingerichtet werden.
	 */
	public NetworkCommunication( InetAddress aServerAddress, int aServerPort ) throws IOException
	{		

		mDataPaket = new DatagramPacket( new byte[NetworkCommunication.MAX_DATAGRAM_LENGTH], NetworkCommunication.MAX_DATAGRAM_LENGTH );
		mDataPaket.setAddress( aServerAddress );
		mDataPaket.setPort( aServerPort );
		
		mToServerSocket = new DatagramSocket();
		mToServerSocket.connect( aServerAddress, aServerPort );
		
		mConnected = true;
		
	}
	
	public NetworkCommunication( InetAddress aServerAddress, int aServerPort, int aClientPort) throws IOException
    {       

        this( aServerAddress, aServerPort );

        mToServerSocket.close();
        mToServerSocket = new DatagramSocket( aClientPort );
        mToServerSocket.connect( aServerAddress, aServerPort );

        mConnected = true;
        
    }

	/**
	 * Versendet die übergebenen Daten in einem Datagramm.
	 * 
	 * @param aData
	 * 		Zu versendende Daten als String.
	 */
	public void sendDatagramm( String aData ) throws IOException
	{
		
		mDataPaket.setData( aData.getBytes() );
		mToServerSocket.send( mDataPaket );

	}

	/**
	 * Wartet maximal aWaitTime Millisekunden auf ein Datagramm vom Server, und wandelt die empfangenen
	 * Daten in einen String um.
	 * 
	 * @param aWaitTime
	 * Bei aWaitTime = 0 wartet die Verbindung unentlich lange, bei aWaitTime > 0 entsprechende Millisekunden
	 * @return != null: Empfangene Daten als String.<br/>
	 * == null: Es wurden keine Daten empfangen.
	 * @throws IOException
	 * 	Schwerer Ausnahmefehler.
	 */
	public String getDatagramm( int aWaitTime ) throws IOException, SocketTimeoutException, SocketException
	{
		String vData = null;
		DatagramPacket vDatagrammPacketFromServer = new DatagramPacket( new byte[NetworkCommunication.MAX_DATAGRAM_LENGTH], NetworkCommunication.MAX_DATAGRAM_LENGTH );

		mToServerSocket.setSoTimeout( aWaitTime );
		
		mToServerSocket.receive( vDatagrammPacketFromServer );
		if ( vDatagrammPacketFromServer.getAddress().equals( mDataPaket.getAddress() ) ) {
			vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() - 1 );
		}
			
		return vData;
	}

	/**
	 * Beendet die Verbindung zum Server.
	 */
	public void closeConnection()
	{
		if ( mToServerSocket != null ) {
		    mToServerSocket.disconnect();
			mToServerSocket.close();
			mToServerSocket = null;
		}
		
		mConnected = false;
		
	}
    
    public boolean isConnected() {
        
        return mConnected;
        
    }
	
}
