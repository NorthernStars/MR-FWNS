package fwns_botremotecontrol.core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.core.LogEvent;

import fwns_botremotecontrol.gui.BotFrame;
import fwns_network.botremotecontrol.BotStatusType;
import fwns_network.botremotecontrol.LogListener;
import fwns_network.botremotecontrol.RemoteControlInterface;
import fwns_network.botremotecontrol.StatusListener;

public class RemoteBot implements LogListener, StatusListener {

    private final RemoteControlInterface mTheBot;
    private BotFrame mTheBotFrame = null;
    private BotLoader mBotLoader = null;

    private int mLogListenerIdent = 0;
    private int mStatusListenerIdent = 0;
    
    public RemoteBot( String aBotURL, BotFrame aBotFrame ) throws RemoteException, MalformedURLException, NotBoundException {
    	this(aBotURL, aBotFrame, null);
    }
    
    public RemoteBot( String aBotURL, BotFrame aBotFrame, BotLoader aBotLoader ) throws RemoteException, MalformedURLException, NotBoundException {
    	mTheBot = (RemoteControlInterface)Naming.lookup( aBotURL );
    	mBotLoader = aBotLoader;
        
        UnicastRemoteObject.exportObject( this, 0 );
        connectLogListener();
        connectStatusListener();
        
        mTheBotFrame = aBotFrame;
        mTheBotFrame.registerBot( this );
        
        // try to get bot loader if not set
        if( mBotLoader == null ){
        	mBotLoader = BotLoader.getBotLoaderByKey(aBotURL);
        }
        
        if( mBotLoader != null ){        	
        	// start minimized
        	// TODO: using action listiener
        	mTheBotFrame.mPanelHeadPanelBackLabelButtonExpandContract.doClick();
        }
    	
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
            
        } catch ( Exception e ) {
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
    
    public BotLoader getBotLoader(){
    	return mBotLoader;
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
                    
                	try{
                		mTheBotFrame.addToLog( vLogMessage );
                	} catch (Exception vException){}
                    
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
