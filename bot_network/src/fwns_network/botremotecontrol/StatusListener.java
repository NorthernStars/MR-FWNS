package fwns_network.botremotecontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * StausListener zum Benachrichtigen der Remotekontrolle ueber Statusveraenderungen. 
 * 
 * @author Eike Petersen
 * @since 0.9
 * @version 0.9
 *
 */
public interface StatusListener extends Remote {
    
    /**
     * Benachrichtigt den StatusListener ueber den veraenderten Status
     * 
     * @since 0.9
     * @param aBotStatus der veraenderte Status
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public void changedStatus( BotStatusType aBotStatus ) throws RemoteException;

}
