package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    
    private static Logger BOTCORELOGGER = LogManager.getLogger("CORE");
    
    public static Logger getCORELogger(){
        
        return BOTCORELOGGER;
        
    }
    
    public static void main( String[] aCommandline ) {

        getCORELogger().info("Starting Bot(" + Thread.currentThread().getId() + ")" );
        
        {
            String vParameters = "";
            
            for ( String vParameter: aCommandline) {
                
                vParameters += vParameter + " ";
                
            }
            
            getCORELogger().info("Parameters: " + vParameters);
        }
        
        Core Botcore;
        try {
            
            Botcore = Core.getInstance();
            
            Botcore.startBot( aCommandline );
            
        } catch ( Exception e ) {

            getCORELogger().error( "Fatal error! Bot terminates. ", e );
            
        }
       
    }
   
}
