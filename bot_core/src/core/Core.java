package core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLClassLoader;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import fwns_network.server_2008.NetworkCommunication;
import gui.CoreWindow;

//ToDo: Logger mit Apache Commons Logging or log4j ersetzen

//ToDo: Initialle Loggermeldung mit idents
@ThreadSafe
public class Core {
    
    private static Core INSTANCE;
 
    private Core(){
        
        mBotinformation = new BotInformation();
        
    }

    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
            Core.INSTANCE = new Core();
        }
        
        return Core.INSTANCE;
        
    }
    
    public void close() {

        stopAI();
        stopServerConnection();
        RestartAiManagement.getInstance().close();
        
        System.runFinalization();
        
    }
    
    @GuardedBy("this") private final BotInformation mBotinformation;
    @GuardedBy("this") private ArtificialIntelligence mAI;
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
        
        synchronized (this) {
            
            if( mAI == null ){
                
            } else if( mAI.isRunning() ){
                
                stopAI();
                
            }
            
        }

        try {
            
            URL url = new File( mBotinformation.getAIArchive() ).toURI().toURL();
            URLClassLoader cl = new URLClassLoader( new URL[]{ url } );
            synchronized (this) {
                
                mAI = (ArtificialIntelligence) cl.loadClass( mBotinformation.getAIClassname() ).newInstance();
                
            }
            // cl.close(); <- verursacht Fehler
            
        } catch ( Exception e) {
            
            e.printStackTrace();
            
        }
        synchronized (this) {
            
            mAI.initializeAI( getBotinformation() );
        
        }
        
        RestartAiManagement.getInstance().startManagement();
        
    }

    synchronized public void stopAI() {
        
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

            e.printStackTrace();
            
        } 
        
    }
    
    public void stopServerConnection(){
        
        stopServermanagements();
        
        synchronized (this) {
            
            if( mServerConnection != null ){
                
                mServerConnection.closeConnection();
                
            }
        
        }
        
    }

    /**
     * @param aServerConnection
     */
    public void startServermanagements() {

        stopServermanagements(); 
        
        FromServerManagement.getInstance().start();
        ToServerManagement.getInstance().start();
        
        
    }
    
    /**
     * 
     */
    public void stopServermanagements() {

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

            close();
            
        } finally {
            
            super.finalize();
            
        }
        
    }
    
}
