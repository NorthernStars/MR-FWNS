package core;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Level;

import remotecontrol.RemoteControlServer;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.communication.worlddata_server2008.RawWorldData;
import fwns_network.botremotecontrol.BotStatusType;

@ThreadSafe
public class FromServerManagement extends Thread{

    private static FromServerManagement INSTANCE;
    
    private FromServerManagement(){
        
        this.setName( "FromServerManagement" );
        
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
				
			} catch ( SocketTimeoutException vSocketTimeoutException ) {
                
			    Core.getLogger().error( "Receiving no messages from server " + vSocketTimeoutException.getLocalizedMessage() );
	            Core.getLogger().catching( Level.ERROR, vSocketTimeoutException );
	            
		        RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkIncomingTraffic );
                
            } catch ( Exception vException ) {
                
                Core.getLogger().error( "Error receiving messages from server " + vException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vException );
                
            }
			
			if( (System.currentTimeMillis() - mLastReceivedMessage.get() ) > 132 ){ //TODO: dynamisch mit der Zeit eines Ticks verbinden
                
                RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkIncomingTraffic );
                
            }
			
		}
		
	}

    public boolean isReceivingMessages(){
        
        return (isAlive() && (System.currentTimeMillis() - mLastReceivedMessage.get()) < 100);
        
    }
    
}
