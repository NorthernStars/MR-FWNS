package core;

public class RestartAiManagement extends Thread{

    private static RestartAiManagement INSTANCE;
    
    private RestartAiManagement(){
        
        this.setName( "RestartAiManagement" );
        
    }

    public static RestartAiManagement getInstance() {
        
        if( RestartAiManagement.INSTANCE == null){
            RestartAiManagement.INSTANCE = new RestartAiManagement();
        }
        
        return RestartAiManagement.INSTANCE;
        
    }
    
    public void close(){
        
        Core.getLogger().info( "RestartAiServerManagement closing." );
        if( RestartAiManagement.INSTANCE != null ) {
            
            stopManagement();
            RestartAiManagement.INSTANCE = null;
            
        }
        Core.getLogger().info( "RestartAiServerManagement closed." );
        
    }
    
	private boolean mAiActive = false;

    @Override
	public void start(){
		
		startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{
	    
	    if( !isAlive() ){
	    
	        mAiActive = true;
	        super.start();
	        Core.getLogger().info( "RestartAiServerManagement started." );
	    
	    }
        
	}
	
	public void stopManagement(){
		
		mAiActive = false;
		
		if( isAlive()){
		    
		    Core.getLogger().info( "RestartAiServerManagement stopping." );
		
		    while(isAlive()){
		        
    		    try {
                    Thread.sleep( 10 );
                } catch ( InterruptedException e ) {
    
                    Core.getLogger().error( "Error stopping RestartAiServerManagement.", e );
                    
                } 
            }
		    
		    Core.getLogger().info( "RestartAiServerManagement stopped." );
		    
		}
		
	}
	
	public void run(){
		
		while( mAiActive ){
			
		    if(Core.getInstance().getAI() != null && Core.getInstance().getAI().wantRestart()){
		        
		        Core.getInstance().initializeAI();
		        
		    }
		    
		    try {
		        Thread.sleep( 100 );
		    } catch ( InterruptedException e ) {
		        
		        Core.getLogger().error( "Error while waiting in RestartAiServerManagement.", e );
                
            } 
		}
		
	}
    
}
