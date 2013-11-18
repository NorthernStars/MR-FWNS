package core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.core.LogEvent;

import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.botremotecontrol.LogListener;
import fwns_network.botremotecontrol.RemoteControlInterface;
import fwns_network.botremotecontrol.StatusListener;
import gui.BotFrame;

public class RemoteBot implements LogListener, StatusListener {

    private final RemoteControlInterface mTheBot;
    private BotFrame mTheBotFrame = null;

    private int mLogListenerIdent = 0;
    private int mStatusListenerIdent = 0;
    
    public RemoteBot( String aBotURL, BotFrame aBotFrame ) throws RemoteException, MalformedURLException, NotBoundException {
        
        mTheBot = (RemoteControlInterface)Naming.lookup( aBotURL );
        
        UnicastRemoteObject.exportObject( this, 0 );
        connectLogListener();
        connectStatusListener();
        
        mTheBotFrame = aBotFrame;
        mTheBotFrame.registerBot( this );
        
    }

    public void connectStatusListener() throws RemoteException {
        mLogListenerIdent = mTheBot.registerStatusListener( this );
    }

    public void connectLogListener() throws RemoteException {
        
        mStatusListenerIdent = mTheBot.registerLogListener( this );
        
    }
    
    public void close( boolean aCloseBot ){
        
        try {
            
            disconnectLogListener();
            disconnectStatusListener();
            if( aCloseBot ){
                
                mTheBot.closeBot();
                
            }
            UnicastRemoteObject.unexportObject( this, true );
            
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        mTheBotFrame = null;
        
    }

    public void disconnectStatusListener() throws RemoteException {
        mTheBot.unregisterStatusListener( mStatusListenerIdent );
    }

    public void disconnectLogListener() throws RemoteException {
        
        mTheBot.unregisterLogListener( mLogListenerIdent );
        
    }
    
    public RemoteControlInterface getTheBot(){
        
        return mTheBot;
        
    }
    
    @Override
    public void logEvent( LogEvent aLogEvent ) throws RemoteException {

        if( aLogEvent != null ){ 
            
            final String vLogMessage = new SimpleDateFormat( "HH:mm:ss.SSS" ).format( new java.util.Date( aLogEvent.getMillis() ) ) 
                    + " [" + aLogEvent.getThreadName() + "] "
                    + aLogEvent.getLevel().toString() + " "
                    + aLogEvent.getLoggerName() + " - "
                    + aLogEvent.getMessage().getFormattedMessage() + "\n";
            
            SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                public void run() {
                    
                    mTheBotFrame.addToLog( vLogMessage );
                    
                }
            });
            
            
             
        } else {
            
            System.out.println("---");
            
        }
    }

    @Override
    public void changedStatus( final BotStatusType aStatus ) throws RemoteException {

        SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
            public void run() {
                
                try {
                    mTheBotFrame.updateStatus( aStatus );
                } catch ( RemoteException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        });
        
    }

}
