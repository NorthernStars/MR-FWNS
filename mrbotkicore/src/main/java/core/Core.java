package core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import remotecontrol.RemoteControlServer;
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
            ReloadAiManagement.getInstance().close();
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
                ReloadAiManagement.getInstance().startManagement();
                if( startServerConnection( 3 ) ){
                                    
                } else if( mAI != null ){
                    
                    suspendAI();
                    Core.getLogger().error( "Could not connect to Server. AI suspended" );
                    
                }
                
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

            suspendServermanagements();
            
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
        
        resumeServermanagements();

        RemoteControlServer.getInstance().changedStatus( BotStatusType.AILoaded );
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
        
        return true;
        
    }

    public void disposeAI() {
        
        if( mAI != null ){
            
            suspendServermanagements();
            Core.getLogger().trace( "Disposing AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
            mAI.disposeAI();
            mAI = null;
            Core.getLogger().info( "Disposed AI " + mBotinformation.getAIClassname() + " from " + mBotinformation.getAIArchive() );
            resumeServermanagements();
            
            RemoteControlServer.getInstance().changedStatus( BotStatusType.AILoaded );
            RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
            
        }
        
    }
    
    synchronized public boolean resumeAI(){

        Core.getLogger().trace( "Resuming AI" );
        mAI.resumeAI();
        Core.getLogger().info( "Resumed AI" );
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
        return Core.getInstance().getAI().isRunning();
        
    }
    
    synchronized public void suspendAI(){

        Core.getLogger().info( "Suspending AI" );
        mAI.suspendAI();
        Core.getLogger().info( "Suspended AI" );
        RemoteControlServer.getInstance().changedStatus( BotStatusType.AIRunning );
        
    }

    /**
     * @throws IOException
     * @throws SocketTimeoutException
     * @throws SocketException
     */
    public boolean startServerConnection( int aNumberOfTries ) { //TODO: return boolean, connect successful?
        
        int mTrysToConnect = 0;
        
        while( !(mServerConnection != null && mServerConnection.isConnected()) && ++mTrysToConnect <= aNumberOfTries ){
        
            Core.getLogger().info( "(" + mTrysToConnect + ") Trying to " + (mBotinformation.getReconnect()?"reconnect":"connect") + " to server " + mBotinformation.getServerIP() + ":" + mBotinformation.getServerPort() );
            
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
                        
                    } else {
                        
                        mServerConnection.reconnectToServer();
                        
                    }
                    
                }
                

                RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkConnection );
                
                startServermanagements();
                
            } catch ( IOException e ) {
    
                Core.getLogger().error( "(" + mTrysToConnect + ") Error starting serverconnection: " + e.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, e );
                
                if( mServerConnection != null ){
                    
                    mServerConnection.closeConnection();
                    mServerConnection = null;
                    
                }
                
            } 
            
            if ( mServerConnection != null && mServerConnection.isConnected() ){
                
                Core.getLogger().info( "(" + mTrysToConnect + ") Connected to server (" + mServerConnection.toString() + ")" );
                break;
                
            } else {
                
                try {
                    Thread.sleep( 5000 ); //TODO: Besser
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                } 
                
            }
            
        }
        
        return mServerConnection != null && mServerConnection.isConnected();
        
    }
    
    
    /**
     * Beendet die Verbindung zum MRServer. 
     * Dazu werden auch eventuelle Servermanagements pausiert, um Fehler bei dem Verarbeiten zu verhindern. 
     * 
     * @since 0.1
     */
    public void stopServerConnection(){
         
        if( mServerConnection != null ){
            
            stopServermanagements();
            
            Core.getLogger().info( "Closing serversocket (" + mServerConnection.toString() + ")" );
            mServerConnection.closeConnection();
            Core.getLogger().info( "Closed serversocket." );
            
            RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkConnection );
            
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
            FromServerManagement.getInstance().close();
            ToServerManagement.getInstance().close();
            Core.getLogger().info( "Stopped servermanagements." );
            
        }
        
    }
    
    /**
     * 
     */
    public void resumeServermanagements() {

        if( FromServerManagement.getInstance().isAlive() || ToServerManagement.getInstance().isAlive() ){
            
            Core.getLogger().info( "Resuming servermanagements." );
            FromServerManagement.getInstance().resumeManagement();
            ToServerManagement.getInstance().resumeManagement();
            Core.getLogger().info( "Resumed servermanagements." );

            RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkIncomingTraffic );
            RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic );
            
        }
        
    }
    
    /**
     * 
     */
    public void suspendServermanagements() {

        if( FromServerManagement.getInstance().isAlive() || ToServerManagement.getInstance().isAlive() ){
            
            Core.getLogger().info( "Suspending servermanagements." );
            FromServerManagement.getInstance().suspendManagement();
            ToServerManagement.getInstance().suspendManagement();
            Core.getLogger().info( "Suspending servermanagements." );

            RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkIncomingTraffic );
            RemoteControlServer.getInstance().changedStatus( BotStatusType.NetworkOutgoingTraffic );
            
        }
        
    }
    
    
    
    synchronized public BotInformation getBotinformation() {
        
        Core.getLogger().debug( "Retriving Botinformation: \n" + mBotinformation.toString() );
        return mBotinformation;
      
    }

    synchronized public void setBotinformation( BotInformation aBotinformation ) {
        
        Core.getLogger().debug( "Setting Botinformation: \n" + mBotinformation.toString() );
        mBotinformation = aBotinformation;
        
    }
    
    synchronized public NetworkCommunication getServerConnection() {
        return mServerConnection;
    }

    synchronized public ArtificialIntelligence getAI() {
        return mAI;
    }

}
