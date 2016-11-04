package fwns_network.botremotecontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;

/**
 * Interface der Funktionen, die die Remotekontrolle ausfuehren kann. 
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
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public void setBotInformation( BotInformation aBotinformation ) throws RemoteException;
    
    /**
     * Gibt das momentane Botinformationsobjekt des Bots zurueck 
     * 
     * @since 0.9
     * 
     * @return die aktuelle Botinformation
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public BotInformation getBotInformation() throws RemoteException;
    
    /**
     * Verbindet den Bot zu dem in BotInformation angegebenen Server und Teamport 
     * 
     * @since 0.9
     * 
     * @return ob die Verbindung erfolgreich hergestellt werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public boolean connectBot() throws RemoteException;
    
    /**
     * Verbindet den Bot zu dem in BotInformation angegebenen Server und Teamport 
     * ohne einen Handshake durchzufuehren. 
     * Ist sinnvoll fuer Debug und Testzwecke oder wenn der Bot neugestartet wurde
     * 
     * @since 0.9
     * 
     * @return ob die Verbindung erfolgreich hergestellt werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public boolean reconnectBot() throws RemoteException;
    
    /**
     * Unterbricht die Verbindung und das senden von Daten zum Server 
     * 
     * @since 0.9
     * 
     * @return ob die Verbindung erfolgreich geschlossen werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public boolean disconnectBot() throws RemoteException;
    
    /**
     * Initalisiert die AI. Sollte immer vor dem Starten der AI durchgefuert werden.
     * 
     * @since 0.9
     * 
     * @return ob die Initalisierung erfolgreich durchgefuehrt werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public boolean initialiseAI() throws RemoteException;
    
    /**
     * Unpausiert die AI. Vorher sollte die AI initalisiert werden. 
     * 
     * @since 0.9
     * 
     * @return ob die AI erfolgreich gestartet werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public boolean resumeAI() throws RemoteException;
    
    /**
     * Pausiert die laufende AI.
     * 
     * @since 0.9
     * 
     * @return ob die AI erfolgreich pausiert werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public void suspendAI() throws RemoteException;

    /**
     * Beendet und entfernt die laufende AI.
     * 
     * @since 0.9
     * 
     * @return ob die AI erfolgreich beendet werden konnte
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public void disposeAI() throws RemoteException;
    
    /**
     * Uebergibt den Befehl an die AI zum ausfuehren.
     * 
     * @since 0.9
     * 
     * @param aCommandString der auszufuehrende Befehl 
     * 
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird
     */
    public void executeCommandOnAI( String aCommandString ) throws RemoteException;
    
    /**
     * Meldet einen LogListener an dem Server an um die Logeintraege gesendet zu bekommen 
     * 
     * @since 0.9
     * @param aLogListener der anzumeldende LogListener
     * 
     * @return falls 0 -> Listener nicht angemeldet
     *         falls !0 -> Listener angemeldet, bytearray ist ein Identifier um Listener abzumelden
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public int registerLogListener( LogListener aLogListener ) throws RemoteException;
    
    /**
     * Meldet einen LogListener von dem Server ab 
     * 
     * @since 0.9
     * @param aListenerIdent Identifier des abzumeldenden LogListeners oder
     *                       0 wenn alle Listener abgemeldet werden sollen
     * 
     * @return ob der listener erfolgreich abgemeldet werden konnte
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird  
     */
    public boolean unregisterLogListener( int aListenerIdent ) throws RemoteException;
    
    /**
     * Fragt das momentane Loglevel des Servers ab 
     * 
     * @since 0.9
     * 
     * @return das LogLevel des Servers
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public Level getLogLevel() throws RemoteException;
    /**
     * Stellt das Loglevels des Servers ein 
     * 
     * @since 0.9
     * @param aLogLevel das gewuenschte LogLevel
     * 
     * @return ob das LogLevel erfolgreich eingestellt werden konnte oder nicht
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public boolean setLogLevel( Level aLogLevel ) throws RemoteException;
    
    /**
     * Meldet einen StatusListener an dem Server um Updates fuer Statusveraenderungen gesendet zu bekommen 
     * 
     * @since 0.9
     * @param aLogListener der anzumeldende LogListener
     * 
     * @return falls null -> Listener nicht angemeldet
     *         falls !null -> Listener angemeldet, bytearray ist ein Identifier um Listener abzumelden
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public int registerStatusListener( StatusListener aStatusListener ) throws RemoteException;
    
    /**
     * Meldet einen StatusListener von dem Server ab 
     * 
     * @since 0.9
     * @param aListenerIdent Identifier des abzumeldenden LogListeners oder
     *                       null wenn alle Listener abgemeldet werden sollen
     * 
     * @return ob der listener erfolgreich abgemeldet werden konnte 
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public boolean unregisterStatusListener( int aListenerIdent ) throws RemoteException;
    
    /**
     * Gibt den gewuenschten Status wenn moeglich als boolean zurueck 
     * 
     * @since 0.9
     * @param aBotStatus der gewuenschte Status
     * 
     * @return den Zustand des angeforderten Status, falls er in boolean angegeben werden kann, 
     *         falls nicht false
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public boolean getBooleanStatus( BotStatusType aBotStatus ) throws RemoteException;
    

    /**
     * Stoppt und Beendet den Bot. 
     * 
     * @since 0.9
     *         
     * @exception RemoteException
     *          falls die Verbindung in irgendeiner Weise gestoert wird 
     */
    public void closeBot() throws RemoteException;
    
}
