package fwns_botremotecontrol.core;

import fwns_botremotecontrol.gui.Botcontrol;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Bildet das Herzstueck des MixedRealityBot-RemoteControlClienten. Hier werden alle Metaprozesse und Threads verwaltet
 * und gesteuert.
 * 
 * @author Eike Petersen
 * @since 0.9
 * @version 0.9
 *
 */
public class Core {
    
    private static Core INSTANCE;
 
    private Core(){
            
    }

    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
            Core.getLogger().trace( "Creating Core-instance." );
            Core.INSTANCE = new Core();
        }

        Core.getLogger().trace( "Retrieving Core-instance." );
        return Core.INSTANCE;
        
    }
    
    private static Logger BOTCORELOGGER = LogManager.getLogger("CORE");
    
    public static Logger getLogger(){
        
        return BOTCORELOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){
           
        }
        
    }
    
    /**
     * Initialisiert die Grundfunktionen der RemoteKontrolle. 
     * 
     * @since 0.9
     */
    public void startBot() {

        try {
            
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    
                    close();
                    
                }
                
            });
            
            Botcontrol.startGUI();
            
        } catch ( Exception vException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
        }
        
    }
    
    public String[] getListOfBots( String aRegistry ){
        
        try {
            
            return Naming.list( aRegistry );
            
        } catch ( RemoteException vRemoteException ) {

            Core.getLogger().error( "Fehler beim Verbinden " + vRemoteException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vRemoteException );
            
        } catch ( MalformedURLException vMalformedURLException ) {

            Core.getLogger().error( "Diese URL ist fehlerhaft " + vMalformedURLException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vMalformedURLException );
            
        }
        
        return null;
        
    }
    
    
    public void connectToBot( String aBotName ){
        
        
        
    }

}
