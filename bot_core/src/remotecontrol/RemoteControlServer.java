package remotecontrol;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import core.Core;
import essentials.core.BotInformation;

public class RemoteControlServer implements RemoteControlInterface{

    private static RemoteControlServer INSTANCE;
    private LogListener mLogListener;
    
    public void writeToLoglistener( String aString ){
        
        if(mLogListener != null){
            try {
                
                mLogListener.logEvent( "Client " + aString );
                
            } catch ( RemoteException vRemoteException ) {

                Core.getLogger().error( "RemoteControlClient exception", vRemoteException );
                
            }
        }
        
    }
    
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
    
    @Override
    public BotInformation getBotInformation() throws RemoteException {
        
        return Core.getInstance().getBotinformation();
        
    }


    @Override
    public void setBotInformation( BotInformation aBotinformation ) throws RemoteException {

        Core.getInstance().setBotinformation( aBotinformation );
        
    }
    
    @Override
    public boolean registerLogListener(LogListener aLoglistener) throws RemoteException {
        
        mLogListener = aLoglistener;
        return true;
        
    }
    
    public void startRemoteServer() {
        
        //TODO: Besser sicher machen, jetzt noch nicht wichtig... :)
        /*if ( System.getSecurityManager() == null ) {
            
            System.setSecurityManager( new SecurityManager() );
            Core.getLogger().trace( "SecurityManager registry created." );
            
        }*/
        
        try {
            
            LocateRegistry.createRegistry(1099); 
            Core.getLogger().trace( "RMI registry created " + LocateRegistry.getRegistry().toString() );
            
        } catch (RemoteException vRemoteException ) {
             
                Core.getLogger().trace( "RMI registry already exists ", vRemoteException );
            
        }
        
        try {

            RemoteControlInterface vRemoteControlServerStub = (RemoteControlInterface) UnicastRemoteObject.exportObject( getInstance(), 0 ); //TODO: Port frei waehlbar?
            Registry vRMIServerRegistry = LocateRegistry.getRegistry();
            vRMIServerRegistry.rebind( Core.getInstance().getBotinformation().getBotname() + "-" + Core.getInstance().getBotinformation().getRcId() + "-" + Core.getInstance().getBotinformation().getVtId() , vRemoteControlServerStub);
            Core.getLogger().info( "RemoteControlServer gestartet und an " +  Core.getInstance().getBotinformation().getBotname() + "-" + Core.getInstance().getBotinformation().getRcId() + "-" + Core.getInstance().getBotinformation().getVtId() + " gebunden." );
            
            
        } catch ( Exception vException ) {
            
            Core.getLogger().error( "RemoteControlServer exception", vException);
            
        }
        
    }
    
}
