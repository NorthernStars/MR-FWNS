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

        stopAI();
        stopServerConnection();
        RestartAiManagement.getInstance().close();
        
        System.runFinalization();
        
        Core.getLogger().info( mBotinformation.getBotname() + "(" + mBotinformation.getRcId() + "/" + mBotinformation.getVtId() + ") closed!" );
        
        System.exit(0);
        
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
                startAI();
                startServerConnection();
                
            }
            
        } catch ( Exception vNormalException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen", vNormalException );

        }
        
    }

    @SuppressWarnings("resource")
	public void startAI() {
        
        Core.getLogger().trace( "Starting Ai." );
        synchronized (this) {
            
            if( mAI == null ){
                
            } else if( mAI.isRunning() ){

                stopAI();
                
            }
            
        }

        while( mAI == null){
           
            try {
    
                Core.getLogger().info( "Loading AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
                URL url = new File( mBotinformation.getAIArchive() ).toURI().toURL();
                URLClassLoader cl = new URLClassLoader( new URL[]{ url } );
                synchronized (this) {
                    
                    mAI = (ArtificialIntelligence) cl.loadClass( mBotinformation.getAIClassname() ).newInstance();
                    
                }
                // cl.close(); <- verursacht Fehler
                
            } catch ( Exception e) {
                
                Core.getLogger().error( "Error loading AI ", e );
                
            }
        
        }
        
        synchronized (this) {
            
            mAI.initializeAI( getBotinformation() );
        
        }
        
        RestartAiManagement.getInstance().startManagement();
        
    }

    synchronized public void stopAI() {
        
        Core.getLogger().info( "Stopping AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
        if( mAI != null ){
            
            mAI.disposeAI();
            mAI = null;
            
        }
        
    }

    /**
     * @throws IOException
     * @throws SocketTimeoutException
     * @throws SocketException
     */
    public void startServerConnection() {
        
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
            
        }
        
    }
    
    synchronized public BotInformation getBotinformation() {
        
        return mBotinformation;
      
    }

    synchronized public void setBotinformation( BotInformation aBotinformation ) {
        
        mBotinformation = aBotinformation;
        
    }
    
    synchronized public NetworkCommunication getServerConnection() {
        return mServerConnection;
    }

    synchronized public ArtificialIntelligence getAI() {
        return mAI;
    }
    
    @Override
    protected void finalize() throws Throwable{
        
        try {
            
            Core.getLogger().info( "Final death." );
            close();
            
        } finally {
            
            super.finalize();
            
        }
        
    }
}
