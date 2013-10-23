package gui;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.VerifyEvent;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.VerifyListener;

public class CoreWindow {
       
    private Label mStatusNetworkConnectedPictureLabel;
    private Label mStatusNetworkIncomingPictureLabel;
    private Label mStatusNetworkOutgoingPictureLabel;
    private Label mStatusAiRunsPictureLabel;
    
    private Text mBotName;
    private Text mBotRcID;
    private Text mBotVtID;
    private Text mBotIPAddress;
    private Text mBotPort;
    private Text mBotTeamname;
    private Combo mBotTeam;
    private Text mServerIPAddress;
    private Text mServerTeamport;
    
    private Button mServerConnect;
    private Button mServerReconnect;
    private Button mServerDisconnect;
    private Text mServerGamevalues;
    private Combo mServerGamevaluesSelector;
    
    private Text mAIArchive;
    private Button mAIKonfiguration;
    private Button mAIInitialisieren;
    
    private StatusListener mStatusListener;
    private Shell mCoreShell;
    private Button mAIBeenden;
    private Text mAIClassname;
    private Text mAIArguments;
    
    
    /**
     * Open the window.
     * @throws Exception 
     * @wbp.parser.entryPoint
     */
    public void open() throws Exception {
        Display display = Display.getDefault();
        mCoreShell = new Shell( display, SWT.SHELL_TRIM & (~SWT.RESIZE));
        mCoreShell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent arg0) {

                mStatusListener.stopListener();
                SWTResourceManager.dispose();
                Core.getInstance().close();
                
            }
        });
        mCoreShell.setSize( 329, 787 );
        mCoreShell.setText( "Botcontrol" );
        mCoreShell.setLayout(null);
        
        Group vStatusGroup = new Group(mCoreShell, SWT.NONE);
        vStatusGroup.setText("Status");
        vStatusGroup.setBounds(10, 10, 301, 109);
        
        Group vStatusNetworkGroup = new Group(vStatusGroup, SWT.NONE);
        vStatusNetworkGroup.setText("Netzwerk");
        vStatusNetworkGroup.setBounds(5, 15, 145, 89);
        
        Group vStatusNetworkConnectedGroup = new Group(vStatusNetworkGroup, SWT.NONE);
        vStatusNetworkConnectedGroup.setBounds(5, 15, 136, 23);
        
        Label vStatusNetworkConnectedTextLabel = new Label(vStatusNetworkConnectedGroup, SWT.NONE);
        vStatusNetworkConnectedTextLabel.setBounds(3, 3, 90, 17);
        vStatusNetworkConnectedTextLabel.setText("Verbunden");
        
        Group vStatusNetworkIncomingGroup = new Group(vStatusNetworkGroup, SWT.NONE);
        vStatusNetworkIncomingGroup.setBounds(5, 40, 136, 23);
        
        Label vStatusNetworkIncomingTextLabel = new Label(vStatusNetworkIncomingGroup, SWT.NONE);
        vStatusNetworkIncomingTextLabel.setText("Incoming Traffic");
        vStatusNetworkIncomingTextLabel.setBounds(3, 3, 107, 17);
        
        Group vStatusNetworkOutgoingGroup = new Group(vStatusNetworkGroup, SWT.NONE);
        vStatusNetworkOutgoingGroup.setBounds(5, 63, 136, 23);
        
        Label vStatusNetworkOutgoingTextLabel = new Label(vStatusNetworkOutgoingGroup, SWT.NONE);
        vStatusNetworkOutgoingTextLabel.setText("Outgoing Traffic");
        vStatusNetworkOutgoingTextLabel.setBounds(3, 3, 107, 17);
        
        Group vStatusAiGroup = new Group(vStatusGroup, SWT.NONE);
        vStatusAiGroup.setText("AI");
        vStatusAiGroup.setBounds(150, 15, 145, 41);
        
        Group vStatusAiRunsGroup = new Group(vStatusAiGroup, SWT.NONE);
        vStatusAiRunsGroup.setBounds(5, 15, 136, 23);
        
        Label vStatusAiRunsTextLabel = new Label(vStatusAiRunsGroup, SWT.NONE);
        vStatusAiRunsTextLabel.setText("Läuft");
        vStatusAiRunsTextLabel.setBounds(3, 3, 90, 17);
        
        mStatusNetworkConnectedPictureLabel = new Label(vStatusNetworkConnectedGroup, SWT.NONE);
        mStatusNetworkConnectedPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
        mStatusNetworkConnectedPictureLabel.setBounds(116, 3, 17, 17);

        mStatusNetworkIncomingPictureLabel = new Label(vStatusNetworkIncomingGroup, SWT.NONE);
        mStatusNetworkIncomingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
        mStatusNetworkIncomingPictureLabel.setBounds(116, 3, 17, 17);

        mStatusNetworkOutgoingPictureLabel = new Label(vStatusNetworkOutgoingGroup, SWT.NONE);
        mStatusNetworkOutgoingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
        mStatusNetworkOutgoingPictureLabel.setBounds(116, 3, 17, 17);
        
        mStatusAiRunsPictureLabel = new Label(vStatusAiRunsGroup, SWT.NONE);
        mStatusAiRunsPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
        mStatusAiRunsPictureLabel.setBounds(116, 3, 17, 17);
        
        mStatusListener = new StatusListener( this );
        mStatusListener.start();
        
        Group vBotGruppe = new Group(mCoreShell, SWT.NONE);
        vBotGruppe.setText("Bot");
        vBotGruppe.setBounds(10, 125, 301, 187);
        
        Label vBotNameLabel = new Label(vBotGruppe, SWT.NONE);
        vBotNameLabel.setBounds(10, 15, 65, 17);
        vBotNameLabel.setText("Name");
        
        Label vBotRcIDLabel = new Label(vBotGruppe, SWT.NONE);
        vBotRcIDLabel.setBounds(216, 15, 35, 17);
        vBotRcIDLabel.setText("Rc-ID");
        
        Label vBotVtIDLabel = new Label(vBotGruppe, SWT.NONE);
        vBotVtIDLabel.setText("Vt-ID");
        vBotVtIDLabel.setBounds(257, 15, 35, 17);
        
        Label vBotIPAddressLabel = new Label(vBotGruppe, SWT.NONE);
        vBotIPAddressLabel.setText("IP-Adresse");
        vBotIPAddressLabel.setBounds(10, 71, 90, 17);
        
        Label vBotPortLabel = new Label(vBotGruppe, SWT.NONE);
        vBotPortLabel.setText("Port");
        vBotPortLabel.setBounds(216, 71, 35, 17);
        
        Label vBotTeamnameLabel = new Label(vBotGruppe, SWT.NONE);
        vBotTeamnameLabel.setText("Teamname");
        vBotTeamnameLabel.setBounds(10, 127, 90, 17);

        Label vBotTeamLabel = new Label(vBotGruppe, SWT.NONE);
        vBotTeamLabel.setText("Team");
        vBotTeamLabel.setBounds(181, 127, 35, 17);
        
        mBotName = new Text(vBotGruppe, SWT.BORDER);
        mBotName.setBounds(10, 38, 200, 27);
        mBotName.setText( Core.getInstance().getBotinformation().getBotname() );
        
        mBotRcID = new Text(vBotGruppe, SWT.BORDER);
        mBotRcID.setBounds(216, 38, 35, 27);
        mBotRcID.setText( Integer.toString( Core.getInstance().getBotinformation().getRcId() ) );

        mBotVtID = new Text(vBotGruppe, SWT.BORDER);
        mBotVtID.setBounds(257, 38, 35, 27);
        mBotVtID.setText( Integer.toString( Core.getInstance().getBotinformation().getVtId() ) );

        mBotIPAddress = new Text(vBotGruppe, SWT.BORDER);
        mBotIPAddress.setEnabled(false);
        mBotIPAddress.setEditable(false);
        mBotIPAddress.setBounds(10, 94, 200, 27);
        mBotIPAddress.setText( Core.getInstance().getBotinformation().getBotIP().getCanonicalHostName() + " ("
                             + Core.getInstance().getBotinformation().getBotIP().getHostAddress() + ")" );
        
        mBotPort = new Text(vBotGruppe, SWT.BORDER);
        mBotPort.setBounds(216, 94, 75, 27);
        mBotPort.setText( Integer.toString( Core.getInstance().getBotinformation().getBotPort() ) );
        
        mBotTeamname = new Text(vBotGruppe, SWT.BORDER);
        mBotTeamname.setBounds(10, 150, 165, 27);
        mBotTeamname.setText( Core.getInstance().getBotinformation().getTeamname() );
        
        mBotTeam = new Combo(vBotGruppe, SWT.READ_ONLY);
        mBotTeam.setItems( Teams.getTeamsAsStringArray() );
        mBotTeam.setVisibleItemCount(3);
        mBotTeam.setBounds(181, 150, 110, 29);
        mBotTeam.select(2);
        mBotTeam.setText( Core.getInstance().getBotinformation().getTeam().toString() );
        
        setBotGroupListener();
        
        Group vServerGroup = new Group(mCoreShell, SWT.NONE);
        vServerGroup.setText("Server");
        vServerGroup.setBounds(10, 318, 301, 166);
        
        Label vServerIPAddressLabel = new Label(vServerGroup, SWT.NONE);
        vServerIPAddressLabel.setText("IP-Adresse");
        vServerIPAddressLabel.setBounds(10, 15, 90, 17);
        
        Label vServerTeamportLabel = new Label(vServerGroup, SWT.NONE);
        vServerTeamportLabel.setText("Teamport");
        vServerTeamportLabel.setBounds(216, 15, 75, 17);

        Label vServerGamevaluesLabel = new Label(vServerGroup, SWT.NONE);
        vServerGamevaluesLabel.setText("Serverkonstanten");
        vServerGamevaluesLabel.setBounds(10, 106, 281, 17);
        
        mServerIPAddress = new Text(vServerGroup, SWT.BORDER);
        mServerIPAddress.setBounds(10, 38, 200, 27);
        mServerIPAddress.setText( Core.getInstance().getBotinformation().getServerIP().getHostAddress() );
                
        mServerTeamport = new Text(vServerGroup, SWT.BORDER);
        mServerTeamport.setBounds(216, 38, 75, 27);
        mServerTeamport.setText( Integer.toString( Core.getInstance().getBotinformation().getServerPort() ) );
        
        mServerConnect = new Button(vServerGroup, SWT.NONE);
        mServerConnect.setEnabled(false);
        mServerConnect.setBounds(10, 71, 137, 29);
        mServerConnect.setText("Connect");
        
        mServerReconnect = new Button(vServerGroup, SWT.NONE);
        mServerReconnect.setEnabled(false);
        mServerReconnect.setBounds(154, 71, 137, 29);
        mServerReconnect.setText("Reconnect");
        
        mServerDisconnect = new Button(vServerGroup, SWT.NONE);
        mServerDisconnect.setEnabled(false);
        mServerDisconnect.setBounds(10, 71, 281, 29);
        mServerDisconnect.setText("Disconnect");
        mServerDisconnect.setVisible( false );
        
        mServerGamevaluesSelector = new Combo(vServerGroup, SWT.READ_ONLY);
        mServerGamevaluesSelector.setVisibleItemCount(3);
        mServerGamevaluesSelector.setItems( GamevalueNames.getGamevalueNamesAsStringArray() );
        mServerGamevaluesSelector.setBounds(10, 127, 200, 29);
        mServerGamevaluesSelector.select(0);
        
        mServerGamevalues = new Text(vServerGroup, SWT.BORDER);
        mServerGamevalues.setBounds(216, 129, 75, 27);
        mServerGamevalues.setText( Float.toString( Core.getInstance().getBotinformation().getGamevalue(GamevalueNames.valueOf( mServerGamevaluesSelector.getText() ) ) ) );
        
        setServerGroupListener();
        
        Group vAiGroup = new Group(mCoreShell, SWT.NONE);
        vAiGroup.setText("AI");
        vAiGroup.setBounds(10, 490, 301, 257);
        
        Label vAIArchiveLabel = new Label(vAiGroup, SWT.NONE);
        vAIArchiveLabel.setText("Archive");
        vAIArchiveLabel.setBounds(10, 15, 111, 17);
        
        Label vAIClassnameLabel = new Label(vAiGroup, SWT.NONE);
        vAIClassnameLabel.setText("Klassenname");
        vAIClassnameLabel.setBounds(10, 71, 111, 17);
        
        Label vAIArgumentsLabel = new Label(vAiGroup, SWT.NONE);
        vAIArgumentsLabel.setText("Argumente");
        vAIArgumentsLabel.setBounds(10, 127, 111, 17);
        
        mAIInitialisieren = new Button(vAiGroup, SWT.NONE);
        mAIInitialisieren.setBounds(10, 183, 281, 29);
        mAIInitialisieren.setText("Initialisieren");
        
        mAIBeenden = new Button(vAiGroup, SWT.NONE);
        mAIBeenden.setEnabled(false);
        mAIBeenden.setVisible( false );
        mAIBeenden.setText("Beenden");
        mAIBeenden.setBounds(10, 184, 281, 28);
        
        mAIKonfiguration = new Button(vAiGroup, SWT.NONE);
        mAIKonfiguration.setEnabled(false);
        mAIKonfiguration.setText("Konfiguration");
        mAIKonfiguration.setBounds(10, 218, 281, 29);

        mAIArchive = new Text(vAiGroup, SWT.BORDER);
        mAIArchive.setBounds(10, 38, 281, 27);
        mAIArchive.setText( Core.getInstance().getBotinformation().getAIArchive() ); 
        
        mAIClassname = new Text(vAiGroup, SWT.BORDER);
        mAIClassname.setText( Core.getInstance().getBotinformation().getAIClassname() );
        mAIClassname.setBounds(10, 94, 281, 27);
        
        mAIArguments = new Text(vAiGroup, SWT.BORDER);
        mAIArguments.setText( Core.getInstance().getBotinformation().getAIArgs() );
        mAIArguments.setBounds(10, 150, 281, 27);
        
        setAIGroupListener();
        
        mCoreShell.open();
        mCoreShell.layout();
        while ( !mCoreShell.isDisposed() ) {
            if ( !display.readAndDispatch() ) {
                display.sleep();
            }
        }
    }
    
    private void setBotGroupListener(){
        
        mBotName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setBotname( mBotName.getText() );
            }
        });
        mBotRcID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setRcId( Integer.valueOf( mBotRcID.getText() ) );
            }
        });
        mBotVtID.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent arg0) {
                isANumber( arg0 );
            }
        });
        mBotVtID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setVtId( Integer.valueOf( mBotVtID.getText() ) );
            }
        });
        mBotRcID.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent arg0) {
                isANumber( arg0 );
            }
        });
        mBotPort.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setBotPort( Integer.valueOf( mBotPort.getText() ) );
            }
        });
        mBotPort.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent arg0) {
                isANumber( arg0 );
            }
        });
        mBotTeamname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setBotname( mBotTeamname.getText() );
            }
        });
        mBotTeam.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                Core.getInstance().getBotinformation().setTeam( Teams.valueOf( mBotTeam.getText() ) );
            }
        });
        
    }
    
    private void setServerGroupListener(){
        
        mServerIPAddress.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                try {
                    InetAddress vNewAddress = InetAddress.getByName( mServerIPAddress.getText() );
                    Core.getInstance().getBotinformation().setServerIP( vNewAddress );
                } catch ( UnknownHostException e ) {
                    mServerIPAddress.setText( Core.getInstance().getBotinformation().getServerIP().getHostAddress() );
                }
            }
        });
        mServerTeamport.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setServerPort( Integer.valueOf( mServerTeamport.getText() ) );
            }
        });
        mServerTeamport.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent arg0) {
                isANumber( arg0 );
            }
        });
        mServerGamevalues.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setGamevalue( GamevalueNames.valueOf( mServerGamevaluesSelector.getText() ), Float.valueOf( mServerGamevalues.getText() ) ); 
            }
        });
        mServerGamevalues.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent arg0) {
                isAFloat( arg0 );
            }
        });
        mServerGamevaluesSelector.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                mServerGamevalues.setText( Float.toString( Core.getInstance().getBotinformation().getGamevalue( GamevalueNames.valueOf( mServerGamevaluesSelector.getText() ) ) ) ); 
            }
        });
        mServerConnect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {

                Core.getInstance().getBotinformation().setReconnect( false );
                connectToServerUIChanges( true );
                Core.getInstance().startServerConnection();
                mBotPort.setText( Integer.toString( Core.getInstance().getBotinformation().getBotPort() ) );
                
            }
        });
        mServerReconnect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                
                Core.getInstance().getBotinformation().setReconnect( true );
                connectToServerUIChanges( true );
                Core.getInstance().startServerConnection();
                
            }
        });
        mServerDisconnect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                
                connectToServerUIChanges( false );
                Core.getInstance().stopServerConnection();
                
            }
        });
        
    }
    
    private void connectToServerUIChanges( boolean aWay ){
        
        mServerDisconnect.setEnabled( aWay );
        mServerDisconnect.setVisible( aWay );
        mServerConnect.setVisible( !aWay );
        mServerReconnect.setVisible( !aWay );

        mBotPort.setEnabled( !aWay );
        mBotPort.setEditable( !aWay );
        mServerIPAddress.setEnabled( !aWay );
        mServerIPAddress.setEditable( !aWay );
        mServerTeamport.setEnabled( !aWay );
        mServerTeamport.setEditable( !aWay );
        
    }
    
    private void setAIGroupListener(){

        mAIArchive.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setAIArchive( mAIArchive.getText() );
            }
        });
        mAIClassname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setAIClassname( mAIClassname.getText() );
            }
        });
        mAIArguments.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                Core.getInstance().getBotinformation().setAIArgs( mAIArguments.getText() );
            }
        });
        mAIInitialisieren.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                startAIUIChanges( true );
                Core.getInstance().startAI();
            }
        });
        mAIBeenden.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                startAIUIChanges( false );
                Core.getInstance().stopAI();
            }
        });
        mAIKonfiguration.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                
                Display vDefaultDisplay = Display.getDefault();
                Shell vAIConfigurationShell = Core.getInstance().getAI().getConfigurationWindow( vDefaultDisplay );
                
                vAIConfigurationShell.open();
                vAIConfigurationShell.layout();
                while (!vAIConfigurationShell.isDisposed()) {
                    if (!vDefaultDisplay.readAndDispatch()) {
                        vDefaultDisplay.sleep();
                    }
                }
                
            }
        });
        
    }
    
    private void startAIUIChanges( boolean aWay ){
        
        mAIBeenden.setEnabled( aWay );
        mAIBeenden.setVisible( aWay );
        mAIInitialisieren.setEnabled( !aWay );
        mAIInitialisieren.setVisible( !aWay );
        mAIKonfiguration.setEnabled( aWay );
        
        mServerConnect.setEnabled( aWay );
        mServerReconnect.setEnabled( aWay );
        
    }

    private void updateStatus(){

        if(mStatusListener.mNetworkConnected){
            
            mStatusNetworkConnectedPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/green_signal.gif"));
            
        }else{
            
            mStatusNetworkConnectedPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
            
        }
        
        if(mStatusListener.mNetworkIncoming){
            
            mStatusNetworkIncomingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/green_signal.gif"));
            
        }else{
            
            mStatusNetworkIncomingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
            
        }
        
        if(mStatusListener.mNetworkOutgoing){
            
            mStatusNetworkOutgoingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/green_signal.gif"));
            
        }else{
            
            mStatusNetworkOutgoingPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
            
        }
        
        if(mStatusListener.mAiRunning){
            
            mStatusAiRunsPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/green_signal.gif"));
            
        }else{
            
            mStatusAiRunsPictureLabel.setImage(SWTResourceManager.getImage(CoreWindow.class, "/res/red_signal.gif"));
            
        }
        
    }
    
    public void isAFloat( final VerifyEvent aEvent ) {
        
        try{
            Float.valueOf( aEvent.text ); //doof!
            return;
        } catch ( NumberFormatException vNaN ){
            
        }
        
        if( aEvent.character == '.' ){
            return;
        }
        isANumber( aEvent );
        
    }
    
    public void isANumber( final VerifyEvent aEvent ) {
                
        try{
            Integer.valueOf( aEvent.text ); //doof!
            return;
        } catch ( NumberFormatException vNaN ){
            
        }
        
        switch ( aEvent.keyCode ) {
            case SWT.BS:           // Backspace
            case SWT.DEL:          // Delete
            case SWT.HOME:         // Home
            case SWT.END:          // End
            case SWT.ARROW_LEFT:   // Left arrow
            case SWT.ARROW_RIGHT:  // Right arrow
                return;
            }
        if ( !Character.isDigit( aEvent.character ) ) {
            aEvent.doit = false;  // disallow the action
        }
    }
    
    private class StatusListener extends Thread {
        
        private CoreWindow mCoreWindow;
        
        private boolean mNetworkConnected;
        private boolean mNetworkIncoming;
        private boolean mNetworkOutgoing;
        
        private boolean mAiRunning;

        private boolean mListenToStatus;

        public StatusListener( CoreWindow aCoreWindow ) {
            
            mCoreWindow = aCoreWindow;
            
            mNetworkConnected = false;
            mNetworkIncoming = false;
            mNetworkOutgoing = false;
            
            mAiRunning = false;
            
        }
        
        private void valuesChanged(){
            
            Display.getDefault().asyncExec(
                new Runnable() {
                    public void run(){
                        mCoreWindow.updateStatus();
                    }
                }
            );
            
        }
        
        @Override
        public void start(){
            
            mListenToStatus = true;
            super.start();
            
        }
        
        public void stopListener(){
            
            mListenToStatus = false;

        }
        
        public void run(){
            
            boolean changed = false;
            
            while( mListenToStatus ){
                
                try {
                    
                    Thread.sleep(1000); // besser machen!

                    if(    ( Core.getInstance().getServerConnection() != null && Core.getInstance().getServerConnection().isConnected() != mNetworkConnected ) 
                        || ( Core.getInstance().getServerConnection() == null && mNetworkConnected ) ){
                        
                        mNetworkConnected = !mNetworkConnected;
                        changed = true;
                        
                    } 
                    
                    if( ToServerManagement.getInstance().isSendingMessages() != mNetworkOutgoing ){
                        
                        mNetworkOutgoing= !mNetworkOutgoing;
                        changed = true;
                        
                    }
                    
                    if( FromServerManagement.getInstance().isReceivingMessages() != mNetworkIncoming  ){
                        
                        mNetworkIncoming = !mNetworkIncoming;
                        changed = true;
                        
                    }
                    
                    if(    ( Core.getInstance().getAI() != null && Core.getInstance().getAI().isRunning() != mAiRunning )
                        || ( Core.getInstance().getAI() == null && mAiRunning ) ){
                        
                        mAiRunning = !mAiRunning;
                        changed = true;
                        
                    }
                    
                    if( changed ){

                        changed = false;
                        valuesChanged();
                        
                    }
                    
                } catch ( Exception e ) {
                    
                    // Logging einbauen besser!!!
                    e.printStackTrace();
                    
                }
                
            }
            
        }
        
    }
    
}
