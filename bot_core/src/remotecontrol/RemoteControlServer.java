package remotecontrol;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import core.Core;
import essentials.core.BotInformation;
import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.botremotecontrol.LogListener;
import fwns_network.botremotecontrol.RemoteControlInterface;
import fwns_network.botremotecontrol.StatusListener;

public class RemoteControlServer implements RemoteControlInterface{

    private static RemoteControlServer INSTANCE;
    private LogListener mLogListener;
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

            try {
                mRMIServerRegistry = LocateRegistry.getRegistry();
            } catch ( RemoteException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Core.getLogger().info( "RMI registry already exists ", vRemoteException );
            
        }
        
        try {

            mRemoteControlServerName = Core.getInstance().getBotinformation().getBotname() + "-" + Core.getInstance().getBotinformation().getRcId() + "-" + Core.getInstance().getBotinformation().getVtId();
            Core.getLogger().info( "RemoteControlServer starting" );
            mRemoteControlServerStub = (RemoteControlInterface) UnicastRemoteObject.exportObject( this, 0 ); //TODO: Port frei waehlbar?
            
            mRMIServerRegistry.rebind( mRemoteControlServerName , mRemoteControlServerStub);
            Core.getLogger().info( "RemoteControlServer started and bound to " +  mRemoteControlServerName );
          
        } catch ( Exception vException ) {
            
            Core.getLogger().error( "RemoteControlServer exception", vException);
            
        }
        
    }
    
    public void close() {
        
        try {
            
            
            unregisterStatusListener( null );
            unregisterLogListener( null );

            Core.getLogger().info( "Unbinding RemoteControlServer " + mRemoteControlServerName );
            mRMIServerRegistry.unbind( mRemoteControlServerName );
            
            Core.getLogger().info( "Unexporting RemoteControlServer " + mRemoteControlServerName );
            UnicastRemoteObject.unexportObject( getInstance(), true);
            INSTANCE = null;
            
        } catch( Exception vException ) {
            
            Core.getLogger().error( "Error closing RemoteControlServer", vException);
            
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

        
        
        return false;
    }

    @Override
    public boolean reconnectBot() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean disconnectBot() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean initialiseAI() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean startAI() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean stopAI() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }
    
    //Logging
    
    @Override
    public byte[] registerLogListener( LogListener aLogListener ) throws RemoteException {
        
        mLogListener = aLogListener;
        return null;
        
    }

    @Override
    public boolean setLogLevel( Level aLogLevel ) throws RemoteException {
                
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig( Core.getLogger().getName() );
        
        loggerConfig.setLevel( aLogLevel );
        ctx.updateLoggers(); 
        
        return false;
    }

    @Override
    public boolean unregisterLogListener( byte[] aListenerIdent ) throws RemoteException {
        
        if( aListenerIdent == null && mLogListener != null){

            Core.getLogger().info( "Unregistering all LogListeners" );

            mLogListener = null;
            Core.getLogger().info( "Unregistered all LogListeners" );
            
            return true;
            
        }
        
        return false;
    }

    public void writeToLoglistener( String aString ){
        
        if( mLogListener != null ){
            try {
                
                mLogListener.logEvent( "Client " + aString, Level.ALL );
                
            } catch ( RemoteException vRemoteException ) {

                Core.getLogger().error( "RemoteControlClient exception" );
                mLogListener = null;
            }
        }
        
    }
    
    //Status
    
    @Override
    public byte[] registerStatusListener( StatusListener aStatusListener ) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean unregisterStatusListener( byte[] aListenerIdent ) throws RemoteException {
        
        if( aListenerIdent == null){

            Core.getLogger().info( "Unregistering all StatusListeners" );

            //TODO: Dinge
            
            Core.getLogger().info( "Unregistered all StatusListeners" );
            
            return true;
            
        }
        
        return false;
    }

    @Override
    public boolean getBooleanStatus( BotStatusType aBotStatus ) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }
    
    // Kill

    @Override
    public void closeBot() throws RemoteException {
  
        Core.getInstance().close();
        
    }
    
}
