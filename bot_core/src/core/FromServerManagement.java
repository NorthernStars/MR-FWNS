package core;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Level;

import remotecontrol.RemoteControlServer;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.communication.WorldDataInterpreter;
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
    @GuardedBy("this") private boolean mSuspended = false;
    volatile private AtomicLong mLastReceivedMessage = new AtomicLong( 0 );

    public void resumeManagement(){
        
        Core.getLogger().info( "FromServerManagement resumed." );
        mSuspended = false;
        
    }
    
    public void suspendManagement(){
        
        Core.getLogger().info( "FromServerManagement suspended." );
        mSuspended = true;
        
    }
	
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
		
        boolean vStatusChanged = false;
	    
		while( mManageMessagesFromServer ){
			
		    while( mSuspended ){ try { this.wait( 10 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }
		    
			try {
			
				if( Core.getInstance().getAI() != null ) {
					
					if( Core.getInstance().getServerConnection() != null ) {
						
					    Core.getInstance().getAI().putWorldState( WorldDataInterpreter.unmarshall( Core.getInstance().getServerConnection().getDatagramm( 1000 ) ) );
						mLastReceivedMessage.set( System.currentTimeMillis() );
						vStatusChanged = false;
						
					} else {
						
					    Core.getLogger().debug( "NetworkCommunication cannot be NULL when running FromServerManagement." ) ;
						
					}
					
				} else {
					
				    Core.getLogger().debug( "Without actual AI all messages from the server will be discarded." );
					
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
                
                if( !vStatusChanged ){RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic ); vStatusChanged = true;}
                
            }
			
		}
		
	}

    public boolean isReceivingMessages(){
        
        return (isAlive() && (System.currentTimeMillis() - mLastReceivedMessage.get()) < 100);
        
    }
    
}
