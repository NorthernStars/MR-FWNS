package remotecontrol;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;

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

        System.out.println();
        obj.setLogLevel( Level.ALL );
        Thread.sleep( 3000 );
        System.out.println();
        obj.setLogLevel( Level.DEBUG );
        Thread.sleep( 3000 );
        System.out.println();
        obj.setLogLevel( Level.TRACE );
        Thread.sleep( 3000 );
        System.out.println();
        obj.setLogLevel( Level.ERROR );
        Thread.sleep( 3000 );
        System.out.println();
        obj.setLogLevel( Level.INFO );
        Thread.sleep( 3000 );
        System.out.println();
        obj.setLogLevel( Level.WARN );
        Thread.sleep( 3000 );

        names = Naming.list("//localhost:1099/");
        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);
        
        while( true ){ Thread.sleep( 1 ); }
        
    }

}
