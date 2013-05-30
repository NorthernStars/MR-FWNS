package core;

public class RestartAiManagement extends Thread{

    private static RestartAiManagement INSTANCE;
    
    private RestartAiManagement(){
        
    }

    public static RestartAiManagement getInstance() {
        
        if( RestartAiManagement.INSTANCE == null){
            RestartAiManagement.INSTANCE = new RestartAiManagement();
        }
        
        return RestartAiManagement.INSTANCE;
        
    }
    
    public void close(){
        
        if(RestartAiManagement.INSTANCE != null) {
            
            getInstance().stopManagement();
            RestartAiManagement.INSTANCE = null;
            
        }
        
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
	    
	    }
        
	}
	
	public void stopManagement(){
		
		mAiActive = false;
		while(isAlive()){ 
		    try {
                Thread.sleep( 10 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            } 
        }
		
	}
	
	public void run(){
		
		while( mAiActive ){
			
		    if(Core.getInstance().getAI() != null && Core.getInstance().getAI().wantRestart()){
		        
		        Core.getInstance().startAI();
		        
		    }
		    
		    try {
		        Thread.sleep( 100 );
		    } catch ( InterruptedException e ) {
                e.printStackTrace();
            } 
		}
		
	}
    
}
