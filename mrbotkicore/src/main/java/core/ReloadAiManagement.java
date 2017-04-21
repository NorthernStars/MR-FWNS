package core;

public class ReloadAiManagement extends Thread{

    private static ReloadAiManagement sINSTANCE;
    
    ReloadAiManagement(){
        
        this.setName( "RestartAiManagement" );
        
    }

    public static ReloadAiManagement getInstance() {
        
        if( ReloadAiManagement.sINSTANCE == null){
            ReloadAiManagement.sINSTANCE = new ReloadAiManagement();
        }
        
        return ReloadAiManagement.sINSTANCE;
        
    }
    
    static synchronized void setInstanceNull(){
    	ReloadAiManagement.sINSTANCE = null;
    }
    
    public void close(){
        
        if( ReloadAiManagement.sINSTANCE != null ) {
            
            stopManagement();
            setInstanceNull();
            Core.getLogger().info( "RestartAiServerManagement closed." );
            
        }
        
    }
    
	private boolean mAiActive = false;

    @Override
	public void start(){
		
		startManagement();
		
	}
	
	public void startManagement(){
	    
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
                } catch ( Exception vException ) {
                    Core.getLogger().error( "Error stopping RestartAiServerManagement.", vException );
                } 
            }
		    
		    Core.getLogger().info( "RestartAiServerManagement stopped." );
		    
		}
		
	}
	
	@Override
	public void run(){
		
		while( mAiActive ){

		    try {
		    
		    	if(Core.getInstance().getAI() != null && Core.getInstance().getAI().wantRestart()){

	                Core.getInstance().initializeAI();
	                Core.getInstance().resumeAI();
	                
	    		    Thread.sleep( 500 );
			    }
		    	
			    Thread.sleep( 50 );
			    
		    } catch ( Exception vException ) {
		        Core.getLogger().error( "Error while waiting in RestartAiServerManagement.", vException );
            } 
		}
		
	}
    
}
