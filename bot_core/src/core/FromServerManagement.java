package core;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.swt.widgets.DateTime;

import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.core.ArtificialIntelligence;
import fwns_network.universal.NetworkCommunication;

public class FromServerManagement extends Thread{

	private NetworkCommunication mFromServerCommunication = null;
	private boolean mManageMessagesFromServer = false;
    private AtomicLong mLastReceivedMessage = new AtomicLong( 0 );
	
	private ArtificialIntelligence mCurrentBotAI = null;
	
	public FromServerManagement() {
		
	}
	
	public void setServerConnection( NetworkCommunication aServerCommunication ) {

	    stopManagement();
        mFromServerCommunication = aServerCommunication;
        
    }

	public void setArtificialIntelligence( ArtificialIntelligence aBotAI ){
		
		mCurrentBotAI = aBotAI;
		
	}
	
	@Override
	public void start(){
		
		startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{

		if( mFromServerCommunication != null && !isAlive() ) {
			
			mManageMessagesFromServer = true;
			super.start();
			
		} else {
			
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting FromServerManagement." ) ;
			
		}
		
	}
	
	public void stopManagement(){
		
		mManageMessagesFromServer = false;
		while(isAlive()){ 
		    try {
                Thread.sleep( 10 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            } 
        }
		
	}
	
	public void run(){
		
		while( mManageMessagesFromServer ){
			
			try {
			
				if( mCurrentBotAI != null ) {
					
					if( mFromServerCommunication != null ) {
						
						mCurrentBotAI.putWorldState( RawWorldData.createRawWorldDataFromXML( mFromServerCommunication.getDatagramm( 1000 ) ) );
						mLastReceivedMessage.set( System.currentTimeMillis() );
						
					} else {
						
						throw new NullPointerException( "NetworkCommunication cannot be NULL when running FromServerManagement." ) ;
						
					}
					
				} else {
					
					throw new NullPointerException( "Without actual AI all messages from the server will be discarded." ); // Change Exception 
					
				}
				
			} catch ( SocketTimeoutException e ) {
                
                // Logging einbauen!!!
			    System.out.println("Keine Nachrichten vom Server! " + System.currentTimeMillis() );
                
            } catch ( Exception e ) {
                
                // Logging einbauen!!!
                e.printStackTrace();
                
            }
			
		}
		
	}

    public boolean isReceivingMessages(){
        
        return (isAlive() && (System.currentTimeMillis() - mLastReceivedMessage.get()) < 100);
        
    }
    
}
