package core;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import essentials.communication.worlddata_server2008.RawWorldData;

public class FromServerManagement extends Thread{

	private boolean mManageMessagesFromServer = false;
    private AtomicLong mLastReceivedMessage = new AtomicLong( 0 );
	
	@Override
	public void start(){
		
		startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{

		if( Core.getInstance().getServerConnection() != null && !isAlive() ) {
			
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
			
				if( Core.getInstance().getAI() != null ) {
					
					if( Core.getInstance().getServerConnection() != null ) {
						
					    Core.getInstance().getAI().putWorldState( RawWorldData.createRawWorldDataFromXML( Core.getInstance().getServerConnection().getDatagramm( 1000 ) ) );
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
