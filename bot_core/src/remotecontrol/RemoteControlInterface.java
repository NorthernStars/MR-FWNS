package remotecontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

import essentials.core.BotInformation;

/**
 * Interface der Funktionen, die die Remotekontrolle ausführen kann. 
 * 
 * @author Eike Petersen
 * @since 0.9
 * @version 0.9
 *
 */
public interface RemoteControlInterface extends Remote {

    /**
     * Setzt eine neue Botinformation 
     * 
     * @since 0.9
     * @param BotInformationt Objekt mit den allgemeinen Daten des Bots
     */
    public void setBotInformation( BotInformation aBotinformation ) throws RemoteException;
    
    /**
     * Gibt das momentane Botinformationsobjekt des Bots zurück 
     * 
     * @since 0.9
     * 
     * @return die aktuelle Botinformation
     */
    public BotInformation getBotInformation() throws RemoteException;
    
    /*
    public boolean[] getStatus() throws RemoteException;
    
    public boolean connectBot() throws RemoteException;
    
    public boolean reconnectBot() throws RemoteException;
    
    public boolean disconnectBot() throws RemoteException;
    
    public boolean initialiseAI() throws RemoteException;
    
    public boolean startAI() throws RemoteException;
    
    public boolean stopAI() throws RemoteException;
    */
    public boolean registerLogListener(LogListener aLoglistener) throws RemoteException;
    
    /**
     * Gibt den Punkt in der Mitte von zwei Referenzpunkten als neuen Referenzpunkt zurueck.
     * 
     * Dabei wird zwischen den beiden Punkten eine Line errechnet und 
     * in der Mitte dieser ein neuer Refenzpunkt erstellt. 
     * 
     * @since 0.9
     * @param aFirstReferencePoint der erste Referenzpunkt
     * @param aSecondReferencePoint der zweite Referenzpunkt
     * 
     * @return einen ReferencePoint mit den Koordinaten des Mittelpunkts der Parameter
     */
    
}
