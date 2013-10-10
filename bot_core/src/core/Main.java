package core;

import org.apache.logging.log4j.Logger;

public class Main {
    
    public static void main( String[] aCommandline ) {

        Core.getLogger().info("Starting Bot(" + Thread.currentThread().getId() + ")" );
        
        {
            String vParameters = "";
            
            for ( String vParameter: aCommandline) {
                
                vParameters += vParameter + " ";
                
            }
            
            Core.getLogger().info("Parameters: " + vParameters);
        }
        
        Core Botcore;
        try {
            
            Botcore = Core.getInstance();
            
            Botcore.startBot( aCommandline );
            
        } catch ( Exception e ) {

            Core.getLogger().fatal( "Fatal error! Bot terminates. ", e );
            
        }
       
    }
   
}
