package core;

import java.rmi.RemoteException;

import org.apache.logging.log4j.Level;

import fwns_network.botremotecontrol.LogListener;

public class LogListenerImplementation implements LogListener {

    @Override
    public void logEvent( String aLogEvent, Level aLogLevel ) throws RemoteException{
        
        System.out.println( aLogEvent );

    }

}
