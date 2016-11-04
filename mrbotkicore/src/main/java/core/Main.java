package core;

import java.lang.management.ManagementFactory;

public class Main {
    
    public static void main( String[] aCommandline ) {
        
        System.setProperty("Bot", ManagementFactory.getRuntimeMXBean().getName() + "" );
        Core.getLogger().info("Starting Bot(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        
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
