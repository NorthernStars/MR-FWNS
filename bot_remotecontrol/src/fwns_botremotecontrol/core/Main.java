package fwns_botremotecontrol.core;

import java.lang.management.ManagementFactory;

public class Main {
    
    public static void main( String[] aCommandline ) {
        
        System.setProperty("RemoteControl", ManagementFactory.getRuntimeMXBean().getName() + "" );
        Core.getLogger().info("Starting RemoteControl(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        
        Core RemoteControlCore;
        try {
            
            RemoteControlCore = Core.getInstance();
            
            RemoteControlCore.startBot();
            
        } catch ( Exception e ) {

            Core.getLogger().fatal( "Fatal error! RemoteControl terminates. ", e );
            
        }
       
    }
   
}
