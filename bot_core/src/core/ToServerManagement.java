package core;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Level;

import remotecontrol.RemoteControlServer;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.core.BotInformation.GamevalueNames;
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
    volatile private AtomicLong mLastSendMessage = new AtomicLong( 0 );
    	
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
	    
	    Action vCurrentAction = null;
	    
		while( mManageMessagesToServer ){
			
			try {
				
				Thread.sleep(66); // besser machen!
				
				if( Core.getInstance().getServerConnection() != null ) {
					
					if( Core.getInstance().getAI() != null ) {

					    vCurrentAction = Core.getInstance().getAI().getAction();
					    Core.getInstance().getServerConnection().sendDatagramm( vCurrentAction );
                        mLastSendMessage.set( System.currentTimeMillis() );
                        
						
					} else {

					    Core.getInstance().getServerConnection().sendDatagramm( (Action) Movement.NO_MOVEMENT );
						Core.getLogger().info( "Without actual AI only empty messages will be sent to the Server." );
												
					}
					
				} else {
				    
					throw new NullPointerException( "NetworkCommunication cannot be NULL when running ToServerManagement." ) ;
					
				}
				
			} catch ( Exception vException ) {
				
                Core.getLogger().error( "Error sending messages to server " + vException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vException );
                				
			}
			
			if( (System.currentTimeMillis() - mLastSendMessage.get() ) > 132 ){ //TODO: dynamisch mit der Zeit eines Ticks verbinden
			    
			    RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic );
			    
			}
			
		}
		
	}
	
	public boolean isSendingMessages(){
	    
	    return (isAlive() && (System.currentTimeMillis() - mLastSendMessage.get() ) < 100);
	    
	}
	
}
