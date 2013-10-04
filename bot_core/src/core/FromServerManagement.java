package core;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import essentials.communication.worlddata_server2008.RawWorldData;

@ThreadSafe
public class FromServerManagement extends Thread{

    private static FromServerManagement INSTANCE;
    
    private FromServerManagement(){
        
    }

    public static FromServerManagement getInstance() {
        
        if( FromServerManagement.INSTANCE == null){
            FromServerManagement.INSTANCE = new FromServerManagement();
        }
        
        return FromServerManagement.INSTANCE;
        
    }
    
    public void close(){
        
        if(FromServerManagement.INSTANCE != null) {
            
            getInstance().stopManagement();
            FromServerManagement.INSTANCE = null;
            Core.getLogger().info( "FromServerManagement closed." );
            
        }
        
    }
    
	@GuardedBy("this") private boolean mManageMessagesFromServer = false;
    volatile private AtomicLong mLastReceivedMessage = new AtomicLong( 0 );
	
	@Override
	public void start(){
		
		startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{

		if( Core.getInstance().getServerConnection() != null && !isAlive() ) {
			synchronized (this) {
			    
			    mManageMessagesFromServer = true;
			    
			}
			super.start();
	        Core.getLogger().info( "FromServerManagement started." );
			
		} else {
			
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting FromServerManagement." ) ;
			
		}
		
	}
	
	public void stopManagement(){
	    synchronized (this) {
            
	        mManageMessagesFromServer = false;
		
	    }
	    if( isAlive()){
	          
	        while(isAlive()){ 
    		    try {
                    Thread.sleep( 10 );
                } catch ( InterruptedException e ) {
    
                    Core.getLogger().error( "Error stopping FromServerManagement.", e );
    
                } 
	        }
	        
	        Core.getLogger().info( "FromServerManagement stopped." );
	        
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
                
			    Core.getLogger().error( "Receiving no messages from server ", e );
                
            } catch ( Exception e ) {
                
                Core.getLogger().error( "Error receiving messages from server ", e );
                
            }
			
		}
		
	}

    public boolean isReceivingMessages(){
        
        return (isAlive() && (System.currentTimeMillis() - mLastReceivedMessage.get()) < 100);
        
    }
    
}
