package fwns_network.server_2008;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXB;

import essentials.communication.Action;
import essentials.core.BotInformation;

//toDo: logging

public class NetworkCommunication extends fwns_network.universal.NetworkCommunication{

    private boolean mEstablishedServerConnection = false;
    
    public NetworkCommunication(InetAddress aServerAddress, int aServerPort) throws IOException 
    {

        super(aServerAddress, aServerPort);

    }   
    public NetworkCommunication(InetAddress aServerAddress, int aServerPort, int aClientPort) throws IOException 
    {
        
        super(aServerAddress, aServerPort, aClientPort);

    }
	
	public void connectToServer( BotInformation aBot) throws IOException
	{
		
	    
		if( mToServerSocket != null && mDataPaket != null){
			
		    aBot.setBotIP( InetAddress.getLocalHost() ); //beschiss, besser machen
		    aBot.setBotPort( mToServerSocket.getLocalPort() );
		    
			InitialConnectionData vStartConnection = new InitialConnectionData( aBot );
			
			sendDatagramm( vStartConnection.getXMLString() );
			ConnectionHandshake vHandshake = new ConnectionHandshake();
			String vString = getDatagramm( 1000 );
			vHandshake.connect = JAXB.unmarshal( new StringReader( vString ), boolean.class );
			
			if( vHandshake.connect ){
				
				sendDatagramm( vString );
				
				mEstablishedServerConnection = true;
				
				getDatagramm( 1000 );
				
				return;
				
			}
		}

        mEstablishedServerConnection = false;
		
		return;
		
	}
	
	public void reconnectToServer(){
	    
	    mEstablishedServerConnection = true;
	    
	}
	
	public void sendDatagramm( Action aAction ) throws IOException{
		
		this.sendDatagramm( aAction.getXMLString() );		
		
	}
	
	@Override
    public boolean isConnected() {
        
        return mSocketInitialized && mEstablishedServerConnection;
        
    }

	public boolean ismEstablishedServerConnection()
	{
		return mEstablishedServerConnection;
	}
}
