package core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import remotecontrol.RemoteControlServer;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.server_2008.NetworkCommunication;


/**
 * Bildet das Herzstueck des MixedRealityBot-Framework. Hier werden alle Metaprozesse Threads verwaltet
 * und gesteuert.
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.9
 *
 */
@ThreadSafe
public class Core {
    
    private static Core INSTANCE;
 
    private Core(){
            
        mBotinformation = new BotInformation();
        
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
            Core.getLogger().info( mBotinformation.getBotname() + "(" + mBotinformation.getRcId() + "/" + mBotinformation.getVtId() + ") closeing!" );
            disposeAI();
            stopServermanagements();
            stopServerConnection();
            RestartAiManagement.getInstance().close();
            RemoteControlServer.getInstance().close();
            
            Core.getLogger().info( mBotinformation.getBotname() + "(" + mBotinformation.getRcId() + "/" + mBotinformation.getVtId() + ") closed!" );
            INSTANCE = null;
        }
        
        // Hier kein System.exit( status ) das das das beenden des Bots verhindert
        
    }
    
    @GuardedBy("this") private BotInformation mBotinformation;
    @GuardedBy("this") volatile private ArtificialIntelligence mAI;
    @GuardedBy("this") private NetworkCommunication mServerConnection;
    
    /**
     * Initialisiert die Grundfunktionen des Bots. 
     * 
     * @since 0.1
     * 
     * @param aCommandline die Commandline als Stringarray
     */
    public void startBot( String[] aCommandline ) {

        try {
            
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    
                    close();
                    
                }
                
            });
                        
            if( CommandLineOptions.parseCommandLineArguments( aCommandline ) ){
                
                RemoteControlServer.getInstance().startRemoteServer();
                
            }else{
                
                RemoteControlServer.getInstance().startRemoteServer();
                initializeAI();
                RestartAiManagement.getInstance().startManagement();
                startServerConnection();
                mAI.startAI();
                
            }
            
        } catch ( Exception vNormalException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen", vNormalException );

        }
        
    }

    @SuppressWarnings("resource")
	public boolean initializeAI() {
        
        synchronized (this) {
            
            if( mAI == null ){
                
            } else if( mAI.isRunning() ){

                disposeAI();
                
            }
            
        }
           
        try {

            Core.getLogger().trace( "Loading AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
            URL vUniformResourceLocator = new File( mBotinformation.getAIArchive() ).toURI().toURL();
            URLClassLoader vClassloader = new URLClassLoader( new URL[]{ vUniformResourceLocator } );
            synchronized (this) {
                
                mAI = (ArtificialIntelligence) vClassloader.loadClass( mBotinformation.getAIClassname() ).newInstance();
                
            }
            Core.getLogger().info( "Loaded AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
            // vClassloader.close(); <- verursacht Fehler
            
        } catch ( Exception vException ) {
            
            Core.getLogger().error( "Error loading AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() + " " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
            return false;
            
        }
         
        synchronized (this) {
            
            mAI.initializeAI( getBotinformation() );
        
        }
        
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AILoaded );
        
        return true;
        
    }

    synchronized public void disposeAI() {
        
        if( mAI != null ){
            
            Core.getLogger().trace( "Disposing AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
            mAI.disposeAI();
            mAI = null;
            Core.getLogger().info( "Disposed AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );

            RemoteControlServer.getInstance().changedStatus( BotStatusType.AILoaded );
            RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
            
        }
        
    }
    
    synchronized public boolean startAI(){

        mAI.startAI();
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
        return Core.getInstance().getAI().isRunning();
        
    }
    
    synchronized public void pauseAI(){

        mAI.pauseAI();
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
        
    }

    /**
     * @throws IOException
     * @throws SocketTimeoutException
     * @throws SocketException
     */
    public void startServerConnection() { //TODO: return boolean, connect successful?
        
        while( !(mServerConnection != null && mServerConnection.isConnected()) ){
        
            Core.getLogger().info( mBotinformation.getReconnect()?"Reconnecting":"Connecting" + " to server " + mBotinformation.getServerIP() + ":" + mBotinformation.getServerPort() );
            
            try {
            
                stopServerConnection();
                
                synchronized (this) {
                    
                    if( mBotinformation.getBotPort() > -1 ){
                        
                        mServerConnection = new NetworkCommunication( mBotinformation.getServerIP(), 
                                                                          mBotinformation.getServerPort(),
                                                                          mBotinformation.getBotPort());
                        
                     
                    } else {
                        
                        mServerConnection = new NetworkCommunication( mBotinformation.getServerIP(), 
                                                                      mBotinformation.getServerPort());
                        
                    }
                    
                    if( !mBotinformation.getReconnect() ){
                        
                        mServerConnection.connectToServer( mBotinformation );
                        
                    }
                    
                }
                

                RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkConnection );
                
                startServermanagements();
                
            } catch ( IOException e ) {
    
                Core.getLogger().error( "Error starting serverconnection: " + e.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, e );
                
            } 
        }
    }
    
    
    /**
     * Beendet die Verbindung zum MRServer. 
     * Dazu werden auch eventuelle Servermanagements pausiert, um Fehler bei dem Verarbeiten zu verhindern. 
     * 
     * @since 0.1
     */
    public void stopServerConnection(){
        
        synchronized (this) {
            
            if( mServerConnection != null ){
                
                stopServermanagements();
                
                Core.getLogger().info( "Closing serverconnection (" + mServerConnection.toString() + ")" );
                mServerConnection.closeConnection();
                Core.getLogger().info( "Closed serverconnection." );
                

                RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkConnection );
                
            }
        
        }
        
    }

    /**
     * @param aServerConnection
     */
    public void startServermanagements() {

        stopServermanagements(); 

        Core.getLogger().info( "Start Servermanagements." );
        FromServerManagement.getInstance().start();
        ToServerManagement.getInstance().start();
        
        
    }
    
    /**
     * 
     */
    public void stopServermanagements() {

        if( FromServerManagement.getInstance().isAlive() || ToServerManagement.getInstance().isAlive() ){
            
            Core.getLogger().info( "Stopping servermanagements." );
            FromServerManagement.getInstance().stopManagement();
            ToServerManagement.getInstance().stopManagement();
            Core.getLogger().info( "Stopped servermanagements." );
            
        }
        
    }
    
    synchronized public BotInformation getBotinformation() {
        
        Core.getLogger().debug( "Retriving Botinformation: \n" + mBotinformation.toString() );
        return mBotinformation;
      
    }

    synchronized public void setBotinformation( BotInformation aBotinformation ) {
        
        Core.getLogger().info( "Setting Botinformation: \n" + mBotinformation.toString() );
        mBotinformation = aBotinformation;
        
    }
    
    synchronized public NetworkCommunication getServerConnection() {
        return mServerConnection;
    }

    synchronized public ArtificialIntelligence getAI() {
        return mAI;
    }

}
