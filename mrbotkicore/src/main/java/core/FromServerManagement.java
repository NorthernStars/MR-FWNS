package core;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.apache.logging.log4j.Level;

import remotecontrol.RemoteControlServer;
import essentials.communication.WorldDataInterpreter;
import fwns_network.botremotecontrol.BotStatusType;

@ThreadSafe
public class FromServerManagement extends Thread{

    private static FromServerManagement sINSTANCE;
    
    FromServerManagement(){
        
        this.setName( "FromServerManagement" );
        
    }

    public static FromServerManagement getInstance() {
        
        if( FromServerManagement.sINSTANCE == null){
            FromServerManagement.sINSTANCE = new FromServerManagement();
        }
        
        return FromServerManagement.sINSTANCE;
        
    }
    
    public void close(){
        
        if(FromServerManagement.sINSTANCE != null) {
            
            getInstance().stopManagement();
            FromServerManagement.sINSTANCE = null;
            Core.getLogger().info( "FromServerManagement closed." );
            
        }
        
    }

    @GuardedBy("this") private boolean mManageMessagesFromServer = false;
    @GuardedBy("this") private boolean mSuspended = false;
    private volatile AtomicLong mLastReceivedMessage = new AtomicLong( 0 );

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
	
	public void startManagement(){

		if( Core.getInstance().getServerConnection() == null ) {
			
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting FromServerManagement." ) ;
			
		} else if ( isAlive() ){
			
			throw new IllegalThreadStateException( "FromServerManagement can not be started again." );
			
		} else {
			synchronized (this) {
			    
			    mManageMessagesFromServer = true;
			    
			}
			super.start();
	        Core.getLogger().info( "FromServerManagement started." );
		}
		
	}
	
	public void stopManagement(){
	    synchronized (this) {
            
	        mManageMessagesFromServer = false;
		
	    }
	    if( isAlive()){
	        
	    	mSuspended = false;
	    	
	        while(isAlive()){ 
    		    try {
                    Thread.sleep( 10 );
                } catch ( Exception vException ) {
    
                    Core.getLogger().error( "Error stopping FromServerManagement.", vException );
    
                } 
	        }
	        
	        Core.getLogger().info( "FromServerManagement stopped." );
	        
	    }
		
	}
	
	@Override
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
