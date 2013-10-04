package core;

import java.util.concurrent.atomic.AtomicLong;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;

@ThreadSafe
public class ToServerManagement extends Thread{

    private static ToServerManagement INSTANCE;
    
    private ToServerManagement(){
        
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
    
	@GuardedBy("this") private boolean mManageMessagesFromServer = false;
    volatile private AtomicLong mLastSendMessage = new AtomicLong( 0 );
    	
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{
		
		if( Core.getInstance().getServerConnection() != null && !isAlive() ) {
		 
		    synchronized (this) {
	             
		        mManageMessagesFromServer = true;
		    
		    }
			super.start();
            Core.getLogger().info( "ToServerManagement started." );
			
		} else {
		    
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting ToServerManagement." ) ;
			
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
    
                    Core.getLogger().error( "Error stopping ToServerManagement.", e );
                    
                } 
            }
            
            Core.getLogger().info( "ToServerManagement stopped." );
	    
	    }
		
	}
	
	public void run(){
	    
	    Action vCurrentAction = null;
	    
		while( mManageMessagesFromServer ){
			
			try {
				
				Thread.sleep(66); // besser machen!
				
				if( Core.getInstance().getServerConnection() != null ) {
					
					if( Core.getInstance().getAI() != null ) {

					    vCurrentAction = Core.getInstance().getAI().getAction();
					    Core.getInstance().getServerConnection().sendDatagramm( vCurrentAction );
                        mLastSendMessage.set( System.currentTimeMillis() );
                        
						
					} else {

					    Core.getInstance().getServerConnection().sendDatagramm( (Action) Movement.NO_MOVEMENT );
						throw new NullPointerException( "Without actual AI only empty messages will be sent to the Server." ); // Change Exception 
												
					}
					
				} else {
					
					throw new NullPointerException( "NetworkCommunication cannot be NULL when running ToServerManagement." ) ;
					
				}
				
			} catch ( Exception e ) {
				
                Core.getLogger().error( "Error sending messages to server ", e );
				
			}
			
		}
		
	}
	
	public boolean isSendingMessages(){
	    
	    return (isAlive() && (System.currentTimeMillis() - mLastSendMessage.get() ) < 100);
	    
	}
	
}
