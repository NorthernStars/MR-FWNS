package fwns_botremotecontrol.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;
import fwns_botremotecontrol.core.RemoteBot;
import fwns_network.botremotecontrol.BotStatusType;

public class BotFrame extends JPanel {

    private static final long serialVersionUID = 21785150238115621L;
    
    private JLabel mPanelHeadPanelFrontLabelBotname;
    private JPanel mPanelHeadPanelFront;
    private boolean mExpanded = true;
    private int mMaxLinesInLog = 1000;
    private final Executor mExecutor = Executors.newFixedThreadPool( 1 );
    public JButton mPanelHeadPanelBackLabelButtonExpandContract;
    private JPanel mPanelHead;
    public JTabbedPane mTabbedPane;
    private JPanel vPanelStatus;
    private JPanel vPanelData;
    private JPanel vPanelConnection;
    private JPanel vLoggingPanelControlRight;
    private JScrollPane scrollPane_Logging;
    private JTextArea mLoggingTextarea;
    private JPopupMenu popupMenu;
    private JMenuItem mntmCopy;
    private JMenuItem mntmSetMaxLines;
    private JButton mLoggingControlButtonDisconnectlogger;
    private JButton mLoggingControlButtonConnectlogger;
    @SuppressWarnings("rawtypes")
	private JComboBox mTabbedPanePanelLoggingPanelControlComboBoxLoglevel;
    private JPanel vPanelAIPanelArguments;
    private JTextField mAIExecutionTextfieldAiarchive;
    private JTextField mAIExecutionTextfieldAiclass;
    private JTextField mConnectionTextfieldServeraddress;
    private JTextField mConnectionTextfieldServerteamport;
    private JTextField mConnectionTextfieldBotaddress;
    private JTextField mConnectionTextfieldBotport;
    private JTextField mDataGeneralTextfieldBotname;
    private JTextField mDataGeneralTextFieldBotteamname;
    private JTextField mDataGeneralTextfieldRcid;
    private JTextField mDataGeneralTextfieldVtid;
    private JLabel mPanelHeadPanelBackLabelNetworkConnected;
    private JLabel mPanelHeadPanelBackLabelNetworkTrafficOutgoing;
    private JLabel mPanelHeadPanelBackLabelNetworkTrafficIncoming;
    private JLabel mPanelHeadPanelBackLabelAIRunning;
    private JLabel mPanelHeadPanelBackLabelAILoaded;
    private JLabel mStatusNetworkstatusLabelConnectedstatus;
    private JLabel mStatusAiLabelRunningstatus;
    private JLabel mStatusNetworkstatusLabelTrafficoutgoingstatus;
    private JLabel mStatusNetworkstatusLabelTrafficincomingstatus;
    private JLabel mStatusAiLabelLoadedstatus;
    private JTable mDataPanelServerconstantsTable;
    @SuppressWarnings("rawtypes")
	private JComboBox mDataGeneralComboBoxTeam;
    private JButton mConnectionButtonConnect;
    private JButton mConnectionButtonReconnect;
    private JButton mConnectionButtonDisconnect;
    private JButton mAIArgumentsButtonExecute;
    private JTextArea mAIArgumentsTextareaAiarguments;
    private JButton mAIExecutionButtonAiinitialise;
    private JButton mAIExecutionButtonAiunpause;
    private JButton mAIExecutionButtonAidispose;
    private JButton mAIExecutionButtonAipause;

    private RemoteBot mTheRemoteBot;
    private JButton mDataGeneralButtonChangeData;

    private BotInformation mBotInformation;
    public JButton btnStatusButtonStopBot;

    /**
     * Create the panel.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public BotFrame() {
        
        setBorder(new LineBorder(UIManager.getColor("TabbedPane.selected"), 3));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(349, 278));
        setMinimumSize(new Dimension(400, 45));
        setMaximumSize(new Dimension(10000, 45));
        
        mPanelHead = new JPanel();
        mPanelHead.setPreferredSize(new Dimension(200, 40));
        mPanelHead.setMinimumSize(new Dimension(200, 40));
        mPanelHead.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        add(mPanelHead, BorderLayout.NORTH);
        mPanelHead.setLayout(new BorderLayout(0, 0));
        
        mPanelHeadPanelFront = new JPanel();
        mPanelHead.add(mPanelHeadPanelFront, BorderLayout.CENTER);
        mPanelHeadPanelFront.setLayout(null);
        
        setEnabled( false );
        
        JButton vPanelHeadPanelFrontButtonExit = new JButton("");
        vPanelHeadPanelFrontButtonExit.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

            	// TODO: nachdenken! Frage nicht eindeutig!
                int vSelection = JOptionPane.showConfirmDialog(
                        Botcontrol.getInstance().getMainFrame(),
                        "Would you like to close the Bot?",
                        "Closing the Bot",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                
                if ( vSelection == 0 || vSelection == 1 ){
                    
                    setEnabled( false );
                    setVisible( false );
                    
                    Botcontrol.getInstance().removeBotframe( (BotFrame) mPanelHead.getParent() );
                    close( vSelection == 0 );
                
                }
                                
            }
        });
        vPanelHeadPanelFrontButtonExit.setMargin(new Insets(0, 0, 0, 0));
        vPanelHeadPanelFrontButtonExit.setPressedIcon(new ImageIcon(BotFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/close-pressed.gif")));
        vPanelHeadPanelFrontButtonExit.setPreferredSize(new Dimension(28, 28));
        vPanelHeadPanelFrontButtonExit.setMinimumSize(new Dimension(28, 28));
        vPanelHeadPanelFrontButtonExit.setMaximumSize(new Dimension(28, 28));
        vPanelHeadPanelFrontButtonExit.setIconTextGap(0);
        vPanelHeadPanelFrontButtonExit.setHorizontalTextPosition(SwingConstants.CENTER);
        vPanelHeadPanelFrontButtonExit.setIcon(new ImageIcon(BotFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
        vPanelHeadPanelFrontButtonExit.setBounds(5, 5, 28, 28);
        mPanelHeadPanelFront.add(vPanelHeadPanelFrontButtonExit);
        
        mPanelHeadPanelFrontLabelBotname = new JLabel("New label");
        mPanelHeadPanelFrontLabelBotname.setBounds(40, 5, 160, 28);
        mPanelHeadPanelFront.add(mPanelHeadPanelFrontLabelBotname);

        mPanelHeadPanelFront.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                
                mPanelHeadPanelFrontLabelBotname.setSize( mPanelHeadPanelFront.getWidth() - 45, mPanelHeadPanelFrontLabelBotname.getHeight() );
                
            }
        });
        
        JPanel vPanelHeadPanelBack = new JPanel();
        mPanelHead.add(vPanelHeadPanelBack, BorderLayout.EAST);
        
        mPanelHeadPanelBackLabelNetworkConnected = new JLabel("");
        mPanelHeadPanelBackLabelNetworkConnected.setToolTipText("Networkconnection");
        mPanelHeadPanelBackLabelNetworkConnected.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mPanelHeadPanelBackLabelNetworkConnected.setPreferredSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelNetworkConnected.setIconTextGap(0);
        mPanelHeadPanelBackLabelNetworkConnected.setHorizontalTextPosition(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelNetworkConnected.setHorizontalAlignment(SwingConstants.CENTER);
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelNetworkConnected);
        
        mPanelHeadPanelBackLabelNetworkTrafficIncoming = new JLabel("");
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setToolTipText("Incoming Traffic");
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setPreferredSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIconTextGap(0);
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setHorizontalTextPosition(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelNetworkTrafficIncoming.setHorizontalAlignment(SwingConstants.CENTER);
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelNetworkTrafficIncoming);
        
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing = new JLabel("");
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setToolTipText("Outgoing Traffic");
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setPreferredSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIconTextGap(0);
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setHorizontalTextPosition(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setHorizontalAlignment(SwingConstants.CENTER);
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelNetworkTrafficOutgoing);
        
        mPanelHeadPanelBackLabelAIRunning = new JLabel("");
        mPanelHeadPanelBackLabelAIRunning.setToolTipText("AI Running");
        mPanelHeadPanelBackLabelAIRunning.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mPanelHeadPanelBackLabelAIRunning.setPreferredSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelAIRunning.setIconTextGap(0);
        mPanelHeadPanelBackLabelAIRunning.setHorizontalTextPosition(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelAIRunning.setHorizontalAlignment(SwingConstants.CENTER);
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelAIRunning);
        
        mPanelHeadPanelBackLabelAILoaded = new JLabel("");
        mPanelHeadPanelBackLabelAILoaded.setToolTipText("AI Loaded");
        mPanelHeadPanelBackLabelAILoaded.setHorizontalAlignment(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelAILoaded.setIconTextGap(0);
        mPanelHeadPanelBackLabelAILoaded.setHorizontalTextPosition(SwingConstants.CENTER);
        mPanelHeadPanelBackLabelAILoaded.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mPanelHeadPanelBackLabelAILoaded.setPreferredSize(new Dimension(28, 28));
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelAILoaded);
        
        mPanelHeadPanelBackLabelButtonExpandContract = new JButton("");
        
        mPanelHeadPanelBackLabelButtonExpandContract.setPreferredSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelButtonExpandContract.setMinimumSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelButtonExpandContract.setMaximumSize(new Dimension(28, 28));
        mPanelHeadPanelBackLabelButtonExpandContract.setIcon(new ImageIcon(BotFrame.class.getResource("/res/contract.gif")));
        vPanelHeadPanelBack.add(mPanelHeadPanelBackLabelButtonExpandContract);
        
        mTabbedPane = new JTabbedPane(SwingConstants.TOP);
        add(mTabbedPane, BorderLayout.CENTER);
        
        vPanelStatus = new JPanel();
        mTabbedPane.addTab("Status", null, vPanelStatus, null);
        vPanelStatus.setLayout(null);
        
        JPanel vStatusPanelNetworkstatus = new JPanel();
        vStatusPanelNetworkstatus.setBorder(new TitledBorder(null, "Network", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        vStatusPanelNetworkstatus.setBounds(10, 11, 150, 100);
        vPanelStatus.add(vStatusPanelNetworkstatus);
        vStatusPanelNetworkstatus.setLayout(null);
        
        JLabel vStatusNetworkstatusLabelConnected = new JLabel("Connected....................");
        vStatusNetworkstatusLabelConnected.setBounds(10, 15, 113, 17);
        vStatusPanelNetworkstatus.add(vStatusNetworkstatusLabelConnected);
        
        mStatusNetworkstatusLabelConnectedstatus = new JLabel("");
        mStatusNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mStatusNetworkstatusLabelConnectedstatus.setBounds(120, 15, 17, 17);
        vStatusPanelNetworkstatus.add(mStatusNetworkstatusLabelConnectedstatus);
        
        JLabel vStatusNetworkstatusLabelTrafficincoming = new JLabel("Traffic incoming.............");
        vStatusNetworkstatusLabelTrafficincoming.setBounds(10, 43, 113, 17);
        vStatusPanelNetworkstatus.add(vStatusNetworkstatusLabelTrafficincoming);
        
        mStatusNetworkstatusLabelTrafficincomingstatus = new JLabel("");
        mStatusNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mStatusNetworkstatusLabelTrafficincomingstatus.setBounds(120, 43, 17, 17);
        vStatusPanelNetworkstatus.add(mStatusNetworkstatusLabelTrafficincomingstatus);
        
        JLabel vStatusNetworkstatusLabelTrafficoutgoing = new JLabel("Traffic outgoing............");
        vStatusNetworkstatusLabelTrafficoutgoing.setBounds(10, 71, 113, 17);
        vStatusPanelNetworkstatus.add(vStatusNetworkstatusLabelTrafficoutgoing);
        
        mStatusNetworkstatusLabelTrafficoutgoingstatus = new JLabel("");
        mStatusNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mStatusNetworkstatusLabelTrafficoutgoingstatus.setBounds(120, 71, 17, 17);
        vStatusPanelNetworkstatus.add(mStatusNetworkstatusLabelTrafficoutgoingstatus);
        
        JPanel vStatusPanelAi = new JPanel();
        vStatusPanelAi.setLayout(null);
        vStatusPanelAi.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "AI", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        vStatusPanelAi.setBounds(170, 11, 150, 73);
        vPanelStatus.add(vStatusPanelAi);
        
        JLabel vStatusAiLabelRunning = new JLabel("Running.........................");
        vStatusAiLabelRunning.setBounds(10, 15, 113, 17);
        vStatusPanelAi.add(vStatusAiLabelRunning);
        
        mStatusAiLabelRunningstatus = new JLabel("");
        mStatusAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mStatusAiLabelRunningstatus.setBounds(120, 15, 17, 17);
        vStatusPanelAi.add(mStatusAiLabelRunningstatus);
        
        JLabel vStatusAiLabelLoaded = new JLabel("Loaded..........................");
        vStatusAiLabelLoaded.setBounds(10, 43, 113, 17);
        vStatusPanelAi.add(vStatusAiLabelLoaded);
        
        mStatusAiLabelLoadedstatus = new JLabel("");
        mStatusAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mStatusAiLabelLoadedstatus.setBounds(120, 43, 17, 17);
        vStatusPanelAi.add(mStatusAiLabelLoadedstatus);
        
        JButton vStatusButtonManuallystatuscheck = new JButton("Manually check status");
        vStatusButtonManuallystatuscheck.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                try {
                    updateStatus( null );
                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        vStatusButtonManuallystatuscheck.setBounds(10, 113, 310, 23);
        vPanelStatus.add(vStatusButtonManuallystatuscheck);
        
        btnStatusButtonStopBot = new JButton("Stop bot");
        btnStatusButtonStopBot.setEnabled(false);
        btnStatusButtonStopBot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if( mTheRemoteBot.getBotLoader() != null ){
        			// disable button
        			btnStatusButtonStopBot.setEnabled( !mTheRemoteBot.getBotLoader().stopBot() );
        			
        			// remove bot frame and close bot
        			setEnabled( false );
                    setVisible( false );                    
                    Botcontrol.getInstance().removeBotframe( (BotFrame) mPanelHead.getParent() );
                    close( true );
        		}
        	}
        });
        btnStatusButtonStopBot.setBounds(10, 141, 310, 23);
        vPanelStatus.add(btnStatusButtonStopBot);
        
        vPanelData = new JPanel();
        mTabbedPane.addTab("Data", null, vPanelData, null);
        vPanelData.setLayout(new BorderLayout(0, 0));
        
        JPanel vDataPanelGeneral = new JPanel();
        vDataPanelGeneral.setPreferredSize(new Dimension(200, 10));
        vPanelData.add(vDataPanelGeneral, BorderLayout.WEST);
        vDataPanelGeneral.setLayout(null);
        
        JLabel vDataGeneralLabelBotname = new JLabel("Botname");
        vDataGeneralLabelBotname.setBounds(10, 11, 180, 14);
        vDataPanelGeneral.add(vDataGeneralLabelBotname);
        
        mDataGeneralTextfieldBotname = new JTextField();
        mDataGeneralTextfieldBotname.setBounds(10, 26, 180, 20);
        vDataPanelGeneral.add(mDataGeneralTextfieldBotname);
        mDataGeneralTextfieldBotname.setColumns(10);
        
        JLabel vDataGeneralLabelBotteamname = new JLabel("Teamname");
        vDataGeneralLabelBotteamname.setBounds(10, 51, 180, 14);
        vDataPanelGeneral.add(vDataGeneralLabelBotteamname);
        
        mDataGeneralTextFieldBotteamname = new JTextField();
        mDataGeneralTextFieldBotteamname.setBounds(10, 65, 180, 20);
        vDataPanelGeneral.add(mDataGeneralTextFieldBotteamname);
        mDataGeneralTextFieldBotteamname.setColumns(10);
        
        JLabel vDataGeneralLabelRcid = new JLabel("RC-ID");
        vDataGeneralLabelRcid.setBounds(10, 90, 35, 14);
        vDataPanelGeneral.add(vDataGeneralLabelRcid);
        
        JLabel vDataGeneralLabelVtid = new JLabel("VT-ID");
        vDataGeneralLabelVtid.setBounds(55, 90, 35, 14);
        vDataPanelGeneral.add(vDataGeneralLabelVtid);
        
        JLabel vDataGeneralLabelTeam = new JLabel("Team color");
        vDataGeneralLabelTeam.setBounds(100, 90, 90, 14);
        vDataPanelGeneral.add(vDataGeneralLabelTeam);
        
        mDataGeneralTextfieldRcid = new JTextField();
        mDataGeneralTextfieldRcid.setText("22");
        mDataGeneralTextfieldRcid.setBounds(10, 104, 35, 20);
        vDataPanelGeneral.add(mDataGeneralTextfieldRcid);
        mDataGeneralTextfieldRcid.setColumns(10);
        
        mDataGeneralTextfieldVtid = new JTextField();
        mDataGeneralTextfieldVtid.setBounds(55, 104, 35, 20);
        vDataPanelGeneral.add(mDataGeneralTextfieldVtid);
        mDataGeneralTextfieldVtid.setColumns(10);
        
        mDataGeneralComboBoxTeam = new JComboBox();
        mDataGeneralComboBoxTeam.setModel(new DefaultComboBoxModel(Teams.values()));
        mDataGeneralComboBoxTeam.setBounds(100, 103, 90, 22);
        vDataPanelGeneral.add(mDataGeneralComboBoxTeam);
        
        mDataGeneralButtonChangeData = new JButton("Change Data");
        mDataGeneralButtonChangeData.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
            }
        });
        mDataGeneralButtonChangeData.setBounds(10, 135, 180, 23);
        vDataPanelGeneral.add(mDataGeneralButtonChangeData);
        
        JScrollPane scrollPane_Serverconstants = new JScrollPane();
        vPanelData.add(scrollPane_Serverconstants, BorderLayout.CENTER);
        
        JPanel vDataPanelServerconstants = new JPanel();
        scrollPane_Serverconstants.setViewportView(vDataPanelServerconstants);
        vDataPanelServerconstants.setLayout(new BorderLayout(0, 0));
        
        JLabel vDataPanelServerconstantsLabel = new JLabel("Serverconstants");
        vDataPanelServerconstants.add(vDataPanelServerconstantsLabel, BorderLayout.NORTH);
        
        mDataPanelServerconstantsTable = new JTable();
        mDataPanelServerconstantsTable.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "New column", "New column"
            }
        ));
        vDataPanelServerconstants.add(mDataPanelServerconstantsTable, BorderLayout.CENTER);
        
        vPanelConnection = new JPanel();
        mTabbedPane.addTab("Connection", null, vPanelConnection, null);
        vPanelConnection.setLayout(new BorderLayout(0, 0));
        
        JPanel vPanelConnectionPanel2 = new JPanel();
        vPanelConnectionPanel2.setPreferredSize(new Dimension(300, 10));
        vPanelConnection.add(vPanelConnectionPanel2, BorderLayout.WEST);
        vPanelConnectionPanel2.setLayout(null);
        
        JLabel vConnectionLabelServeraddress = new JLabel("Server IP-Address");
        vConnectionLabelServeraddress.setBounds(10, 11, 150, 14);
        vPanelConnectionPanel2.add(vConnectionLabelServeraddress);
        
        mConnectionTextfieldServeraddress = new JTextField();
        mConnectionTextfieldServeraddress.setBounds(10, 27, 150, 20);
        vPanelConnectionPanel2.add(mConnectionTextfieldServeraddress);
        mConnectionTextfieldServeraddress.setColumns(10);
        
        mConnectionTextfieldServerteamport = new JTextField();
        mConnectionTextfieldServerteamport.setBounds(170, 27, 86, 20);
        vPanelConnectionPanel2.add(mConnectionTextfieldServerteamport);
        mConnectionTextfieldServerteamport.setColumns(10);
        
        JLabel vConnectionLabelServerteamport = new JLabel("Teamport");
        vConnectionLabelServerteamport.setBounds(170, 11, 86, 14);
        vPanelConnectionPanel2.add(vConnectionLabelServerteamport);
        
        JLabel vConnectionLabelBotaddress = new JLabel("Bot IPAddress");
        vConnectionLabelBotaddress.setBounds(10, 58, 150, 14);
        vPanelConnectionPanel2.add(vConnectionLabelBotaddress);
        
        mConnectionTextfieldBotaddress = new JTextField();
        mConnectionTextfieldBotaddress.setEditable(false);
        mConnectionTextfieldBotaddress.setBounds(10, 75, 150, 20);
        vPanelConnectionPanel2.add(mConnectionTextfieldBotaddress);
        mConnectionTextfieldBotaddress.setColumns(10);
        
        JLabel vConnectionLabelBotport = new JLabel("Botport");
        vConnectionLabelBotport.setBounds(170, 58, 86, 14);
        vPanelConnectionPanel2.add(vConnectionLabelBotport);
        
        mConnectionTextfieldBotport = new JTextField();
        mConnectionTextfieldBotport.setBounds(170, 75, 86, 20);
        vPanelConnectionPanel2.add(mConnectionTextfieldBotport);
        mConnectionTextfieldBotport.setColumns(10);
        
        mConnectionButtonConnect = new JButton("Connect");
        mConnectionButtonConnect.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
                 mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {
                        
                        try {
                            
                            if( !mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) && mTheRemoteBot.getTheBot().connectBot() ){
                                
                                SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                    @Override
									public void run() {
                                        
                                        try {
                                            changeConnectionButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) );
                                        } catch ( RemoteException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                });
                                
                                
                            }
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
                
            }
        });
        mConnectionButtonConnect.setBounds(10, 106, 118, 23);
        vPanelConnectionPanel2.add(mConnectionButtonConnect);
        
        mConnectionButtonReconnect = new JButton("Reconnect");
        mConnectionButtonReconnect.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();

                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {
                        
                        try {
                            if( !mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) && mTheRemoteBot.getTheBot().reconnectBot() ){
                                
                                SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                    @Override
									public void run() {
                                        
                                        try {
                                            changeConnectionButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) );
                                        } catch ( RemoteException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                });
                                
                                
                            }
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mConnectionButtonReconnect.setBounds(138, 106, 118, 23);
        vPanelConnectionPanel2.add(mConnectionButtonReconnect);
        
        mConnectionButtonDisconnect = new JButton("Disconnect");
        mConnectionButtonDisconnect.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

                updateTheRemoteBot();
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) && mTheRemoteBot.getTheBot().disconnectBot() ){

                                SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                    @Override
									public void run() {
                                        
                                        try {

                                            changeConnectionButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) );
                                        } catch ( RemoteException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                });
                                
                                
                            }
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mConnectionButtonDisconnect.setVisible(false);
        mConnectionButtonDisconnect.setEnabled(false);
        mConnectionButtonDisconnect.setBounds(10, 106, 246, 23);
        vPanelConnectionPanel2.add(mConnectionButtonDisconnect);
        
        JPanel vPanelAI = new JPanel();
        mTabbedPane.addTab("AI", null, vPanelAI, null);
        vPanelAI.setLayout(new BorderLayout(0, 0));
        
        JPanel vPanelAIPanelExecution = new JPanel();
        vPanelAIPanelExecution.setPreferredSize(new Dimension(200, 10));
        vPanelAIPanelExecution.setSize(new Dimension(200, 0));
        vPanelAI.add(vPanelAIPanelExecution, BorderLayout.WEST);
        vPanelAIPanelExecution.setLayout(null);
        
        JLabel vAIExecutionLabelAiarchive = new JLabel("Archive");
        vAIExecutionLabelAiarchive.setBounds(10, 11, 180, 14);
        vPanelAIPanelExecution.add(vAIExecutionLabelAiarchive);
        
        mAIExecutionTextfieldAiarchive = new JTextField();
        mAIExecutionTextfieldAiarchive.setBounds(10, 29, 180, 20);
        vPanelAIPanelExecution.add(mAIExecutionTextfieldAiarchive);
        mAIExecutionTextfieldAiarchive.setColumns(50);
        
        JLabel vAIExecutionLabelAiclass = new JLabel("Classname");
        vAIExecutionLabelAiclass.setBounds(10, 59, 180, 14);
        vPanelAIPanelExecution.add(vAIExecutionLabelAiclass);
        
        mAIExecutionTextfieldAiclass = new JTextField();
        mAIExecutionTextfieldAiclass.setText("ais/example_ai.jar");
        mAIExecutionTextfieldAiclass.setBounds(10, 77, 180, 20);
        vPanelAIPanelExecution.add(mAIExecutionTextfieldAiclass);
        mAIExecutionTextfieldAiclass.setColumns(10);
        
        mAIExecutionButtonAiinitialise = new JButton("Initialise");
        mAIExecutionButtonAiinitialise.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            if( mTheRemoteBot.getTheBot().initialiseAI() ){

                                SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                    @Override
									public void run() {
                                        
                                        try {
                                            changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
                                        } catch ( RemoteException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                });
                                
                                
                            }
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mAIExecutionButtonAiinitialise.setBounds(10, 108, 180, 23);
        vPanelAIPanelExecution.add(mAIExecutionButtonAiinitialise);
        
        mAIExecutionButtonAiunpause = new JButton("Resume");
        mAIExecutionButtonAiunpause.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            if( mTheRemoteBot.getTheBot().resumeAI() ){

                                SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                    @Override
									public void run() {
                                        
                                        try {
                                            changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
                                        } catch ( RemoteException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                });
                                
                                
                            }
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mAIExecutionButtonAiunpause.setEnabled(false);
        mAIExecutionButtonAiunpause.setBounds(10, 142, 180, 23);
        vPanelAIPanelExecution.add(mAIExecutionButtonAiunpause);
        
        mAIExecutionButtonAidispose = new JButton("Dispose");
        mAIExecutionButtonAidispose.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            mTheRemoteBot.getTheBot().disposeAI();
                                    
                            SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                @Override
								public void run() {
                                    
                                    try {
                                        changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
                                    } catch ( RemoteException e ) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    
                                }
                            });
                            
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mAIExecutionButtonAidispose.setEnabled(false);
        mAIExecutionButtonAidispose.setVisible(false);
        mAIExecutionButtonAidispose.setBounds(10, 108, 180, 23);
        vPanelAIPanelExecution.add(mAIExecutionButtonAidispose);
        
        mAIExecutionButtonAipause = new JButton("Suspend");
        mAIExecutionButtonAipause.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            mTheRemoteBot.getTheBot().suspendAI();

                            SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                @Override
								public void run() {
                                    
                                    try {
                                        changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
                                    } catch ( RemoteException e ) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    
                                }
                            });
                                
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mAIExecutionButtonAipause.setVisible(false);
        mAIExecutionButtonAipause.setEnabled(false);
        mAIExecutionButtonAipause.setBounds(10, 142, 180, 23);
        vPanelAIPanelExecution.add(mAIExecutionButtonAipause);
        
        vPanelAIPanelArguments = new JPanel();
        vPanelAI.add(vPanelAIPanelArguments, BorderLayout.CENTER);
        vPanelAIPanelArguments.setLayout(new BorderLayout(0, 0));
        
        mAIArgumentsButtonExecute = new JButton("Execute Arguments");
        mAIArgumentsButtonExecute.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            mTheRemoteBot.getTheBot().executeCommandOnAI( mAIArgumentsTextareaAiarguments.getText() );

                            SwingUtilities.invokeLater( new Runnable() { //TODO: nachdenken
                                @Override
								public void run() {
                                    
                                    try {
                                        changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
                                    } catch ( RemoteException e ) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    
                                }
                            });
                                
                        } catch ( RemoteException e1 ) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                    }
                });
                
            }
        });
        mAIArgumentsButtonExecute.setEnabled(false);
        vPanelAIPanelArguments.add(mAIArgumentsButtonExecute, BorderLayout.SOUTH);
        
        JScrollPane scrollPane_aiarguments = new JScrollPane();
        vPanelAIPanelArguments.add(scrollPane_aiarguments, BorderLayout.CENTER);
        
        mAIArgumentsTextareaAiarguments = new JTextArea();
        mAIArgumentsTextareaAiarguments.setLineWrap(true);
        scrollPane_aiarguments.setViewportView(mAIArgumentsTextareaAiarguments);
        
        JPanel vPanelLogging = new JPanel();
        mTabbedPane.addTab("Logging", null, vPanelLogging, null);
        vPanelLogging.setLayout(new BorderLayout(0, 0));
        
        JPanel vTabbedPanePanelLoggingPanelControl = new JPanel();
        vPanelLogging.add(vTabbedPanePanelLoggingPanelControl, BorderLayout.NORTH);
        vTabbedPanePanelLoggingPanelControl.setLayout(new BorderLayout(0, 0));
        
        vLoggingPanelControlRight = new JPanel();
        vLoggingPanelControlRight.setMinimumSize(new Dimension(100, 25));
        vLoggingPanelControlRight.setPreferredSize(new Dimension(150, 23));
        vLoggingPanelControlRight.setSize(new Dimension(100, 23));
        vTabbedPanePanelLoggingPanelControl.add(vLoggingPanelControlRight, BorderLayout.EAST);
        vLoggingPanelControlRight.setLayout(null);
        
        mLoggingControlButtonConnectlogger = new JButton("Connect Logger");
        mLoggingControlButtonConnectlogger.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    mTheRemoteBot.connectLogListener();
                    mLoggingControlButtonConnectlogger.setEnabled(false);
                    mLoggingControlButtonConnectlogger.setVisible(false);
                    mLoggingControlButtonDisconnectlogger.setEnabled(true);
                    mLoggingControlButtonDisconnectlogger.setVisible(true);
                    
                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        mLoggingControlButtonConnectlogger.setEnabled(false);
        mLoggingControlButtonConnectlogger.setVisible(false);
        mLoggingControlButtonConnectlogger.setBounds(0, 0, 150, 23);
        vLoggingPanelControlRight.add(mLoggingControlButtonConnectlogger);
        
        mLoggingControlButtonDisconnectlogger = new JButton("Disconnect Logger");
        
        mLoggingControlButtonDisconnectlogger.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

                try {
                    
                    mTheRemoteBot.disconnectLogListener();
                    mLoggingControlButtonDisconnectlogger.setEnabled(false);
                    mLoggingControlButtonDisconnectlogger.setVisible(false);
                    mLoggingControlButtonConnectlogger.setEnabled(true);
                    mLoggingControlButtonConnectlogger.setVisible(true);

                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        mLoggingControlButtonDisconnectlogger.setBounds(0, 0, 150, 23);
        vLoggingPanelControlRight.add(mLoggingControlButtonDisconnectlogger);
        
        JPanel vLoggingPanelControlLeft = new JPanel();
        vTabbedPanePanelLoggingPanelControl.add(vLoggingPanelControlLeft, BorderLayout.CENTER);
        vLoggingPanelControlLeft.setLayout(null);
        
        JLabel vTabbedPanePanelLoggingPanelControlLabelLoglevel = new JLabel("Loglevel:");
        vTabbedPanePanelLoggingPanelControlLabelLoglevel.setHorizontalAlignment(SwingConstants.RIGHT);
        vTabbedPanePanelLoggingPanelControlLabelLoglevel.setBounds(3, 5, 50, 17);
        vLoggingPanelControlLeft.add(vTabbedPanePanelLoggingPanelControlLabelLoglevel);
        
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel = new JComboBox();
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                try {
                    mTheRemoteBot.getTheBot().setLogLevel( (Level) mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.getSelectedItem() );
                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.setModel(new DefaultComboBoxModel(Level.values()));
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.setBounds(55, 0, 100, 23);
        vLoggingPanelControlLeft.add(mTabbedPanePanelLoggingPanelControlComboBoxLoglevel);
        
        scrollPane_Logging = new JScrollPane();
        vPanelLogging.add(scrollPane_Logging, BorderLayout.CENTER);
        
        mLoggingTextarea = new JTextArea();
        mLoggingTextarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if( e.getButton() == MouseEvent.BUTTON3){
                    
                    popupMenu.show( e.getComponent(), e.getX(), e.getY() );
                    
                }
                
            }
        });
        mLoggingTextarea.setEditable(false);
        scrollPane_Logging.setViewportView(mLoggingTextarea);
        
        popupMenu = new JPopupMenu();
        popupMenu.setBounds(0, 0, 119, 25);
        
        mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mLoggingTextarea.copy();
                
            }
        });
        popupMenu.add(mntmCopy);
        
                JSeparator vMenueItemSeparator = new JSeparator();
                popupMenu.add(vMenueItemSeparator);
                
                mntmSetMaxLines = new JMenuItem("Set Max Lines");
                mntmSetMaxLines.addActionListener(new ActionListener() {
                    @Override
					public void actionPerformed(ActionEvent e) {
                        
                        final JDialog vMaxLines = new JDialog( Botcontrol.getInstance().getMainFrame() , "", Dialog.ModalityType.DOCUMENT_MODAL);
                        JPanel vContentPanel = new JPanel();
                        final JTextField textField;
                        
                        vMaxLines.setResizable(false);
                        vMaxLines.setTitle("Set Maximum Lines");
                        vMaxLines.setBounds( 100, 100, 174, 120 );
                        vMaxLines.getContentPane().setLayout( new BorderLayout() );
                        vContentPanel.setLayout( new FlowLayout() );
                        vContentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
                        vMaxLines.getContentPane().add( vContentPanel, BorderLayout.CENTER );
                        {
                            JLabel lblNewLabel = new JLabel("Max Lines");
                            vContentPanel.add(lblNewLabel);
                        }
                        {
                            textField = new JTextField();
                            vContentPanel.add(textField);
                            textField.setColumns(10);
                            textField.setText( Integer.toString( mMaxLinesInLog ) );
                        }
                        {
                            JPanel buttonPane = new JPanel();
                            buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
                            vMaxLines.getContentPane().add( buttonPane, BorderLayout.SOUTH );
                            {
                                JButton okButton = new JButton( "OK" );
                                okButton.addActionListener(new ActionListener() {
                                    @Override
									public void actionPerformed(ActionEvent e) {

                                        mMaxLinesInLog = Integer.parseInt( textField.getText() );
                                        vMaxLines.dispatchEvent(new WindowEvent( 
                                                vMaxLines, WindowEvent.WINDOW_CLOSING 
                                            ));
                                        
                                    }
                                });
                                okButton.setActionCommand( "OK" );
                                buttonPane.add( okButton );
                                getRootPane().setDefaultButton( okButton );
                            }
                            {
                                JButton cancelButton = new JButton( "Cancel" );
                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
									public void actionPerformed(ActionEvent e) {
                                        
                                        vMaxLines.dispatchEvent(new WindowEvent( 
                                                vMaxLines, WindowEvent.WINDOW_CLOSING 
                                            ));
                                        
                                    }
                                });
                                cancelButton.setActionCommand( "Cancel" );
                                buttonPane.add( cancelButton );
                            }
                        }
                        
                        vMaxLines.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
                        vMaxLines.setVisible( true );
                        
                    }
                });
                popupMenu.add(mntmSetMaxLines);
        
        mPanelHeadPanelBackLabelButtonExpandContract.addActionListener(new ActionListener() {
            
//			private int i = 0;
            
            @Override
			public void actionPerformed(ActionEvent e) {
                
                if( mExpanded == true ){

                    mPanelHeadPanelBackLabelButtonExpandContract.setIcon(new ImageIcon(BotFrame.class.getResource("/res/expand.gif")));
                    mTabbedPane.setPreferredSize( new Dimension( 200, 0 ) );
                    mTabbedPane.setMinimumSize(new Dimension( 0, 0 ));
                    mTabbedPane.setEnabled( false );
                    mTabbedPane.setVisible( false );
                    setMinimumSize( new Dimension( 200, 45 ));
                    setPreferredSize( new Dimension( 200, 45 ) );
                    mExpanded = false;
                    
                } else {

                    mPanelHeadPanelBackLabelButtonExpandContract.setIcon(new ImageIcon(BotFrame.class.getResource("/res/contract.gif")));
                    mTabbedPane.setPreferredSize( new Dimension( 200, 300 ) );
                    mTabbedPane.setMinimumSize(new Dimension( 0, 300 ));
                    mTabbedPane.setEnabled( true );
                    mTabbedPane.setVisible( true );
                    setMinimumSize(new Dimension( 200, 240 ));
                    setPreferredSize( new Dimension( 200, 240 ) );
                    mExpanded = true;
                    
                    try {
                        
                        updateData();
                        updateStatus( null );
                        
                    } catch ( RemoteException e1 ) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    
                }
                
                try{
                	revalidate();
                	getParent().validate();
                }catch (Exception err){
//                	err.printStackTrace();
                }
                
            }
        });

    }
    
    protected void updateTheRemoteBot() {

        
        mBotInformation.setBotname( mPanelHeadPanelFrontLabelBotname.getText() );
        
        mBotInformation.setTeam( (Teams) mDataGeneralComboBoxTeam.getSelectedItem() );
        mBotInformation.setBotname( mDataGeneralTextfieldBotname.getText() );
        mBotInformation.setTeamname( mDataGeneralTextFieldBotteamname.getText() );
        try{
            mBotInformation.setRcId( Integer.decode( mDataGeneralTextfieldRcid.getText() ) );
        }catch( Exception vException ){
            
        }
        try{
            mBotInformation.setVtId( Integer.decode( mDataGeneralTextfieldVtid.getText() ) );
        }catch( Exception vException ){
            
        }
        try {
            
            if( !mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) ){
                
                try{
                    mBotInformation.setBotPort( Integer.decode( mConnectionTextfieldBotport.getText() ) );
                }catch( Exception vException ){
                    
                }
                try{
                    mBotInformation.setServerIP(  InetAddress.getByName( mConnectionTextfieldServeraddress.getText() ) );
                }catch( Exception vException ){
                    
                }
                try{
                    mBotInformation.setServerPort( Integer.decode( mConnectionTextfieldServerteamport.getText() ) );
                }catch( Exception vException ){
                    
                }
                
            }
            
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        mBotInformation.setAIArchive( mAIExecutionTextfieldAiarchive.getText() );
        mBotInformation.setAIArgs( mAIArgumentsTextareaAiarguments.getText() );
        mBotInformation.setAIClassname( mAIExecutionTextfieldAiclass.getText() );
        
        try {
            mTheRemoteBot.getTheBot().setBotInformation( mBotInformation );
            updateData();
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

    public void addToLog( String aString ){
        
        while( mLoggingTextarea.getLineCount() > mMaxLinesInLog ){
            
            try {
                mLoggingTextarea.replaceRange( null, mLoggingTextarea.getLineStartOffset( 0 ), mLoggingTextarea.getLineEndOffset( 0 ) );
            } catch ( BadLocationException e2 ) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            
        }
        
        mLoggingTextarea.append( aString );
        
    }
    
    public void close( boolean aCloseBot ){
        
        //TODO

        if( mTheRemoteBot != null ){
            
            mTheRemoteBot.close( aCloseBot );
            
        }
        mTheRemoteBot = null;
        
        
    }

    public void registerBot( RemoteBot aRemoteBot ) throws RemoteException {
       
        mTheRemoteBot = aRemoteBot;
        
        updateData();
        updateStatus( null );
        
        changeConnectionButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) );
        changeAIButtons( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ), mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning ) );
        
        if( mTheRemoteBot.getBotLoader() != null ){
        	btnStatusButtonStopBot.setEnabled(true);
        }
        
        //mTabbedPanePanelConnectionButtonConnect;
        //mTabbedPanePanelConnectionButtonDisconnect;
        //mTabbedPanePanelConnectionButtonReconnect;
        
    }

    private void updateData() throws RemoteException {
        
        mBotInformation = mTheRemoteBot.getTheBot().getBotInformation();
        
        mPanelHeadPanelFrontLabelBotname.setText( mBotInformation.getBotname() );
        
        mDataGeneralComboBoxTeam.setSelectedItem( mBotInformation.getTeam() );
        mDataGeneralTextfieldBotname.setText( mBotInformation.getBotname() );
        mDataGeneralTextFieldBotteamname.setText( mBotInformation.getTeamname() );
        mDataGeneralTextfieldRcid.setText( Integer.toString( mBotInformation.getRcId() ) );
        mDataGeneralTextfieldVtid.setText( Integer.toString( mBotInformation.getVtId() ) );
        
        mConnectionTextfieldBotaddress.setText( mBotInformation.getBotIP().getHostAddress() );
        mConnectionTextfieldBotport.setText( Integer.toString( mBotInformation.getBotPort() ) );
        mConnectionTextfieldServeraddress.setText( mBotInformation.getServerIP().getHostAddress() );
        mConnectionTextfieldServerteamport.setText( Integer.toString( mBotInformation.getServerPort() ) );
        
        mAIExecutionTextfieldAiarchive.setText( mBotInformation.getAIArchive() );
        mAIExecutionTextfieldAiclass.setText( mBotInformation.getAIClassname() );
        mAIArgumentsTextareaAiarguments.setText( mBotInformation.getAIArgs() );
        
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.setSelectedItem( mTheRemoteBot.getTheBot().getLogLevel() );
        
    }
    
    public void updateStatus( BotStatusType aStatus ) throws RemoteException{
        
        if( aStatus == null || aStatus == BotStatusType.AILoaded ) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ) ){
                
                mStatusAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelAILoaded.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mStatusAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelAILoaded.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.AIRunning) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning) ){
                
                mStatusAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelAIRunning.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mStatusAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelAIRunning.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkConnection) {
                 
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) ){
                
                mStatusNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkConnected.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mStatusNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkConnected.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkIncomingTraffic) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkIncomingTraffic ) ){
                
                mStatusNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mStatusNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkOutgoingTraffic ) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkOutgoingTraffic ) ){
                
                mStatusNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mStatusNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
        
        }
    }

    public void changeConnectionButtons( boolean aConnected ) throws RemoteException {
        
        mConnectionButtonConnect.setEnabled( !aConnected );
        mConnectionButtonConnect.setVisible( !aConnected );
        mConnectionButtonReconnect.setEnabled( !aConnected );
        mConnectionButtonReconnect.setVisible( !aConnected );
        mConnectionButtonDisconnect.setEnabled( aConnected );
        mConnectionButtonDisconnect.setVisible( aConnected);
        
        updateStatus( null );
    }
    
    public void changeAIButtons( boolean aInitalised, boolean aRunning ) throws RemoteException{

        mAIArgumentsButtonExecute.setEnabled( aInitalised & aRunning );

        mAIExecutionButtonAipause.setEnabled( aInitalised & aRunning );
        mAIExecutionButtonAipause.setVisible( aInitalised & aRunning );
        mAIExecutionButtonAiunpause.setEnabled( aInitalised & !aRunning );
        mAIExecutionButtonAiunpause.setVisible( aInitalised & !aRunning );

        mAIExecutionButtonAidispose.setEnabled( aInitalised );
        mAIExecutionButtonAidispose.setVisible( aInitalised );
        mAIExecutionButtonAiinitialise.setEnabled( !aInitalised );
        mAIExecutionButtonAiinitialise.setVisible( !aInitalised );
        
        updateStatus( null );
        
    }
}
