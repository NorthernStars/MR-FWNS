package remotecontrol;

import java.rmi.RemoteException;

public class LogListenerImplementation implements LogListener {

    @Override
    public void logEvent( String aLogEvent ) throws RemoteException{
        
        System.out.println( aLogEvent );

    }

}
