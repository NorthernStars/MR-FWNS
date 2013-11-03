package core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.logging.log4j.core.LogEvent;

import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.botremotecontrol.LogListener;
import fwns_network.botremotecontrol.RemoteControlInterface;
import fwns_network.botremotecontrol.StatusListener;
import gui.BotFrame;

public class RemoteBot implements LogListener, StatusListener {

    private final RemoteControlInterface mTheBot;
    private BotFrame mTheBotFrame = null;
    
    private int mListenerIdent = -1;
    
    public RemoteBot( String aBotURL, BotFrame aBotFrame ) throws RemoteException, MalformedURLException, NotBoundException {
        
        mTheBot = (RemoteControlInterface)Naming.lookup( aBotURL );
        
        UnicastRemoteObject.exportObject( this, 0 );
        connectLogListener();
        mListenerIdent = mTheBot.registerStatusListener( this );
        
        mTheBotFrame = aBotFrame;
        mTheBotFrame.registerBot( this );
        
    }

    public void connectLogListener() throws RemoteException {
        
        mListenerIdent = mTheBot.registerLogListener( this );
        
    }
    
    public void close(){
        
        try {

            disconnectLogListener();
            mTheBot.unregisterStatusListener( mListenerIdent );
            UnicastRemoteObject.unexportObject( this, true );
            
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        mTheBotFrame = null;
        
    }

    public void disconnectLogListener() throws RemoteException {
        
        mTheBot.unregisterLogListener( mListenerIdent );
        
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
