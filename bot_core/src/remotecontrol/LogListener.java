package remotecontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogListener extends Remote{
    
    public void logEvent( String aLogEvent ) throws RemoteException;
    
}
