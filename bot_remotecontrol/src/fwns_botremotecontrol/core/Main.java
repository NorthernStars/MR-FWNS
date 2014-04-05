package fwns_botremotecontrol.core;

import java.lang.management.ManagementFactory;

import essentials.core.BotInformation;

public class Main {
    
    public static void main( String[] aCommandline ) {
        
    	BotLoader botloader = new BotLoader(new BotInformation());
    	Core.getLogger().info("loaded bot: " + botloader.startBot());
    	
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
