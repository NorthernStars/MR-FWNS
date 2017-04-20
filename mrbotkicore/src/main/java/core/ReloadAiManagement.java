package core;

public class ReloadAiManagement extends Thread{

    private static ReloadAiManagement INSTANCE;
    
    private ReloadAiManagement(){
        
        this.setName( "RestartAiManagement" );
        
    }

    public static ReloadAiManagement getInstance() {
        
        if( ReloadAiManagement.INSTANCE == null){
            ReloadAiManagement.INSTANCE = new ReloadAiManagement();
        }
        
        return ReloadAiManagement.INSTANCE;
        
    }
    
    public void close(){
        
        Core.getLogger().info( "RestartAiServerManagement closing." );
        if( ReloadAiManagement.INSTANCE != null ) {
            
            stopManagement();
            ReloadAiManagement.INSTANCE = null;
            
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
	
	@Override
	public void run(){
		
		while( mAiActive ){
			
		    if(Core.getInstance().getAI() != null && Core.getInstance().getAI().wantRestart()){

                Core.getInstance().initializeAI();
                Core.getInstance().resumeAI();
		        
		    }
		    
		    try {
		        Thread.sleep( 1000 );
		    } catch ( InterruptedException e ) {
		        
		        Core.getLogger().error( "Error while waiting in RestartAiServerManagement.", e );
                
            } 
		}
		
	}
    
}
