package core;

import java.io.IOException;
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

    private static ToServerManagement sINSTANCE;
    
    ToServerManagement(){
        
        this.setName( "ToServerManagement" );
        
    }

    public static ToServerManagement getInstance() {
        
        if( ToServerManagement.sINSTANCE == null){
            ToServerManagement.sINSTANCE = new ToServerManagement();
        }
        
        return ToServerManagement.sINSTANCE;
        
    }
    
    static synchronized void setInstanceNull(){
    	ToServerManagement.sINSTANCE = null;
    }
    
    
    public void close(){
        
        if(ToServerManagement.sINSTANCE != null) {
            
            getInstance().stopManagement();
            setInstanceNull();
            Core.getLogger().info( "ToServerManagement closed." );
            
        }
        
        
    }
    
	@GuardedBy("this") private boolean mManageMessagesToServer = false;
    @GuardedBy("this") private boolean mSuspended = false;
    private volatile AtomicLong mLastSendMessage = new AtomicLong( 0 );

    public void resumeManagement(){
        
        Core.getLogger().info( "ToServerManagement resumed." );
        mSuspended = false;
        
    }
    
    public boolean isSuspended(){
        
        return mSuspended;
        
    }
    
    public void suspendManagement(){
        
        Core.getLogger().info( "ToServerManagement suspended." );
        mSuspended = true;
        
    }
    
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void startManagement(){
		
		if( Core.getInstance().getServerConnection() == null ) {
			
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting ToServerManagement." ) ;
			
		} else if ( isAlive() ){
			
			throw new IllegalThreadStateException( "ToServerManagement can not be started again." );
			
		} else {
		    
			synchronized (this) {
	             
		        mManageMessagesToServer = true;
		    
		    }
			super.start();
            Core.getLogger().info( "ToServerManagement started." );
			
		}
		
	}
	
	public void stopManagement(){
	 
	    synchronized (this) {
            
	        mManageMessagesToServer = false;
	    
	    }
	    
	    if( isAlive()){
	      
	    	resumeManagement();
	    	
            while(isAlive()){ 
                try {
                    Thread.sleep( 10 );
                } catch ( Exception vException ) {
    
                    Core.getLogger().error( "Error stopping ToServerManagement.", vException );
                    
                } 
            }
            
            Core.getLogger().info( "ToServerManagement stopped." );
	    
	    }
		
	}
	
	@Override
	public void run(){
	    
	    Action vOldAction = null;
	    
		while( mManageMessagesToServer ){
            
			try {
				
				 // besser machen!
				
				if( Core.getInstance().getServerConnection() != null ) {
					
					vOldAction = sendMassage(vOldAction);
					
				} else {
				    
				    Core.getLogger().debug( "NetworkCommunication cannot be NULL when running ToServerManagement." ) ;
				    Thread.sleep(66);
					
				}
				
			} catch ( Exception vException ) {
				
                Core.getLogger().error( "Error sending messages to server " + vException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vException ); 
                mStatusChanged = true;
                				
			}
			
			notifyControlServer();
			
			while( mSuspended ){ 
				try { 
					Thread.sleep( 10 ); 
				} catch ( Exception vException ) { 
					Core.getLogger().error( "Error suspending ToServerManagement.", vException ); 
				} 
			}
			
			
		}
		
	}

	private Action sendMassage(Action vOldAction) throws IOException {
		Action vCurrentAction;
		if( Core.getInstance().getAI() != null ) {

		    while( (vCurrentAction = Core.getInstance().getAI().getAction()) == vOldAction && mManageMessagesToServer ){ 
		    	try {
					Thread.sleep( 0, 100 );
				} catch ( Exception vException ) {
					Core.getLogger().error( "Error waiting for new action.", vException ); 
				} 
		    }
		    if( vCurrentAction != null ){
			    Core.getLogger().debug( "Sending Action {} over {}.", vCurrentAction.getXMLString(), Core.getInstance().getServerConnection().toString() );
			    Core.getInstance().getServerConnection().sendDatagramm( vCurrentAction );
		    
		    
			    mLastSendMessage.set( System.currentTimeMillis() );
			    mStatusChanged = true;
			    
			    return vCurrentAction;
		    }
		    
		} else {

		    Core.getInstance().getServerConnection().sendDatagramm( Movement.NO_MOVEMENT );
			Core.getLogger().debug( "Without actual AI only empty messages will be sent to the Server." );
			mStatusChanged = true;
			
		}
		return vOldAction;
	}

	//TODO: This is not tested and pretty bad -> make it better
    boolean mStatusChanged = false;
    private void notifyControlServer(){
    	if( (System.currentTimeMillis() - mLastSendMessage.get() ) > 132 && mStatusChanged ){
    		RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic ); 
    		mStatusChanged = false;
        }
    }
	
	public boolean isSendingMessages(){
	    
	    return isAlive() && (System.currentTimeMillis() - mLastSendMessage.get() ) < 100;
	    
	}
	
}
