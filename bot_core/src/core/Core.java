package core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import fwns_network.server_2008.NetworkCommunication;
import gui.CoreWindow;

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
        
    }
    
    @GuardedBy("this") private final BotInformation mBotinformation;
    @GuardedBy("this") volatile private ArtificialIntelligence mAI;
    private CoreWindow mCoreWindow = null;

    @GuardedBy("this") private NetworkCommunication mServerConnection;
    
    public void startBot( String[] aCommandline ) {

        try {
            
            synchronized (this) {
                
                CommandLineOptions.parseCommandLineArguments( aCommandline );
                
            }
            
            if( getCoreWindow() != null ){
                
                getCoreWindow().open();
                
            }else{
                
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        
                        close();
                        
                    }
                    
                });

                startAI();
                startServerConnection();
                
            }
            
        } catch ( Exception vNormalException ) {

            vNormalException.printStackTrace();

        }
        
    }

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
        
            Core.getLogger().info( mBotinformation.getReconnect()?"Reconnecting":"Connecting" + " to server" );
            
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
    
                Core.getLogger().error( "Error starting serverconnection ", e );
                
            } 
        }
    }
    
    public void stopServerConnection(){
        
        stopServermanagements();
        
        synchronized (this) {
            
            if( mServerConnection != null ){
                
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

        Core.getLogger().info( "Closed servermanagements." );
        FromServerManagement.getInstance().close();
        ToServerManagement.getInstance().close();
        
    }
    
    synchronized public BotInformation getBotinformation() {
        
        return mBotinformation;
      
    }
    
    public CoreWindow getCoreWindow() {
        
        return mCoreWindow;
        
    }

    void setCoreWindow( CoreWindow aCoreWindow ) {
        
        mCoreWindow = aCoreWindow;
        
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
