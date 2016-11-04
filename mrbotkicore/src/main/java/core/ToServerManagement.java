package core;

import java.util.concurrent.atomic.AtomicLong;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.apache.logging.log4j.Level;

import remotecontrol.RemoteControlServer;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import fwns_network.botremotecontrol.BotStatusType;

@ThreadSafe
public class ToServerManagement extends Thread{

    private static ToServerManagement INSTANCE;
    
    private ToServerManagement(){
        
        this.setName( "ToServerManagement" );
        
    }

    public static ToServerManagement getInstance() {
        
        if( ToServerManagement.INSTANCE == null){
            ToServerManagement.INSTANCE = new ToServerManagement();
        }
        
        return ToServerManagement.INSTANCE;
        
    }
    
    public void close(){
        
        if(ToServerManagement.INSTANCE != null) {
            
            getInstance().stopManagement();
            ToServerManagement.INSTANCE = null;
            
        }
        Core.getLogger().info( "ToServerManagement closed." );
        
    }
    
	@GuardedBy("this") private boolean mManageMessagesToServer = false;
    @GuardedBy("this") private boolean mSuspended = false;
    volatile private AtomicLong mLastSendMessage = new AtomicLong( 0 );

    public void resumeManagement(){
        
        Core.getLogger().info( "ToServerManagement resumed." );
        mSuspended = false;
        
    }
    
    public void suspendManagement(){
        
        Core.getLogger().info( "ToServerManagement suspended." );
        mSuspended = true;
        
    }
    
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{
		
		if( Core.getInstance().getServerConnection() != null && !isAlive() ) {
		 
		    synchronized (this) {
	             
		        mManageMessagesToServer = true;
		    
		    }
			super.start();
            Core.getLogger().info( "ToServerManagement started." );
			
		} else {
		    
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting ToServerManagement." ) ;
			
		}
		
	}
	
	public void stopManagement(){
	 
	    synchronized (this) {
            
	        mManageMessagesToServer = false;
	    
	    }
	    
	    if( isAlive()){
	      
            while(isAlive()){ 
                try {
                    Thread.sleep( 10 );
                } catch ( InterruptedException e ) {
    
                    Core.getLogger().error( "Error stopping ToServerManagement.", e );
                    
                } 
            }
            
            Core.getLogger().info( "ToServerManagement stopped." );
	    
	    }
		
	}
	
	public void run(){
	    
	    Action vCurrentAction = null, vOldAction = null;
	    boolean vStatusChanged = false; //TODO: musch
	    
		while( mManageMessagesToServer ){
            
            while( mSuspended ){ try { this.wait( 10 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }
			
			try {
				
				 // besser machen!
				
				if( Core.getInstance().getServerConnection() != null ) {
					
					if( Core.getInstance().getAI() != null ) {

					    while( (vCurrentAction = Core.getInstance().getAI().getAction()) == vOldAction ){ Thread.sleep( 0, 100 ); };
					    Core.getLogger().debug( "Sending Action {} over {}.", vCurrentAction.getXMLString(), Core.getInstance().getServerConnection().toString() );
					    Core.getInstance().getServerConnection().sendDatagramm( vCurrentAction );
					    vOldAction = vCurrentAction;
                        mLastSendMessage.set( System.currentTimeMillis() );
                        vStatusChanged = false;
                        
						
					} else {

					    Core.getInstance().getServerConnection().sendDatagramm( (Action) Movement.NO_MOVEMENT );
						Core.getLogger().debug( "Without actual AI only empty messages will be sent to the Server." );
						if( !vStatusChanged ){RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic ); vStatusChanged = true;}
												
					}
					
				} else {
				    
				    Core.getLogger().debug( "NetworkCommunication cannot be NULL when running ToServerManagement." ) ;
				    Thread.sleep(66);
					
				}
				
			} catch ( Exception vException ) {
				
                Core.getLogger().error( "Error sending messages to server " + vException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vException );
                				
			}
			
			if( (System.currentTimeMillis() - mLastSendMessage.get() ) > 132 ){ //TODO: dynamisch mit der Zeit eines Ticks verbinden
			    
			    if( !vStatusChanged ){RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic ); vStatusChanged = true;}
                
			}
			
		}
		
	}
	
	public boolean isSendingMessages(){
	    
	    return (isAlive() && (System.currentTimeMillis() - mLastSendMessage.get() ) < 100);
	    
	}
	
}
