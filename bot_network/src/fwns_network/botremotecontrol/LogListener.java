package fwns_network.botremotecontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.apache.logging.log4j.Level;

/**
 * LogListener zum uebertragen der Logevents des Bots an die Remotekontrolle. 
 * 
 * @author Eike Petersen
 * @since 0.9
 * @version 0.9
 *
 */
public interface LogListener extends Remote{
    
    /**
     * Sendet ein Logevent mit dem angegeben Loglevel 
     * 
     * @since 0.9
     * @param aLogEvent die zu senden Logmeldung
     * @param aLogLevel das LogLevel des Events
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public void logEvent( String aLogEvent, Level aLogLevel ) throws RemoteException;
    
}
