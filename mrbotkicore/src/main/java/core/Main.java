package core;

import java.lang.management.ManagementFactory;

public class Main {
    
    public static void main( String[] aCommandline ) {
        
        System.setProperty("Bot", ManagementFactory.getRuntimeMXBean().getName() + "" );
        Core.getLogger().info("Starting Bot(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        
        StringBuilder vParameters = new StringBuilder();
        for ( String vParameter: aCommandline) {
            vParameters.append(vParameter).append(" "); 
        }
        Core.getLogger().info("Parameters: " + vParameters.toString());
        
        try {
            
        	Core.getInstance().startBot( aCommandline );
            
        } catch ( Exception e ) {

            Core.getLogger().fatal( "Fatal error! Bot terminates. ", e );
            
        }
       
    }
   
}
