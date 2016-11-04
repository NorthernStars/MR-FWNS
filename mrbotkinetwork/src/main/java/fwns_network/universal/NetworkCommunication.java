package fwns_network.universal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;


/**
 * Die grundlegende Netzwerkverbindung zum MRServer. Es wird das UDP-
 * Protokoll genutzt.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.1
 * @version 0.9
 *
 */
public class NetworkCommunication {

    protected boolean mSocketInitialized = false;
    
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
	 * @param aServerAddress
	 * - Adresse des Servers oder "localhost", wenn sich der Server auf dem
	 * gleichen Rechner befindet.
	 * @param aServerPort
	 * - Portnummer über die der Server seine Datagramme versendet.
	 * @throws IOException
	 * Verbindung konnte nicht eingerichtet werden.
	 */
	public NetworkCommunication( InetAddress aServerAddress, int aServerPort ) throws IOException
	{		

		initialiseDatagram( aServerAddress, aServerPort );
		
		mToServerSocket = new DatagramSocket();
		mToServerSocket.connect( aServerAddress, aServerPort );
		
		mSocketInitialized = true;
		
	}

    private void initialiseDatagram( InetAddress aServerAddress, int aServerPort ) {
        
        mDataPaket = new DatagramPacket( new byte[NetworkCommunication.MAX_DATAGRAM_LENGTH], NetworkCommunication.MAX_DATAGRAM_LENGTH );
		mDataPaket.setAddress( aServerAddress );
		mDataPaket.setPort( aServerPort );
		
    }
	
	public NetworkCommunication( InetAddress aServerAddress, int aServerPort, int aClientPort) throws IOException
    {       

	    initialiseDatagram( aServerAddress, aServerPort );
	    
        mToServerSocket = new DatagramSocket( aClientPort );
        mToServerSocket.connect( aServerAddress, aServerPort );

        mSocketInitialized = true;
        
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
			vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() );
			// Alter Footballserver sendet ein "|" am Ende des XMLStrings und das hier ist der Fix :(
			if( !vData.endsWith( ">" ) ){
			    
			    vData = new String( vData.getBytes(), 0, vData.length() - 1 );
			    
			}
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
		
		mSocketInitialized = false;
		
	}
    
    public boolean isConnected() {
        
        return mSocketInitialized;
        
    }
    
    /**
     * Gibt die Serveradresse, den Serverport, die Clientadresse und den Clientport in einem
     * vorformatierten String aus.
     * 
     * @since 0.9
     * 
     * @return String in der Form: (Clientip):(Clientport) zu (Serverip):(Serverport)
     */
    public String toString(){
        if ( mToServerSocket != null ) {
            
            return mToServerSocket.getLocalAddress().toString() + ":" + mToServerSocket.getLocalPort() + " zu " + mToServerSocket.getInetAddress().toString() + ":" + mToServerSocket.getPort();
            
        } else {
            
            return "Es besteht keine Serververbindung";
            
        }
        
    }
	
}
