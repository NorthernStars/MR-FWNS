package core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;

import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import fwns_network.server_2008.NetworkCommunication;
import gui.CoreWindow;

//ToDo: Logger mit Apache Commons Logging or log4j ersetzen

//ToDo: Initialle Loggermeldung mit idents

public class Core {

    private final BotInformation mBotinformation;
    private ArtificialIntelligence mAI;
    private FromServerManagement mFromServerManagement;
    private ToServerManagement mToServerManagement;
    private CoreWindow mCoreWindow = null;

    private static Core INSTANCE;
    private NetworkCommunication mServerConnection;
    
    private Core(){
        
        mBotinformation = new BotInformation();
        
    }

    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
            Core.INSTANCE = new Core();
        }
        
        return Core.INSTANCE;
        
    }

    public void startBot( String[] aCommandline ) {

        try {
            
            CommandLineOptions.parseCommandLineArguments( aCommandline );
            
            if( getCoreWindow() != null ){
                
                getCoreWindow().open();
                
            }else{
                
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        
                        closeBot();
                        System.out.println( mBotinformation.getBotname() + "(" + mBotinformation.getRcId() + "/" + mBotinformation.getVtId() + ") is dead!" );
                        
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
        
        if( mAI == null ){
            
        } else if( mAI.isRunning() ){
            
            stopAI();
            
        }

        try {
            
            URL url = new File( mBotinformation.getAIArchive() ).toURI().toURL();
            URLClassLoader cl = new URLClassLoader( new URL[]{ url } );
            mAI = (ArtificialIntelligence) cl.loadClass( mBotinformation.getAIClassname() ).newInstance();
            
        } catch ( Exception e) {
            
            e.printStackTrace();
            
        }
        
        mAI.initializeAI( getBotinformation() );
        
    }

    public void stopAI() {
        
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
        try {
            
            stopServerConnection();
            
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
            
            startServermanagements();
            
        } catch ( IOException e ) {

            e.printStackTrace();
            
        } 
        
    }
    
    public void stopServerConnection(){
        
        stopServermanagements();
        if( mServerConnection != null ){
            
            mServerConnection.closeConnection();
            
        }
        
    }

    /**
     * @param aServerConnection
     */
    public void startServermanagements() {

        stopServermanagements();
        mFromServerManagement = new FromServerManagement();
        mToServerManagement = new ToServerManagement();

        mFromServerManagement.start();
        mToServerManagement.start();
        
    }
    
    /**
     * 
     */
    public void stopServermanagements() {

        if( mFromServerManagement != null ){
            
            mFromServerManagement.stopManagement();
            mFromServerManagement = null;            
            
        }
        
        if( mToServerManagement != null ){
            
            mToServerManagement.stopManagement();
            mToServerManagement = null;            
            
        }
        
    }
    
    public BotInformation getBotinformation() {
        
        return mBotinformation;
      
    }
    
    public CoreWindow getCoreWindow() {
        
        return mCoreWindow;
        
    }

    void setCoreWindow( CoreWindow aCoreWindow ) {
        
        mCoreWindow = aCoreWindow;
        
    }

    public NetworkCommunication getServerConnection() {
        return mServerConnection;
    }

    public ArtificialIntelligence getAI() {
        return mAI;
    }

    public FromServerManagement getFromServerManagement() {
        return mFromServerManagement;
    }

    public ToServerManagement getToServerManagement() {
        return mToServerManagement;
    }

    public void closeBot() {

        stopAI();
        stopServerConnection();
        
        System.runFinalization();
        
    }
    
    @Override
    protected void finalize() throws Throwable{
        
        try {

            closeBot();
            
        } finally {
            
            super.finalize();
            
        }
        
    }
    
}
