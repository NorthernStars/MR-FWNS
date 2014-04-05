package fwns_botremotecontrol.core;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

import essentials.core.BotInformation;
import fwns_network.botremotecontrol.RemoteControlInterface;

public class RemoteControlClient {
 
    public static void main(String args[]) throws Exception {

        String[] names = Naming.list("//localhost:1099/");
        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);
        
        final LogListenerImplementation listener = new LogListenerImplementation();
        UnicastRemoteObject.exportObject(listener, 0);
        
        RemoteControlInterface obj = (RemoteControlInterface)Naming.lookup("//localhost:1099/MyBot-0-0");
        
        System.out.println( ( (BotInformation) obj.getBotInformation() ).getBotname() );
        System.out.println( obj.getBotInformation().toString() );
        BotInformation vBotInformation = (BotInformation) obj.getBotInformation();
        vBotInformation.setBotname( "farts" );
        obj.setBotInformation( vBotInformation ); 
        System.out.println( obj.getBotInformation().getBotname() );
        System.out.println( obj.getBotInformation().toString() );
        
        obj.registerLogListener( listener );

        names = Naming.list("//localhost:1099/");
        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);
        
        obj.unregisterLogListener( -1 );
        obj.closeBot();

        names = Naming.list("//localhost:1099/");
        for (int i = 0; i < names.length; i++)
            System.out.println("->" + names[i]);
        
        UnicastRemoteObject.unexportObject(listener, true);
        
    }

}
