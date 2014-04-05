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
    private JButton mPanelHeadPanelBackLabelButtonExpandContract;
    private JPanel mPanelHead;
    private JTabbedPane mTabbedPane;
    private JPanel vTabbedPanePanelStatus;
    private JPanel vTabbedPanePanelData;
    private JPanel vTabbedPanePanelConnection;
    private JPanel vTabbedPanePanelLoggingPanelControlRight;
    private JScrollPane scrollPane_Logging;
    private JTextArea mTabbedPanePanelLoggingTextarea;
    private JPopupMenu popupMenu;
    private JMenuItem mntmCopy;
    private JMenuItem mntmSetMaxLines;
    private JButton mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger;
    private JButton mTabbedPanePanelLoggingPanelControlButtonConnectlogger;
    private JComboBox mTabbedPanePanelLoggingPanelControlComboBoxLoglevel;
    private JPanel vTabbedPanePanelAIPanelArguments;
    private JTextField mTabbedPanePanelAIPanelExecutionTextfieldAiarchive;
    private JTextField mTabbedPanePanelAIPanelExecutionTextfieldAiclass;
    private JTextField mTabbedPanePanelConnectionTextfieldServeraddress;
    private JTextField mTabbedPanePanelConnectionTextfieldServerteamport;
    private JTextField mTabbedPanePanelConnectionTextfieldBotaddress;
    private JTextField mTabbedPanePanelConnectionTextfieldBotport;
    private JTextField mTabbedPanePanelDataPanelGeneralTextfieldBotname;
    private JTextField mTabbedPanePanelDataPanelGeneralTextFieldBotteamname;
    private JTextField mTabbedPanePanelDataPanelGeneralTextfieldRcid;
    private JTextField mTabbedPanePanelDataPanelGeneralTextfieldVtid;
    private JLabel mPanelHeadPanelBackLabelNetworkConnected;
    private JLabel mPanelHeadPanelBackLabelNetworkTrafficOutgoing;
    private JLabel mPanelHeadPanelBackLabelNetworkTrafficIncoming;
    private JLabel mPanelHeadPanelBackLabelAIRunning;
    private JLabel mPanelHeadPanelBackLabelAILoaded;
    private JLabel mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus;
    private JLabel mTabbedPanePanelStatusPanelAiLabelRunningstatus;
    private JLabel mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus;
    private JLabel mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus;
    private JLabel mTabbedPanePanelStatusPanelAiLabelLoadedstatus;
    private JTable mTabbedPanePanelDataPanelServerconstantsTable;
    private JComboBox mTabbedPanePanelDataPanelGeneralComboBoxTeam;
    private JButton mTabbedPanePanelConnectionButtonConnect;
    private JButton mTabbedPanePanelConnectionButtonReconnect;
    private JButton mTabbedPanePanelConnectionButtonDisconnect;
    private JButton mTabbedPanePanelAIPanelArgumentsButtonExecute;
    private JTextArea mTabbedPanePanelAIPanelArgumentsTextareaAiarguments;
    private JButton mTabbedPanePanelAIPanelExecutionButtonAiinitialise;
    private JButton mTabbedPanePanelAIPanelExecutionButtonAiunpause;
    private JButton mTabbedPanePanelAIPanelExecutionButtonAidispose;
    private JButton mTabbedPanePanelAIPanelExecutionButtonAipause;

    private RemoteBot mTheRemoteBot;
    private JButton mTabbedPanePanelDataPanelGeneralButtonChangeData;

    private BotInformation mBotInformation;

    /**
     * Create the panel.
     */
    public BotFrame() {
        
        setBorder(new LineBorder(UIManager.getColor("TabbedPane.selected"), 3));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(363, 221));
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

                int vSelection = JOptionPane.showConfirmDialog(
                        Botcontrol.getInstance().getMainFrame(),
                        "Would you like to close the Bot?",
                        "Closing the Bot",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                
                System.out.println(vSelection);
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
        
        vTabbedPanePanelStatus = new JPanel();
        mTabbedPane.addTab("Status", null, vTabbedPanePanelStatus, null);
        vTabbedPanePanelStatus.setLayout(null);
        
        JPanel vTabbedPanePanelStatusPanelNetworkstatus = new JPanel();
        vTabbedPanePanelStatusPanelNetworkstatus.setBorder(new TitledBorder(null, "Network", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        vTabbedPanePanelStatusPanelNetworkstatus.setBounds(10, 11, 150, 100);
        vTabbedPanePanelStatus.add(vTabbedPanePanelStatusPanelNetworkstatus);
        vTabbedPanePanelStatusPanelNetworkstatus.setLayout(null);
        
        JLabel vTabbedPanePanelStatusPanelNetworkstatusLabelConnected = new JLabel("Connected....................");
        vTabbedPanePanelStatusPanelNetworkstatusLabelConnected.setBounds(10, 15, 113, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(vTabbedPanePanelStatusPanelNetworkstatusLabelConnected);
        
        mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus = new JLabel("");
        mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus.setBounds(120, 15, 17, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus);
        
        JLabel vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincoming = new JLabel("Traffic incoming.............");
        vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincoming.setBounds(10, 43, 113, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincoming);
        
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus = new JLabel("");
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus.setBounds(120, 43, 17, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus);
        
        JLabel vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoing = new JLabel("Traffic outgoing............");
        vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoing.setBounds(10, 71, 113, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(vTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoing);
        
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus = new JLabel("");
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus.setBounds(120, 71, 17, 17);
        vTabbedPanePanelStatusPanelNetworkstatus.add(mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus);
        
        JPanel vTabbedPanePanelStatusPanelAi = new JPanel();
        vTabbedPanePanelStatusPanelAi.setLayout(null);
        vTabbedPanePanelStatusPanelAi.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "AI", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        vTabbedPanePanelStatusPanelAi.setBounds(170, 11, 150, 73);
        vTabbedPanePanelStatus.add(vTabbedPanePanelStatusPanelAi);
        
        JLabel vTabbedPanePanelStatusPanelAiLabelRunning = new JLabel("Running.........................");
        vTabbedPanePanelStatusPanelAiLabelRunning.setBounds(10, 15, 113, 17);
        vTabbedPanePanelStatusPanelAi.add(vTabbedPanePanelStatusPanelAiLabelRunning);
        
        mTabbedPanePanelStatusPanelAiLabelRunningstatus = new JLabel("");
        mTabbedPanePanelStatusPanelAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mTabbedPanePanelStatusPanelAiLabelRunningstatus.setBounds(120, 15, 17, 17);
        vTabbedPanePanelStatusPanelAi.add(mTabbedPanePanelStatusPanelAiLabelRunningstatus);
        
        JLabel vTabbedPanePanelStatusPanelAiLabelLoaded = new JLabel("Loaded..........................");
        vTabbedPanePanelStatusPanelAiLabelLoaded.setBounds(10, 43, 113, 17);
        vTabbedPanePanelStatusPanelAi.add(vTabbedPanePanelStatusPanelAiLabelLoaded);
        
        mTabbedPanePanelStatusPanelAiLabelLoadedstatus = new JLabel("");
        mTabbedPanePanelStatusPanelAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        mTabbedPanePanelStatusPanelAiLabelLoadedstatus.setBounds(120, 43, 17, 17);
        vTabbedPanePanelStatusPanelAi.add(mTabbedPanePanelStatusPanelAiLabelLoadedstatus);
        
        JButton vTabbedPanePanelStatusButtonManuallystatuscheck = new JButton("Manually check status");
        vTabbedPanePanelStatusButtonManuallystatuscheck.addActionListener(new ActionListener() {
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
        vTabbedPanePanelStatusButtonManuallystatuscheck.setBounds(10, 122, 310, 23);
        vTabbedPanePanelStatus.add(vTabbedPanePanelStatusButtonManuallystatuscheck);
        
        vTabbedPanePanelData = new JPanel();
        mTabbedPane.addTab("Data", null, vTabbedPanePanelData, null);
        vTabbedPanePanelData.setLayout(new BorderLayout(0, 0));
        
        JPanel vTabbedPanePanelDataPanelGeneral = new JPanel();
        vTabbedPanePanelDataPanelGeneral.setPreferredSize(new Dimension(200, 10));
        vTabbedPanePanelData.add(vTabbedPanePanelDataPanelGeneral, BorderLayout.WEST);
        vTabbedPanePanelDataPanelGeneral.setLayout(null);
        
        JLabel vTabbedPanePanelDataPanelGeneralLabelBotname = new JLabel("Botname");
        vTabbedPanePanelDataPanelGeneralLabelBotname.setBounds(10, 11, 180, 14);
        vTabbedPanePanelDataPanelGeneral.add(vTabbedPanePanelDataPanelGeneralLabelBotname);
        
        mTabbedPanePanelDataPanelGeneralTextfieldBotname = new JTextField();
        mTabbedPanePanelDataPanelGeneralTextfieldBotname.setBounds(10, 26, 180, 20);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralTextfieldBotname);
        mTabbedPanePanelDataPanelGeneralTextfieldBotname.setColumns(10);
        
        JLabel vTabbedPanePanelDataPanelGeneralLabelBotteamname = new JLabel("Botteamname");
        vTabbedPanePanelDataPanelGeneralLabelBotteamname.setBounds(10, 51, 180, 14);
        vTabbedPanePanelDataPanelGeneral.add(vTabbedPanePanelDataPanelGeneralLabelBotteamname);
        
        mTabbedPanePanelDataPanelGeneralTextFieldBotteamname = new JTextField();
        mTabbedPanePanelDataPanelGeneralTextFieldBotteamname.setBounds(10, 65, 180, 20);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralTextFieldBotteamname);
        mTabbedPanePanelDataPanelGeneralTextFieldBotteamname.setColumns(10);
        
        JLabel vTabbedPanePanelDataPanelGeneralLabelRcid = new JLabel("RC-ID");
        vTabbedPanePanelDataPanelGeneralLabelRcid.setBounds(10, 90, 35, 14);
        vTabbedPanePanelDataPanelGeneral.add(vTabbedPanePanelDataPanelGeneralLabelRcid);
        
        JLabel vTabbedPanePanelDataPanelGeneralLabelVtid = new JLabel("VT-ID");
        vTabbedPanePanelDataPanelGeneralLabelVtid.setBounds(55, 90, 35, 14);
        vTabbedPanePanelDataPanelGeneral.add(vTabbedPanePanelDataPanelGeneralLabelVtid);
        
        JLabel vTabbedPanePanelDataPanelGeneralLabelTeam = new JLabel("Team");
        vTabbedPanePanelDataPanelGeneralLabelTeam.setBounds(100, 90, 90, 14);
        vTabbedPanePanelDataPanelGeneral.add(vTabbedPanePanelDataPanelGeneralLabelTeam);
        
        mTabbedPanePanelDataPanelGeneralTextfieldRcid = new JTextField();
        mTabbedPanePanelDataPanelGeneralTextfieldRcid.setText("22");
        mTabbedPanePanelDataPanelGeneralTextfieldRcid.setBounds(10, 104, 35, 20);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralTextfieldRcid);
        mTabbedPanePanelDataPanelGeneralTextfieldRcid.setColumns(10);
        
        mTabbedPanePanelDataPanelGeneralTextfieldVtid = new JTextField();
        mTabbedPanePanelDataPanelGeneralTextfieldVtid.setBounds(55, 104, 35, 20);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralTextfieldVtid);
        mTabbedPanePanelDataPanelGeneralTextfieldVtid.setColumns(10);
        
        mTabbedPanePanelDataPanelGeneralComboBoxTeam = new JComboBox();
        mTabbedPanePanelDataPanelGeneralComboBoxTeam.setModel(new DefaultComboBoxModel(Teams.values()));
        mTabbedPanePanelDataPanelGeneralComboBoxTeam.setBounds(100, 103, 90, 22);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralComboBoxTeam);
        
        mTabbedPanePanelDataPanelGeneralButtonChangeData = new JButton("Change Data");
        mTabbedPanePanelDataPanelGeneralButtonChangeData.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                updateTheRemoteBot();
                
            }
        });
        mTabbedPanePanelDataPanelGeneralButtonChangeData.setBounds(10, 135, 180, 23);
        vTabbedPanePanelDataPanelGeneral.add(mTabbedPanePanelDataPanelGeneralButtonChangeData);
        
        JScrollPane scrollPane_Serverconstants = new JScrollPane();
        vTabbedPanePanelData.add(scrollPane_Serverconstants, BorderLayout.CENTER);
        
        JPanel vTabbedPanePanelDataPanelServerconstants = new JPanel();
        scrollPane_Serverconstants.setViewportView(vTabbedPanePanelDataPanelServerconstants);
        vTabbedPanePanelDataPanelServerconstants.setLayout(new BorderLayout(0, 0));
        
        JLabel vTabbedPanePanelDataPanelServerconstantsLabel = new JLabel("Serverconstants");
        vTabbedPanePanelDataPanelServerconstants.add(vTabbedPanePanelDataPanelServerconstantsLabel, BorderLayout.NORTH);
        
        mTabbedPanePanelDataPanelServerconstantsTable = new JTable();
        mTabbedPanePanelDataPanelServerconstantsTable.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "New column", "New column"
            }
        ));
        vTabbedPanePanelDataPanelServerconstants.add(mTabbedPanePanelDataPanelServerconstantsTable, BorderLayout.CENTER);
        
        vTabbedPanePanelConnection = new JPanel();
        mTabbedPane.addTab("Connection", null, vTabbedPanePanelConnection, null);
        vTabbedPanePanelConnection.setLayout(new BorderLayout(0, 0));
        
        JPanel vTabbedPanePanelConnectionPanel2 = new JPanel();
        vTabbedPanePanelConnectionPanel2.setPreferredSize(new Dimension(300, 10));
        vTabbedPanePanelConnection.add(vTabbedPanePanelConnectionPanel2, BorderLayout.WEST);
        vTabbedPanePanelConnectionPanel2.setLayout(null);
        
        JLabel vTabbedPanePanelConnectionLabelServeraddress = new JLabel("Server IP-Address");
        vTabbedPanePanelConnectionLabelServeraddress.setBounds(10, 11, 150, 14);
        vTabbedPanePanelConnectionPanel2.add(vTabbedPanePanelConnectionLabelServeraddress);
        
        mTabbedPanePanelConnectionTextfieldServeraddress = new JTextField();
        mTabbedPanePanelConnectionTextfieldServeraddress.setBounds(10, 27, 150, 20);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionTextfieldServeraddress);
        mTabbedPanePanelConnectionTextfieldServeraddress.setColumns(10);
        
        mTabbedPanePanelConnectionTextfieldServerteamport = new JTextField();
        mTabbedPanePanelConnectionTextfieldServerteamport.setBounds(170, 27, 86, 20);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionTextfieldServerteamport);
        mTabbedPanePanelConnectionTextfieldServerteamport.setColumns(10);
        
        JLabel vTabbedPanePanelConnectionLabelServerteamport = new JLabel("Teamport");
        vTabbedPanePanelConnectionLabelServerteamport.setBounds(170, 11, 86, 14);
        vTabbedPanePanelConnectionPanel2.add(vTabbedPanePanelConnectionLabelServerteamport);
        
        JLabel vTabbedPanePanelConnectionLabelBotaddress = new JLabel("Bot IPAddress");
        vTabbedPanePanelConnectionLabelBotaddress.setBounds(10, 58, 150, 14);
        vTabbedPanePanelConnectionPanel2.add(vTabbedPanePanelConnectionLabelBotaddress);
        
        mTabbedPanePanelConnectionTextfieldBotaddress = new JTextField();
        mTabbedPanePanelConnectionTextfieldBotaddress.setEditable(false);
        mTabbedPanePanelConnectionTextfieldBotaddress.setBounds(10, 75, 150, 20);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionTextfieldBotaddress);
        mTabbedPanePanelConnectionTextfieldBotaddress.setColumns(10);
        
        JLabel vTabbedPanePanelConnectionLabelBotport = new JLabel("Botport");
        vTabbedPanePanelConnectionLabelBotport.setBounds(170, 58, 86, 14);
        vTabbedPanePanelConnectionPanel2.add(vTabbedPanePanelConnectionLabelBotport);
        
        mTabbedPanePanelConnectionTextfieldBotport = new JTextField();
        mTabbedPanePanelConnectionTextfieldBotport.setBounds(170, 75, 86, 20);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionTextfieldBotport);
        mTabbedPanePanelConnectionTextfieldBotport.setColumns(10);
        
        mTabbedPanePanelConnectionButtonConnect = new JButton("Connect");
        mTabbedPanePanelConnectionButtonConnect.addActionListener(new ActionListener() {
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
        mTabbedPanePanelConnectionButtonConnect.setBounds(10, 106, 118, 23);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionButtonConnect);
        
        mTabbedPanePanelConnectionButtonReconnect = new JButton("Reconnect");
        mTabbedPanePanelConnectionButtonReconnect.addActionListener(new ActionListener() {
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
        mTabbedPanePanelConnectionButtonReconnect.setBounds(138, 106, 118, 23);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionButtonReconnect);
        
        mTabbedPanePanelConnectionButtonDisconnect = new JButton("Disconnect");
        mTabbedPanePanelConnectionButtonDisconnect.addActionListener(new ActionListener() {
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
        mTabbedPanePanelConnectionButtonDisconnect.setVisible(false);
        mTabbedPanePanelConnectionButtonDisconnect.setEnabled(false);
        mTabbedPanePanelConnectionButtonDisconnect.setBounds(10, 106, 246, 23);
        vTabbedPanePanelConnectionPanel2.add(mTabbedPanePanelConnectionButtonDisconnect);
        
        JPanel vTabbedPanePanelAI = new JPanel();
        mTabbedPane.addTab("AI", null, vTabbedPanePanelAI, null);
        vTabbedPanePanelAI.setLayout(new BorderLayout(0, 0));
        
        JPanel vTabbedPanePanelAIPanelExecution = new JPanel();
        vTabbedPanePanelAIPanelExecution.setPreferredSize(new Dimension(200, 10));
        vTabbedPanePanelAIPanelExecution.setSize(new Dimension(200, 0));
        vTabbedPanePanelAI.add(vTabbedPanePanelAIPanelExecution, BorderLayout.WEST);
        vTabbedPanePanelAIPanelExecution.setLayout(null);
        
        JLabel vTabbedPanePanelAIPanelExecutionLabelAiarchive = new JLabel("Archive");
        vTabbedPanePanelAIPanelExecutionLabelAiarchive.setBounds(10, 11, 180, 14);
        vTabbedPanePanelAIPanelExecution.add(vTabbedPanePanelAIPanelExecutionLabelAiarchive);
        
        mTabbedPanePanelAIPanelExecutionTextfieldAiarchive = new JTextField();
        mTabbedPanePanelAIPanelExecutionTextfieldAiarchive.setBounds(10, 29, 180, 20);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionTextfieldAiarchive);
        mTabbedPanePanelAIPanelExecutionTextfieldAiarchive.setColumns(50);
        
        JLabel vTabbedPanePanelAIPanelExecutionLabelAiclass = new JLabel("Classname");
        vTabbedPanePanelAIPanelExecutionLabelAiclass.setBounds(10, 59, 180, 14);
        vTabbedPanePanelAIPanelExecution.add(vTabbedPanePanelAIPanelExecutionLabelAiclass);
        
        mTabbedPanePanelAIPanelExecutionTextfieldAiclass = new JTextField();
        mTabbedPanePanelAIPanelExecutionTextfieldAiclass.setText("ais/example_ai.jar");
        mTabbedPanePanelAIPanelExecutionTextfieldAiclass.setBounds(10, 77, 180, 20);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionTextfieldAiclass);
        mTabbedPanePanelAIPanelExecutionTextfieldAiclass.setColumns(10);
        
        mTabbedPanePanelAIPanelExecutionButtonAiinitialise = new JButton("Initialise");
        mTabbedPanePanelAIPanelExecutionButtonAiinitialise.addActionListener(new ActionListener() {
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
        mTabbedPanePanelAIPanelExecutionButtonAiinitialise.setBounds(10, 108, 180, 23);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionButtonAiinitialise);
        
        mTabbedPanePanelAIPanelExecutionButtonAiunpause = new JButton("Resume");
        mTabbedPanePanelAIPanelExecutionButtonAiunpause.addActionListener(new ActionListener() {
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
        mTabbedPanePanelAIPanelExecutionButtonAiunpause.setEnabled(false);
        mTabbedPanePanelAIPanelExecutionButtonAiunpause.setBounds(10, 142, 180, 23);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionButtonAiunpause);
        
        mTabbedPanePanelAIPanelExecutionButtonAidispose = new JButton("Dispose");
        mTabbedPanePanelAIPanelExecutionButtonAidispose.addActionListener(new ActionListener() {
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
        mTabbedPanePanelAIPanelExecutionButtonAidispose.setEnabled(false);
        mTabbedPanePanelAIPanelExecutionButtonAidispose.setVisible(false);
        mTabbedPanePanelAIPanelExecutionButtonAidispose.setBounds(10, 108, 180, 23);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionButtonAidispose);
        
        mTabbedPanePanelAIPanelExecutionButtonAipause = new JButton("Suspend");
        mTabbedPanePanelAIPanelExecutionButtonAipause.addActionListener(new ActionListener() {
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
        mTabbedPanePanelAIPanelExecutionButtonAipause.setVisible(false);
        mTabbedPanePanelAIPanelExecutionButtonAipause.setEnabled(false);
        mTabbedPanePanelAIPanelExecutionButtonAipause.setBounds(10, 142, 180, 23);
        vTabbedPanePanelAIPanelExecution.add(mTabbedPanePanelAIPanelExecutionButtonAipause);
        
        vTabbedPanePanelAIPanelArguments = new JPanel();
        vTabbedPanePanelAI.add(vTabbedPanePanelAIPanelArguments, BorderLayout.CENTER);
        vTabbedPanePanelAIPanelArguments.setLayout(new BorderLayout(0, 0));
        
        mTabbedPanePanelAIPanelArgumentsButtonExecute = new JButton("Execute Arguments");
        mTabbedPanePanelAIPanelArgumentsButtonExecute.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mExecutor.execute( new Runnable() { //TODO: nachdenken
                    @Override
					public void run() {

                        try {
                            
                            mTheRemoteBot.getTheBot().executeCommandOnAI( mTabbedPanePanelAIPanelArgumentsTextareaAiarguments.getText() );

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
        mTabbedPanePanelAIPanelArgumentsButtonExecute.setEnabled(false);
        vTabbedPanePanelAIPanelArguments.add(mTabbedPanePanelAIPanelArgumentsButtonExecute, BorderLayout.SOUTH);
        
        JScrollPane scrollPane_aiarguments = new JScrollPane();
        vTabbedPanePanelAIPanelArguments.add(scrollPane_aiarguments, BorderLayout.CENTER);
        
        mTabbedPanePanelAIPanelArgumentsTextareaAiarguments = new JTextArea();
        mTabbedPanePanelAIPanelArgumentsTextareaAiarguments.setLineWrap(true);
        scrollPane_aiarguments.setViewportView(mTabbedPanePanelAIPanelArgumentsTextareaAiarguments);
        
        JPanel vTabbedPanePanelLogging = new JPanel();
        mTabbedPane.addTab("Logging", null, vTabbedPanePanelLogging, null);
        vTabbedPanePanelLogging.setLayout(new BorderLayout(0, 0));
        
        JPanel vTabbedPanePanelLoggingPanelControl = new JPanel();
        vTabbedPanePanelLogging.add(vTabbedPanePanelLoggingPanelControl, BorderLayout.NORTH);
        vTabbedPanePanelLoggingPanelControl.setLayout(new BorderLayout(0, 0));
        
        vTabbedPanePanelLoggingPanelControlRight = new JPanel();
        vTabbedPanePanelLoggingPanelControlRight.setMinimumSize(new Dimension(100, 25));
        vTabbedPanePanelLoggingPanelControlRight.setPreferredSize(new Dimension(150, 23));
        vTabbedPanePanelLoggingPanelControlRight.setSize(new Dimension(100, 23));
        vTabbedPanePanelLoggingPanelControl.add(vTabbedPanePanelLoggingPanelControlRight, BorderLayout.EAST);
        vTabbedPanePanelLoggingPanelControlRight.setLayout(null);
        
        mTabbedPanePanelLoggingPanelControlButtonConnectlogger = new JButton("Connect Logger");
        mTabbedPanePanelLoggingPanelControlButtonConnectlogger.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    mTheRemoteBot.connectLogListener();
                    mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setEnabled(false);
                    mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setVisible(false);
                    mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.setEnabled(true);
                    mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.setVisible(true);
                    
                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setEnabled(false);
        mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setVisible(false);
        mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setBounds(0, 0, 150, 23);
        vTabbedPanePanelLoggingPanelControlRight.add(mTabbedPanePanelLoggingPanelControlButtonConnectlogger);
        
        mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger = new JButton("Disconnect Logger");
        
        mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

                try {
                    
                    mTheRemoteBot.disconnectLogListener();
                    mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.setEnabled(false);
                    mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.setVisible(false);
                    mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setEnabled(true);
                    mTabbedPanePanelLoggingPanelControlButtonConnectlogger.setVisible(true);

                } catch ( RemoteException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger.setBounds(0, 0, 150, 23);
        vTabbedPanePanelLoggingPanelControlRight.add(mTabbedPanePanelLoggingPanelControlButtonDisconnectlogger);
        
        JPanel vTabbedPanePanelLoggingPanelControlLeft = new JPanel();
        vTabbedPanePanelLoggingPanelControl.add(vTabbedPanePanelLoggingPanelControlLeft, BorderLayout.CENTER);
        vTabbedPanePanelLoggingPanelControlLeft.setLayout(null);
        
        JLabel vTabbedPanePanelLoggingPanelControlLabelLoglevel = new JLabel("Loglevel:");
        vTabbedPanePanelLoggingPanelControlLabelLoglevel.setHorizontalAlignment(SwingConstants.RIGHT);
        vTabbedPanePanelLoggingPanelControlLabelLoglevel.setBounds(3, 5, 50, 17);
        vTabbedPanePanelLoggingPanelControlLeft.add(vTabbedPanePanelLoggingPanelControlLabelLoglevel);
        
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
        vTabbedPanePanelLoggingPanelControlLeft.add(mTabbedPanePanelLoggingPanelControlComboBoxLoglevel);
        
        scrollPane_Logging = new JScrollPane();
        vTabbedPanePanelLogging.add(scrollPane_Logging, BorderLayout.CENTER);
        
        mTabbedPanePanelLoggingTextarea = new JTextArea();
        mTabbedPanePanelLoggingTextarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if( e.getButton() == MouseEvent.BUTTON3){
                    
                    popupMenu.show( e.getComponent(), e.getX(), e.getY() );
                    
                }
                
            }
        });
        mTabbedPanePanelLoggingTextarea.setEditable(false);
        scrollPane_Logging.setViewportView(mTabbedPanePanelLoggingTextarea);
        
        popupMenu = new JPopupMenu();
        popupMenu.setBounds(0, 0, 119, 25);
        
        mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mTabbedPanePanelLoggingTextarea.copy();
                
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
            
            private int i = 0;
            
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
                
                revalidate();
                getParent().validate();
                
            }
        });

    }
    
    protected void updateTheRemoteBot() {

        
        mBotInformation.setBotname( mPanelHeadPanelFrontLabelBotname.getText() );
        
        mBotInformation.setTeam( (Teams) mTabbedPanePanelDataPanelGeneralComboBoxTeam.getSelectedItem() );
        mBotInformation.setBotname( mTabbedPanePanelDataPanelGeneralTextfieldBotname.getText() );
        mBotInformation.setTeamname( mTabbedPanePanelDataPanelGeneralTextFieldBotteamname.getText() );
        try{
            mBotInformation.setRcId( Integer.decode( mTabbedPanePanelDataPanelGeneralTextfieldRcid.getText() ) );
        }catch( Exception vException ){
            
        }
        try{
            mBotInformation.setVtId( Integer.decode( mTabbedPanePanelDataPanelGeneralTextfieldVtid.getText() ) );
        }catch( Exception vException ){
            
        }
        try {
            
            if( !mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) ){
                
                try{
                    mBotInformation.setBotPort( Integer.decode( mTabbedPanePanelConnectionTextfieldBotport.getText() ) );
                }catch( Exception vException ){
                    
                }
                try{
                    mBotInformation.setServerIP(  InetAddress.getByName( mTabbedPanePanelConnectionTextfieldServeraddress.getText() ) );
                }catch( Exception vException ){
                    
                }
                try{
                    mBotInformation.setServerPort( Integer.decode( mTabbedPanePanelConnectionTextfieldServerteamport.getText() ) );
                }catch( Exception vException ){
                    
                }
                
            }
            
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        mBotInformation.setAIArchive( mTabbedPanePanelAIPanelExecutionTextfieldAiarchive.getText() );
        mBotInformation.setAIArgs( mTabbedPanePanelAIPanelArgumentsTextareaAiarguments.getText() );
        mBotInformation.setAIClassname( mTabbedPanePanelAIPanelExecutionTextfieldAiclass.getText() );
        
        try {
            mTheRemoteBot.getTheBot().setBotInformation( mBotInformation );
            updateData();
        } catch ( RemoteException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

    public void addToLog( String aString ){
        
        while( mTabbedPanePanelLoggingTextarea.getLineCount() > mMaxLinesInLog ){
            
            try {
                mTabbedPanePanelLoggingTextarea.replaceRange( null, mTabbedPanePanelLoggingTextarea.getLineStartOffset( 0 ), mTabbedPanePanelLoggingTextarea.getLineEndOffset( 0 ) );
            } catch ( BadLocationException e2 ) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            
        }
        
        mTabbedPanePanelLoggingTextarea.append( aString );
        
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
        
        //mTabbedPanePanelConnectionButtonConnect;
        //mTabbedPanePanelConnectionButtonDisconnect;
        //mTabbedPanePanelConnectionButtonReconnect;
        
    }

    private void updateData() throws RemoteException {
        
        mBotInformation = mTheRemoteBot.getTheBot().getBotInformation();
        
        mPanelHeadPanelFrontLabelBotname.setText( mBotInformation.getBotname() );
        
        mTabbedPanePanelDataPanelGeneralComboBoxTeam.setSelectedItem( mBotInformation.getTeam() );
        mTabbedPanePanelDataPanelGeneralTextfieldBotname.setText( mBotInformation.getBotname() );
        mTabbedPanePanelDataPanelGeneralTextFieldBotteamname.setText( mBotInformation.getTeamname() );
        mTabbedPanePanelDataPanelGeneralTextfieldRcid.setText( Integer.toString( mBotInformation.getRcId() ) );
        mTabbedPanePanelDataPanelGeneralTextfieldVtid.setText( Integer.toString( mBotInformation.getVtId() ) );
        
        mTabbedPanePanelConnectionTextfieldBotaddress.setText( mBotInformation.getBotIP().getHostAddress() );
        mTabbedPanePanelConnectionTextfieldBotport.setText( Integer.toString( mBotInformation.getBotPort() ) );
        mTabbedPanePanelConnectionTextfieldServeraddress.setText( mBotInformation.getServerIP().getHostAddress() );
        mTabbedPanePanelConnectionTextfieldServerteamport.setText( Integer.toString( mBotInformation.getServerPort() ) );
        
        mTabbedPanePanelAIPanelExecutionTextfieldAiarchive.setText( mBotInformation.getAIArchive() );
        mTabbedPanePanelAIPanelExecutionTextfieldAiclass.setText( mBotInformation.getAIClassname() );
        mTabbedPanePanelAIPanelArgumentsTextareaAiarguments.setText( mBotInformation.getAIArgs() );
        
        mTabbedPanePanelLoggingPanelControlComboBoxLoglevel.setSelectedItem( mTheRemoteBot.getTheBot().getLogLevel() );
        
    }
    
    public void updateStatus( BotStatusType aStatus ) throws RemoteException{
        
        if( aStatus == null || aStatus == BotStatusType.AILoaded ) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AILoaded ) ){
                
                mTabbedPanePanelStatusPanelAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelAILoaded.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mTabbedPanePanelStatusPanelAiLabelLoadedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelAILoaded.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.AIRunning) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.AIRunning) ){
                
                mTabbedPanePanelStatusPanelAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelAIRunning.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mTabbedPanePanelStatusPanelAiLabelRunningstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelAIRunning.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkConnection) {
                 
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkConnection ) ){
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkConnected.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelConnectedstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkConnected.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkIncomingTraffic) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkIncomingTraffic ) ){
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficincomingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficIncoming.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
            
        }
    
        if( aStatus == null || aStatus == BotStatusType.NetworkOutgoingTraffic ) {
         
            if( mTheRemoteBot.getTheBot().getBooleanStatus( BotStatusType.NetworkOutgoingTraffic ) ){
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIcon(new ImageIcon(BotFrame.class.getResource("/res/green_signal.gif")));
                
            } else {
                
                mTabbedPanePanelStatusPanelNetworkstatusLabelTrafficoutgoingstatus.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                mPanelHeadPanelBackLabelNetworkTrafficOutgoing.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
                
            }
        
        }
    }

    public void changeConnectionButtons( boolean aConnected ) throws RemoteException {
        
        mTabbedPanePanelConnectionButtonConnect.setEnabled( !aConnected );
        mTabbedPanePanelConnectionButtonConnect.setVisible( !aConnected );
        mTabbedPanePanelConnectionButtonReconnect.setEnabled( !aConnected );
        mTabbedPanePanelConnectionButtonReconnect.setVisible( !aConnected );
        mTabbedPanePanelConnectionButtonDisconnect.setEnabled( aConnected );
        mTabbedPanePanelConnectionButtonDisconnect.setVisible( aConnected);
        
        updateStatus( null );
    }
    
    public void changeAIButtons( boolean aInitalised, boolean aRunning ) throws RemoteException{

        mTabbedPanePanelAIPanelArgumentsButtonExecute.setEnabled( aInitalised & aRunning );

        mTabbedPanePanelAIPanelExecutionButtonAipause.setEnabled( aInitalised & aRunning );
        mTabbedPanePanelAIPanelExecutionButtonAipause.setVisible( aInitalised & aRunning );
        mTabbedPanePanelAIPanelExecutionButtonAiunpause.setEnabled( aInitalised & !aRunning );
        mTabbedPanePanelAIPanelExecutionButtonAiunpause.setVisible( aInitalised & !aRunning );

        mTabbedPanePanelAIPanelExecutionButtonAidispose.setEnabled( aInitalised );
        mTabbedPanePanelAIPanelExecutionButtonAidispose.setVisible( aInitalised );
        mTabbedPanePanelAIPanelExecutionButtonAiinitialise.setEnabled( !aInitalised );
        mTabbedPanePanelAIPanelExecutionButtonAiinitialise.setVisible( !aInitalised );
        
        updateStatus( null );
        
    }
}
