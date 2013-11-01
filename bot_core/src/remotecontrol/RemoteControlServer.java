package remotecontrol;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import core.Core;
import core.FromServerManagement;
import core.ToServerManagement;
import essentials.core.BotInformation;
import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.botremotecontrol.LogListener;
import fwns_network.botremotecontrol.RemoteControlInterface;
import fwns_network.botremotecontrol.StatusListener;

public class RemoteControlServer implements RemoteControlInterface{

    private static RemoteControlServer INSTANCE;
    private Map<Integer, LogListener> mLogListener = Collections.synchronizedMap(new HashMap<Integer, LogListener>());
    private Map<Integer, StatusListener> mStatusListener = Collections.synchronizedMap(new HashMap<Integer, StatusListener>());
    private String mRemoteControlServerName;
    private RemoteControlInterface mRemoteControlServerStub;
    private Registry mRMIServerRegistry;
    
    private RemoteControlServer(){
        
        super();
        
    }

    public static RemoteControlServer getInstance() {
        
        if( RemoteControlServer.INSTANCE == null){
            
            Core.getLogger().trace( "Creating RemoteControlserver" );
            RemoteControlServer.INSTANCE = new RemoteControlServer();
             
        }

        Core.getLogger().trace( "Retrieving RemoteControlServer." );
        return RemoteControlServer.INSTANCE;
        
    }
    
    public void startRemoteServer() {
        
        //TODO: Besser sicher machen, jetzt noch nicht wichtig... :)
        /*if ( System.getSecurityManager() == null ) {
            
            System.setSecurityManager( new SecurityManager() );
            Core.getLogger().trace( "SecurityManager registry created." );
            
        }*/
        
        try {
            
            mRMIServerRegistry = LocateRegistry.createRegistry(1099); 
            Core.getLogger().info( "RMI registry created " + LocateRegistry.getRegistry().toString() );
            
        } catch (RemoteException vRemoteException ) {

            Core.getLogger().catching( Level.ERROR, vRemoteException );
            
            try {
                
                mRMIServerRegistry = LocateRegistry.getRegistry();
                Core.getLogger().info( "RMI registry already exists " + mRMIServerRegistry.toString()  );
                
            } catch ( RemoteException vExtraRemoteException ) {
                
                Core.getLogger().info( "RMI-registry is unavalible " + vExtraRemoteException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vExtraRemoteException );
                
                Core.getLogger().info( "Cannot create RemoteControlServer" );
                return;
                
            }
            
        }
        
        try {

            mRemoteControlServerName = Core.getInstance().getBotinformation().getBotname() + "-" + Core.getInstance().getBotinformation().getRcId() + "-" + Core.getInstance().getBotinformation().getVtId();
            Core.getLogger().info( "RemoteControlServer starting" );
            mRemoteControlServerStub = (RemoteControlInterface) UnicastRemoteObject.exportObject( this, 0 ); //TODO: Port frei waehlbar?
            
            mRMIServerRegistry.rebind( mRemoteControlServerName , mRemoteControlServerStub);
            Core.getLogger().info( "RemoteControlServer started and bound to " +  mRemoteControlServerName );
          
        } catch ( Exception vException ) {
            
            Core.getLogger().error( "RemoteControlServer exception " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
        }
        
    }
    
    public void close() {
        
        try {
            
            
            unregisterStatusListener( 0 );
            unregisterLogListener( 0 );

            Core.getLogger().info( "Unbinding RemoteControlServer " + mRemoteControlServerName );
            mRMIServerRegistry.unbind( mRemoteControlServerName );
            
            Core.getLogger().info( "Unexporting RemoteControlServer " + mRemoteControlServerName );
            UnicastRemoteObject.unexportObject( getInstance(), true);
            INSTANCE = null;
            
        } catch( Exception vException ) {
            
            Core.getLogger().error( "Error closing RemoteControlServer " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
        }
        
    }
    
    //Standartfunktionen
    
    @Override
    public BotInformation getBotInformation() throws RemoteException {
        
        return Core.getInstance().getBotinformation();
        
    }


    @Override
    public void setBotInformation( BotInformation aBotinformation ) throws RemoteException {

        aBotinformation.setBotMemory( Core.getInstance().getBotinformation().getBotMemory() );
        Core.getInstance().setBotinformation( aBotinformation );
        
    }
    
    @Override
    public boolean connectBot() throws RemoteException {

        Core.getInstance().getBotinformation().setReconnect( false );
        Core.getInstance().startServerConnection();
        return Core.getInstance().getServerConnection().isConnected();
        
    }

    @Override
    public boolean reconnectBot() throws RemoteException {
        
        Core.getInstance().getBotinformation().setReconnect( true );
        Core.getInstance().startServerConnection();
        return Core.getInstance().getServerConnection().isConnected();
    }

    @Override
    public boolean disconnectBot() throws RemoteException {
        
        Core.getInstance().stopServerConnection();
        return Core.getInstance().getServerConnection().isConnected();
        
    }

    @Override
    public boolean initialiseAI() throws RemoteException {

        return Core.getInstance().initializeAI();
        
    }

    @Override
    public boolean startAI() throws RemoteException {

        return Core.getInstance().startAI();
        
    }

    @Override
    public void pauseAI() throws RemoteException {
        
        Core.getInstance().getAI().pauseAI();
        
    }

    @Override
    public void disposeAI() throws RemoteException {

        Core.getInstance().getAI().disposeAI();
        
    }

    @Override
    public void executeCommandOnAI( String vCommandString ) throws RemoteException {

        Core.getInstance().getAI().executeCommand( vCommandString );
        
    }
    
    //Logging
    
    @Override
    public int registerLogListener( LogListener aLogListener ) throws RemoteException {
        
        Core.getLogger().info( "Registering LogListener " + aLogListener.hashCode() );
        mLogListener.put( aLogListener.hashCode(), aLogListener );
        
        return aLogListener.hashCode();
        
    }


    @Override
    public Level getLogLevel() throws RemoteException {
        
        LoggerContext vLoggerContext = (LoggerContext) LogManager.getContext(false);
        Configuration vGlobalConfig = vLoggerContext.getConfiguration();
        LoggerConfig vLoggerConfig = vGlobalConfig.getLoggerConfig( Core.getLogger().getName() );
        
        return vLoggerConfig.getLevel();
        
    }

    @Override
    public boolean setLogLevel( Level aLogLevel ) throws RemoteException {
                
        LoggerContext vLoggerContext = (LoggerContext) LogManager.getContext(false);
        Configuration vGlobalConfig = vLoggerContext.getConfiguration();
        LoggerConfig vLoggerConfig = vGlobalConfig.getLoggerConfig( Core.getLogger().getName() );
        
        vLoggerConfig.setLevel( aLogLevel );
        vLoggerContext.updateLoggers(); 
        
        return true;
    }

    @Override
    public boolean unregisterLogListener( int aListenerIdent ) throws RemoteException {
        
        if( aListenerIdent == 0 ){

            Core.getLogger().trace( "Unregistering all LogListeners" );
            mLogListener.clear();
            Core.getLogger().info( "Unregistered all LogListeners" );
            return true;
            
        }
        if( mLogListener.containsKey( aListenerIdent ) ){
            
            Core.getLogger().trace( "Unregistering LogListener " + aListenerIdent );
            mLogListener.remove( aListenerIdent );
            Core.getLogger().info( "Unregistered LogListener " + aListenerIdent );
            return true;
            
        }
        
        return false;
    }

    public void writeToLoglistener( LogEvent aLogEvent ){
        
        for ( int vLogListenerKey : mLogListener.keySet() ){
            try {
                
                mLogListener.get( vLogListenerKey ).logEvent( aLogEvent );
            
            } catch ( RemoteException vRemoteException ) {

                //TODO: recursives loggen loesen
                Core.getLogger().error( "RemoteControlClient exception " + vRemoteException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vRemoteException );
                Core.getLogger().trace( "Unregistering LogListener " + vLogListenerKey );
                mLogListener.remove( vLogListenerKey );
                Core.getLogger().info( "UnregisteredLogListener " + vLogListenerKey );
                
            }
        }

    }
    
    //Status
    
    @Override
    public int registerStatusListener( StatusListener aStatusListener ) throws RemoteException {
        
        Core.getLogger().info( "Registering StatusListener " + aStatusListener.hashCode() );
        mStatusListener.put( aStatusListener.hashCode(), aStatusListener );
        
        return aStatusListener.hashCode();
    }

    @Override
    public boolean unregisterStatusListener( int aListenerIdent ) throws RemoteException {

        if( aListenerIdent == 0 ){

            Core.getLogger().trace( "Unregistering all StatusListeners" );
            mStatusListener.clear();
            Core.getLogger().info( "Unregistered all StatusListeners" );
            return true;
            
        }
        if( mStatusListener.containsKey( aListenerIdent ) ){
            
            Core.getLogger().trace( "Unregistering StatusListener " + aListenerIdent );
            mStatusListener.remove( aListenerIdent );
            Core.getLogger().info( "Unregistered all StatusListeners " + aListenerIdent );
            return true;
            
        }
        
        return false;
    }

    @Override
    public boolean getBooleanStatus( BotStatusType aBotStatus ) throws RemoteException {

        switch( aBotStatus ){
        
            case NetworkConnection:
                return Core.getInstance().getServerConnection() != null && Core.getInstance().getServerConnection().isConnected();
            case NetworkIncomingTraffic:
                return FromServerManagement.getInstance().isReceivingMessages(); 
            case NetworkOutgoingTraffic:
                return ToServerManagement.getInstance().isSendingMessages(); 
            case AIRunning:
                return Core.getInstance().getAI() != null && Core.getInstance().getAI().isRunning();
            case AILoaded:
                return Core.getInstance().getAI() != null;
                
        }
        
        return false;
    }
    
    public void changedStatus( BotStatusType aBotStatus ){
        
        for ( int vStatusListenerKey : mStatusListener.keySet() ){
            try {
                
                mStatusListener.get( vStatusListenerKey ).changedStatus( aBotStatus );
            
            } catch ( RemoteException vRemoteException ) {

                Core.getLogger().error( "RemoteControlClient exception " + vRemoteException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vRemoteException );
                Core.getLogger().trace( "Unregistering StatusListener " + vStatusListenerKey );
                mStatusListener.remove( vStatusListenerKey );
                Core.getLogger().info( "Unregistered StatusListeners " + vStatusListenerKey );
                
            }
        }

    }
    
    // Kill

    @Override
    public void closeBot() throws RemoteException {
  
        Core.getInstance().close();
        
    }
    
}
