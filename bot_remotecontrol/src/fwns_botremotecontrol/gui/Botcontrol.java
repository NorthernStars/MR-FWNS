package fwns_botremotecontrol.gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Component;

import javax.swing.JSeparator;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import fwns_botremotecontrol.core.BotLoader;
import fwns_botremotecontrol.core.Core;
import fwns_botremotecontrol.core.RemoteBot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.IllegalFormatException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;

public class Botcontrol {

    private JFrame mFrameBotcontrol;

    private static Botcontrol INSTANCE;
    
    private File mCoreJarFile;
    
    private JPanel mPanelContent;
    private JPanel mPanelFiller = new JPanel();
    public JTextField txtBotname;
    public JTextField txtTeamname;
    public JTextField txtBotFile;
    public JTextField txtRcId;
    public JTextField txtVtId;
    public JTextField txtServerIp;
    public JTextField txtServerPort;
    
    private DefaultComboBoxModel<String> mAiClasses = new DefaultComboBoxModel<String>();
    private JComboBox<String> cmbAiClasses;
    private JComboBox<Teams> cmbTeam;
    private int mRcId = 0;
    private int mVtId = 0;
    private JLabel lblStatus;
    private JButton btnStartBot;
    
    private Botcontrol(){
        initialize();
            
    }

    public static Botcontrol getInstance() {
        
        if( Botcontrol.INSTANCE == null){
            Core.getLogger().trace( "Creating Botcontrol-instance." );
            Botcontrol.INSTANCE = new Botcontrol();
        }

        Core.getLogger().trace( "Retrieving Botcontrol-instance." );
        return Botcontrol.INSTANCE;
        
    }
    
    public void revalidate(){

        mFrameBotcontrol.validate();       
        
    }
    
    public JFrame getMainFrame(){
        
        return mFrameBotcontrol;
        
    }
    
    /**
     * Launch the application.
     */
    public static void startGUI( ) {
        EventQueue.invokeLater( new Runnable() {
            @Override
			public void run() {
                try {
                    getInstance().mFrameBotcontrol.setVisible( true );
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mFrameBotcontrol = new JFrame();
        mFrameBotcontrol.setTitle("Botcontrol");
        mFrameBotcontrol.setBounds( 100, 100, 500, 600 );
        mFrameBotcontrol.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JMenuBar vMenuBar = new JMenuBar();
        mFrameBotcontrol.setJMenuBar(vMenuBar);
        
        JMenu vFileMenue = new JMenu("File");
        vFileMenue.setMnemonic('f');
        vMenuBar.add(vFileMenue);
        
        JMenuItem vMenueItemConnectToBot = new JMenuItem("Connect to Bot");
        vMenueItemConnectToBot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        vMenueItemConnectToBot.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                EventQueue.invokeLater( new Runnable() {
                    @Override
					public void run() {
                        try {
                            BotSearchWindow frame = new BotSearchWindow();
                            frame.setVisible( true );
                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                } );
                
            }
        });
        
        JMenuItem mntmLoadBotCore = new JMenuItem("Load bot core file");
        mntmLoadBotCore.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// create file chooser dialog and open it
        		JFileChooser chooser = new JFileChooser();
        		FileFilter filter = new FileNameExtensionFilter("fwns bot core jar file (.jar)", "jar");
        		chooser.setCurrentDirectory( new File( System.getProperty("user.dir" ) ));
        		chooser.addChoosableFileFilter(filter);
        		chooser.setFileFilter(filter);		
        		chooser.setAcceptAllFileFilterUsed(false);
        		chooser.setSelectedFile(new File("bot_mr.jar"));
        		
        		if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
        			mCoreJarFile = chooser.getSelectedFile();
        			if( mCoreJarFile.exists() ){
        				lblStatus.setText("Bot core jar file loaded.");
        				btnStartBot.setEnabled(true);
        			}
        		}
        	}
        });
        mntmLoadBotCore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        vFileMenue.add(mntmLoadBotCore);
        vFileMenue.add(vMenueItemConnectToBot);
        
        JSeparator vMenueItemSeparator = new JSeparator();
        vFileMenue.add(vMenueItemSeparator);
        
        JMenuItem vMenueItemExit = new JMenuItem("Exit");
        vMenueItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.SHIFT_MASK));
        vMenueItemExit.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mFrameBotcontrol.dispatchEvent( new WindowEvent( mFrameBotcontrol, WindowEvent.WINDOW_CLOSING ));
                mFrameBotcontrol.dispose();
                
            }
        });
        vFileMenue.add(vMenueItemExit);
        
        JPanel panel = new JPanel();
        vMenuBar.add(panel);
        
        lblStatus = new JLabel("");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblStatus);
                GridBagLayout gridBagLayout = new GridBagLayout();
                gridBagLayout.columnWidths = new int[]{0, 0};
                gridBagLayout.rowHeights = new int[]{0, 0, 0};
                gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
                mFrameBotcontrol.getContentPane().setLayout(gridBagLayout);
                        
                        JPanel panelAddBot = new JPanel();
                        GridBagConstraints gbc_panelAddBot = new GridBagConstraints();
                        gbc_panelAddBot.fill = GridBagConstraints.BOTH;
                        gbc_panelAddBot.insets = new Insets(0, 0, 5, 0);
                        gbc_panelAddBot.gridx = 0;
                        gbc_panelAddBot.gridy = 0;
                        mFrameBotcontrol.getContentPane().add(panelAddBot, gbc_panelAddBot);
                        GridBagLayout gbl_panelAddBot = new GridBagLayout();
                        gbl_panelAddBot.columnWidths = new int[]{0, 50, 50, 50, 80, 50, 0, 0};
                        gbl_panelAddBot.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        gbl_panelAddBot.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
                        gbl_panelAddBot.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                        panelAddBot.setLayout(gbl_panelAddBot);
                        
                        JLabel lblSpaceHolder0 = new JLabel("");
                        GridBagConstraints gbc_lblSpaceHolder0 = new GridBagConstraints();
                        gbc_lblSpaceHolder0.insets = new Insets(0, 0, 5, 5);
                        gbc_lblSpaceHolder0.gridx = 0;
                        gbc_lblSpaceHolder0.gridy = 0;
                        panelAddBot.add(lblSpaceHolder0, gbc_lblSpaceHolder0);
                        
                        JLabel lblBotname = new JLabel("Botname");
                        lblBotname.setHorizontalAlignment(SwingConstants.LEFT);
                        GridBagConstraints gbc_lblBotname = new GridBagConstraints();
                        gbc_lblBotname.gridwidth = 2;
                        gbc_lblBotname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblBotname.insets = new Insets(0, 0, 5, 5);
                        gbc_lblBotname.gridx = 1;
                        gbc_lblBotname.gridy = 1;
                        panelAddBot.add(lblBotname, gbc_lblBotname);
                        
                        JLabel lblBotfile = new JLabel("Botfile");
                        GridBagConstraints gbc_lblBotfile = new GridBagConstraints();
                        gbc_lblBotfile.gridwidth = 2;
                        gbc_lblBotfile.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblBotfile.insets = new Insets(0, 0, 5, 5);
                        gbc_lblBotfile.gridx = 3;
                        gbc_lblBotfile.gridy = 1;
                        panelAddBot.add(lblBotfile, gbc_lblBotfile);
                        
                        JButton btnChooseBotFile = new JButton("Choose bot file");
                        btnChooseBotFile.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
                        		// create file chooser dialog and open it
                        		JFileChooser chooser = new JFileChooser();
                        		FileFilter filter = new FileNameExtensionFilter("jar file (.jar)", "jar");
                        		chooser.setCurrentDirectory( new File( System.getProperty("user.dir" ) ));
                        		chooser.addChoosableFileFilter(filter);
                        		chooser.setFileFilter(filter);		
                        		chooser.setAcceptAllFileFilterUsed(false);
                        		chooser.setSelectedFile(new File("example_ai.jar"));
                        		
                        		if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
                        			// set filename
                        			File botFile =  chooser.getSelectedFile();
                        			txtBotFile.setText( botFile.getPath() );
                        			
                        			// load classnames
                        			updateBotFileClasses(botFile);
                        		}                        		
                        	}
                        });
                        GridBagConstraints gbc_btnChooseBotFile = new GridBagConstraints();
                        gbc_btnChooseBotFile.gridheight = 2;
                        gbc_btnChooseBotFile.fill = GridBagConstraints.BOTH;
                        gbc_btnChooseBotFile.insets = new Insets(0, 0, 5, 5);
                        gbc_btnChooseBotFile.gridx = 5;
                        gbc_btnChooseBotFile.gridy = 1;
                        panelAddBot.add(btnChooseBotFile, gbc_btnChooseBotFile);
                        
                        txtBotname = new JTextField();
                        GridBagConstraints gbc_txtBotname = new GridBagConstraints();
                        gbc_txtBotname.gridwidth = 2;
                        gbc_txtBotname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtBotname.insets = new Insets(0, 0, 5, 5);
                        gbc_txtBotname.gridx = 1;
                        gbc_txtBotname.gridy = 2;
                        panelAddBot.add(txtBotname, gbc_txtBotname);
                        txtBotname.setColumns(10);
                        
                        txtBotFile = new JTextField();
                        txtBotFile.setText("ais/example_ai.jar");
                        GridBagConstraints gbc_txtBotFile = new GridBagConstraints();
                        gbc_txtBotFile.gridwidth = 2;
                        gbc_txtBotFile.insets = new Insets(0, 0, 5, 5);
                        gbc_txtBotFile.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtBotFile.gridx = 3;
                        gbc_txtBotFile.gridy = 2;
                        panelAddBot.add(txtBotFile, gbc_txtBotFile);
                        txtBotFile.setColumns(10);
                        
                        JLabel lblTeamname = new JLabel("Teamname");
                        GridBagConstraints gbc_lblTeamname = new GridBagConstraints();
                        gbc_lblTeamname.gridwidth = 2;
                        gbc_lblTeamname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblTeamname.insets = new Insets(0, 0, 5, 5);
                        gbc_lblTeamname.gridx = 1;
                        gbc_lblTeamname.gridy = 3;
                        panelAddBot.add(lblTeamname, gbc_lblTeamname);
                        
                        JLabel lblAiClass = new JLabel("AI class");
                        GridBagConstraints gbc_lblAiClass = new GridBagConstraints();
                        gbc_lblAiClass.gridwidth = 3;
                        gbc_lblAiClass.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblAiClass.insets = new Insets(0, 0, 5, 5);
                        gbc_lblAiClass.gridx = 3;
                        gbc_lblAiClass.gridy = 3;
                        panelAddBot.add(lblAiClass, gbc_lblAiClass);
                        
                        txtTeamname = new JTextField();
                        GridBagConstraints gbc_txtTeamname = new GridBagConstraints();
                        gbc_txtTeamname.gridwidth = 2;
                        gbc_txtTeamname.insets = new Insets(0, 0, 5, 5);
                        gbc_txtTeamname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtTeamname.gridx = 1;
                        gbc_txtTeamname.gridy = 4;
                        panelAddBot.add(txtTeamname, gbc_txtTeamname);
                        txtTeamname.setColumns(10);
                        
                        cmbAiClasses = new JComboBox<String>();
                        cmbAiClasses.setModel(mAiClasses);
                        GridBagConstraints gbc_cmbAiClasses = new GridBagConstraints();
                        gbc_cmbAiClasses.gridwidth = 3;
                        gbc_cmbAiClasses.insets = new Insets(0, 0, 5, 5);
                        gbc_cmbAiClasses.fill = GridBagConstraints.HORIZONTAL;
                        gbc_cmbAiClasses.gridx = 3;
                        gbc_cmbAiClasses.gridy = 4;
                        panelAddBot.add(cmbAiClasses, gbc_cmbAiClasses);
                        
                        JLabel lblRcid = new JLabel("RC-ID");
                        GridBagConstraints gbc_lblRcid = new GridBagConstraints();
                        gbc_lblRcid.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblRcid.insets = new Insets(0, 0, 5, 5);
                        gbc_lblRcid.gridx = 1;
                        gbc_lblRcid.gridy = 5;
                        panelAddBot.add(lblRcid, gbc_lblRcid);
                        
                        JLabel lblVtid = new JLabel("VT-ID");
                        GridBagConstraints gbc_lblVtid = new GridBagConstraints();
                        gbc_lblVtid.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblVtid.insets = new Insets(0, 0, 5, 5);
                        gbc_lblVtid.gridx = 2;
                        gbc_lblVtid.gridy = 5;
                        panelAddBot.add(lblVtid, gbc_lblVtid);
                        
                        JLabel lblTeamColor = new JLabel("Team color");
                        GridBagConstraints gbc_lblTeamColor = new GridBagConstraints();
                        gbc_lblTeamColor.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblTeamColor.insets = new Insets(0, 0, 5, 5);
                        gbc_lblTeamColor.gridx = 3;
                        gbc_lblTeamColor.gridy = 5;
                        panelAddBot.add(lblTeamColor, gbc_lblTeamColor);
                        
                        JLabel lblServerIp = new JLabel("Server IP");
                        GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
                        gbc_lblServerIp.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblServerIp.insets = new Insets(0, 0, 5, 5);
                        gbc_lblServerIp.gridx = 4;
                        gbc_lblServerIp.gridy = 5;
                        panelAddBot.add(lblServerIp, gbc_lblServerIp);
                        
                        JLabel lblServerPort = new JLabel("Server port");
                        GridBagConstraints gbc_lblServerPort = new GridBagConstraints();
                        gbc_lblServerPort.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblServerPort.insets = new Insets(0, 0, 5, 5);
                        gbc_lblServerPort.gridx = 5;
                        gbc_lblServerPort.gridy = 5;
                        panelAddBot.add(lblServerPort, gbc_lblServerPort);
                        
                        txtRcId = new JTextField();
                        txtRcId.setHorizontalAlignment(SwingConstants.CENTER);
                        txtRcId.setText( Integer.toString(mRcId) );
                        GridBagConstraints gbc_txtRcId = new GridBagConstraints();
                        gbc_txtRcId.insets = new Insets(0, 0, 5, 5);
                        gbc_txtRcId.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtRcId.gridx = 1;
                        gbc_txtRcId.gridy = 6;
                        panelAddBot.add(txtRcId, gbc_txtRcId);
                        txtRcId.setColumns(10);
                        
                        txtVtId = new JTextField();
                        txtVtId.setHorizontalAlignment(SwingConstants.CENTER);
                        txtVtId.setText( Integer.toString(mVtId) );
                        GridBagConstraints gbc_txtVtId = new GridBagConstraints();
                        gbc_txtVtId.insets = new Insets(0, 0, 5, 5);
                        gbc_txtVtId.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtVtId.gridx = 2;
                        gbc_txtVtId.gridy = 6;
                        panelAddBot.add(txtVtId, gbc_txtVtId);
                        txtVtId.setColumns(10);
                        
                        cmbTeam = new JComboBox<Teams>();
                        cmbTeam.setModel(new DefaultComboBoxModel<Teams>(Teams.values()));
                        GridBagConstraints gbc_cmbTeam = new GridBagConstraints();
                        gbc_cmbTeam.insets = new Insets(0, 0, 5, 5);
                        gbc_cmbTeam.fill = GridBagConstraints.HORIZONTAL;
                        gbc_cmbTeam.gridx = 3;
                        gbc_cmbTeam.gridy = 6;
                        panelAddBot.add(cmbTeam, gbc_cmbTeam);
                        
                        txtServerIp = new JTextField();
                        txtServerIp.setText("127.0.0.1");
                        GridBagConstraints gbc_txtServerIp = new GridBagConstraints();
                        gbc_txtServerIp.insets = new Insets(0, 0, 5, 5);
                        gbc_txtServerIp.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtServerIp.gridx = 4;
                        gbc_txtServerIp.gridy = 6;
                        panelAddBot.add(txtServerIp, gbc_txtServerIp);
                        txtServerIp.setColumns(10);
                        
                        txtServerPort = new JTextField();
                        txtServerPort.setText("3311");
                        GridBagConstraints gbc_txtServerPort = new GridBagConstraints();
                        gbc_txtServerPort.insets = new Insets(0, 0, 5, 5);
                        gbc_txtServerPort.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtServerPort.gridx = 5;
                        gbc_txtServerPort.gridy = 6;
                        panelAddBot.add(txtServerPort, gbc_txtServerPort);
                        txtServerPort.setColumns(10);
                        
                        btnStartBot = new JButton("Start bot");
                        btnStartBot.setEnabled(false);
                        btnStartBot.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {                        		
                        		new Thread(new Runnable(){                					
									@Override
                					public void run(){                        		
		                        		startNewBot();		                        		
                					}
                        		}).start();                        		
                        	}
                        });
                        GridBagConstraints gbc_bntStartBot = new GridBagConstraints();
                        gbc_bntStartBot.gridheight = 2;
                        gbc_bntStartBot.fill = GridBagConstraints.BOTH;
                        gbc_bntStartBot.gridwidth = 5;
                        gbc_bntStartBot.insets = new Insets(0, 0, 5, 5);
                        gbc_bntStartBot.gridx = 1;
                        gbc_bntStartBot.gridy = 7;
                        panelAddBot.add(btnStartBot, gbc_bntStartBot);
                        
                        JLabel lblSpaceHolder1 = new JLabel("");
                        GridBagConstraints gbc_lblSpaceHolder1 = new GridBagConstraints();
                        gbc_lblSpaceHolder1.gridx = 6;
                        gbc_lblSpaceHolder1.gridy = 9;
                        panelAddBot.add(lblSpaceHolder1, gbc_lblSpaceHolder1);
                
                        mPanelContent = new JPanel();
                        
                        JScrollPane vScrollPane = new JScrollPane(mPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        GridBagLayout gbl_mPanelContent = new GridBagLayout();
                        gbl_mPanelContent.columnWidths = new int[]{0};
                        gbl_mPanelContent.rowHeights = new int[]{0};
                        gbl_mPanelContent.columnWeights = new double[]{Double.MIN_VALUE};
                        gbl_mPanelContent.rowWeights = new double[]{Double.MIN_VALUE};
                        mPanelContent.setLayout(gbl_mPanelContent);
                        GridBagConstraints gbc_vScrollPane = new GridBagConstraints();
                        gbc_vScrollPane.fill = GridBagConstraints.BOTH;
                        gbc_vScrollPane.gridx = 0;
                        gbc_vScrollPane.gridy = 1;
                        mFrameBotcontrol.getContentPane().add(vScrollPane, gbc_vScrollPane);

        mPanelFiller.setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );
        
        mFrameBotcontrol.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e) {
                
                super.windowClosed( e );
                windowClosing( e );
                
            }
            
            @Override
			public void windowClosing( WindowEvent e )
            {
                
                super.windowClosing( e );
                for( Component vComponent : mPanelContent.getComponents()){
                    
                    System.out.println("---" + vComponent.getClass() );
                    if( vComponent.getClass() == BotFrame.class ){
                        
                        ((BotFrame)vComponent).close( false );
                        
                    }
                    
                }
                
            }
        }
        );
        
        // Try to load data
        preloadData();
        
    }
    
    
    private void startNewBot(){
    	try{
			
			String botname = txtBotname.getText().trim();
			String teamname = txtTeamname.getText().trim();
			String aiclassname = cmbAiClasses.getItemAt( cmbAiClasses.getSelectedIndex() );
			String serverip = txtServerIp.getText().trim();
			int serverport = (int)( Double.parseDouble(txtServerPort.getText()) );
			mRcId = (int)( Double.parseDouble(txtRcId.getText()) );
			mVtId = (int)( Double.parseDouble(txtVtId.getText()) );
			Teams team = cmbTeam.getItemAt( cmbTeam.getSelectedIndex() );
			File aifile = new File(txtBotFile.getText());
			
			// check botname
			if( botname.length() == 0 && aiclassname != null ){
				botname = aiclassname.substring( aiclassname.lastIndexOf(".")+1 );
			}
			
			// set bot information
			BotInformation vBot = new BotInformation();
			vBot.setBotname(botname);
			vBot.setTeamname(teamname);
			vBot.setAIClassname(aiclassname);
			vBot.setServerIP( InetAddress.getByName(serverip) );
			vBot.setServerPort( serverport );
			vBot.setRcId(mRcId);
			vBot.setVtId(mVtId);
			vBot.setTeam(team);
			vBot.setAIArchive( aifile.getPath() );
			
			// create new bot
			if( mCoreJarFile != null && mCoreJarFile.exists() ){                        				

				// load new bot
				BotLoader botLoader = new BotLoader(vBot, mCoreJarFile);
				if( botLoader.startBot() ){
					
					// increase rcid and vtid
					mRcId++;
					mVtId++;
					txtRcId.setText( Integer.toString(mRcId) );
					txtVtId.setText( Integer.toString(mVtId) );
					lblStatus.setText( "Started bot " + vBot.getBotname()
							+ " (" + vBot.getRcId() + "-" + vBot.getVtId() + ")" );
					
					// Create bot remotecontrol url
					String botRemoteURL = "//localhost:1099/" + vBot.getBotname()
							+ "-" + vBot.getRcId() + "-" + vBot.getVtId();
					
					// wait until bot is registered
					boolean botRegistered = true;
					try{			                        					
    					long tm = System.currentTimeMillis();
    					while( !botRegistered
    							&& (System.currentTimeMillis()-tm < 10000)){
    						String[] vListOfBots = Core.getInstance().getListOfBots("//localhost:1099/");
    						for( String url : vListOfBots ){
    							if( url.equals(botRemoteURL) ){
    								botRegistered = true;
    								break;
    							}
    						}
    						Thread.sleep(100);
    					}
					}catch( Exception e ){
						e.printStackTrace();
					}
					
					// check if bot is registered
					if( botRegistered ){		                        					
    					// show bot frame
    					BotFrame vNewBotFrame = new BotFrame();
    	                RemoteBot vNewRemoteBot = null;
    	                
    	                boolean loaded = false;
    	                
    	                try{
    	                	
	    	                long tm = System.currentTimeMillis();
	    	                while( !loaded && System.currentTimeMillis()-tm < 10000 ){
	        	                try {
	        	                	
	        	                	System.out.println("try");
	        	                    vNewRemoteBot = new RemoteBot( botRemoteURL, vNewBotFrame, botLoader );
	        	                    
	        	                } catch ( RemoteException | MalformedURLException | NotBoundException e1 ) {
	        	                	Thread.sleep(250);
	        	                    continue;
	        	                } catch (Exception e){
	        	                	Thread.sleep(250);
	        	                	continue;
	        	                }
	        	                
	    	                    Botcontrol.getInstance().addBotFrame( vNewBotFrame );
	    	                    loaded = true;
	    	                   	Core.getLogger().debug("Added bot " + botRemoteURL + " to remote control.");
	    	                   	lblStatus.setText("Loaded bot " + vBot.getBotname()
							+ " (" + vBot.getRcId() + "-" + vBot.getVtId() + ")");
	    	                }
	    	                
    	                }catch (InterruptedException e){
    	                	e.printStackTrace();
    	                }
    	                
    	                if( !loaded ){
    	                	vNewRemoteBot.close( false );
    	                    vNewBotFrame.close( false );   
    	                }
					}else {
						// not registered > stop bot
						botLoader.stopBot();
					}
				}
				
			}
		
		
		}catch (IllegalFormatException err){
			Core.getLogger().error("Can not convert string to number. " + err.getLocalizedMessage());
		} catch (UnknownHostException e2) {
			Core.getLogger().error("Could not resolve server ip.");
		}
    }

    
    public void addBotFrame( BotFrame botFrame ) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty = 0.0;
        
        mPanelContent.remove( mPanelFiller );
        mPanelContent.add( botFrame, c );
        
        c.weighty = 1.0;
        mPanelContent.add( mPanelFiller, c );
        
        mFrameBotcontrol.validate();
        
    }

    public void removeBotframe( BotFrame aBotFrame ) {
        
        mPanelContent.remove( aBotFrame );
        
    }
    
    /**
     * Tries to load data on start up of gui.
     */
    private void preloadData(){
    	
    	// try to load core jar file
    	File f = new File("bot_mr.jar");
    	System.out.println("core jar: " + f.getPath() + " " + f.exists());
    	if( f.exists() ){
    		mCoreJarFile = f;
    		btnStartBot.setEnabled(true);
    	}
    	else{
    		lblStatus.setText("No bot core file loaded!");
    	}
    	
    	// try to load default ai classes
    	f = new File( txtBotFile.getText() );
    	if( f.exists() ){
    		updateBotFileClasses(f);
    	}
    	else{
    		txtBotFile.setText("");
    	}
    		
    }
    
    /**
     * Updates list of classes inside bot file
     * @param botFile	{@link File}
     */
    private void updateBotFileClasses(File botFile){
    	try{
			
    		mAiClasses.removeAllElements();
			ZipInputStream zip = new ZipInputStream( new FileInputStream(botFile) );
			
			for( ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry() ){
				if(entry.getName().endsWith(".class") && !entry.isDirectory()) {
					
					// This ZipEntry represents a class. Now, what class does it represent?
			        StringBuilder className = new StringBuilder();
			        for( String part : entry.getName().split("/") ) {
			            if( className.length() != 0 ){
			            	className.append(".");
			            }
			            className.append(part);
			            if( part.endsWith(".class") ){
			                className.setLength(className.length()-".class".length());
			            }
			        }
			        
			        mAiClasses.addElement( className.toString() );
			        
			    }
			}
			zip.close();
			
		}catch (FileNotFoundException err){
			Core.getLogger().trace("COuld not read bot file " + botFile.getPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

}
