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

import java.awt.GridLayout;

import javax.swing.ImageIcon;

public class Botcontrol {

    private JFrame mFrameBotcontrol;

    private static Botcontrol INSTANCE;
    
    private File mCoreJarFile;
    private File mBotFile;
    
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
    private JButton btnStartBot;
    private JButton btnLoadCoreFile;
    private JButton btnLoadBotFile;
    private JLabel lblStatus;
    
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
        mFrameBotcontrol.setBounds( 100, 100, 554, 622 );
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
        
        JMenuItem mntmConnectToBot = new JMenuItem("Connect to bot");
        mntmConnectToBot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		BotSearchWindow search = new BotSearchWindow();
        		search.setVisible(true);
        	}
        });
        mntmConnectToBot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        vFileMenue.add(mntmConnectToBot);
        vFileMenue.add(vMenueItemExit);
                GridBagLayout gridBagLayout = new GridBagLayout();
                gridBagLayout.columnWidths = new int[]{0, 0};
                gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
                gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
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
                        gbl_panelAddBot.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        gbl_panelAddBot.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
                        gbl_panelAddBot.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                        panelAddBot.setLayout(gbl_panelAddBot);
                        
                        JLabel lblSpaceHolder0 = new JLabel("");
                        GridBagConstraints gbc_lblSpaceHolder0 = new GridBagConstraints();
                        gbc_lblSpaceHolder0.insets = new Insets(0, 0, 5, 5);
                        gbc_lblSpaceHolder0.gridx = 0;
                        gbc_lblSpaceHolder0.gridy = 0;
                        panelAddBot.add(lblSpaceHolder0, gbc_lblSpaceHolder0);
                        
                        JPanel panel_1 = new JPanel();
                        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
                        gbc_panel_1.gridwidth = 5;
                        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
                        gbc_panel_1.fill = GridBagConstraints.BOTH;
                        gbc_panel_1.gridx = 1;
                        gbc_panel_1.gridy = 1;
                        panelAddBot.add(panel_1, gbc_panel_1);
                        panel_1.setLayout(new GridLayout(1, 0, 5, 0));
                        
                        btnLoadCoreFile = new JButton("Load Framework Core File");
                        btnLoadCoreFile.addActionListener(new ActionListener() {
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
                        			
                        			// update gui
                        			updateFileLoadedButtons();
                        		}
                        	}
                        });
                        btnLoadCoreFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/red_signal.gif")));
                        panel_1.add(btnLoadCoreFile);
                        
                        btnLoadBotFile = new JButton("Choose Bot File");
                        btnLoadBotFile.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent arg0) {
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
                        			mBotFile =  chooser.getSelectedFile();
                        			txtBotFile.setText( mBotFile.getPath() );
                        			
                        			// update gui
                        			updateFileLoadedButtons();
                        		}          
                        	}
                        });
                        btnLoadBotFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/red_signal.gif")));
                        panel_1.add(btnLoadBotFile);
                        
                        JLabel lblBotname = new JLabel("Botname");
                        lblBotname.setHorizontalAlignment(SwingConstants.LEFT);
                        GridBagConstraints gbc_lblBotname = new GridBagConstraints();
                        gbc_lblBotname.gridwidth = 2;
                        gbc_lblBotname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblBotname.insets = new Insets(0, 0, 5, 5);
                        gbc_lblBotname.gridx = 1;
                        gbc_lblBotname.gridy = 2;
                        panelAddBot.add(lblBotname, gbc_lblBotname);
                        
                        JLabel lblBotfile = new JLabel("Botfile");
                        GridBagConstraints gbc_lblBotfile = new GridBagConstraints();
                        gbc_lblBotfile.gridwidth = 3;
                        gbc_lblBotfile.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblBotfile.insets = new Insets(0, 0, 5, 5);
                        gbc_lblBotfile.gridx = 3;
                        gbc_lblBotfile.gridy = 2;
                        panelAddBot.add(lblBotfile, gbc_lblBotfile);
                        
                        txtBotname = new JTextField();
                        GridBagConstraints gbc_txtBotname = new GridBagConstraints();
                        gbc_txtBotname.gridwidth = 2;
                        gbc_txtBotname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtBotname.insets = new Insets(0, 0, 5, 5);
                        gbc_txtBotname.gridx = 1;
                        gbc_txtBotname.gridy = 3;
                        panelAddBot.add(txtBotname, gbc_txtBotname);
                        txtBotname.setColumns(10);
                        
                        txtBotFile = new JTextField();
                        txtBotFile.setText("ais/example_ai.jar");
                        GridBagConstraints gbc_txtBotFile = new GridBagConstraints();
                        gbc_txtBotFile.gridwidth = 3;
                        gbc_txtBotFile.insets = new Insets(0, 0, 5, 5);
                        gbc_txtBotFile.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtBotFile.gridx = 3;
                        gbc_txtBotFile.gridy = 3;
                        panelAddBot.add(txtBotFile, gbc_txtBotFile);
                        txtBotFile.setColumns(10);
                        
                        JLabel lblTeamname = new JLabel("Teamname");
                        GridBagConstraints gbc_lblTeamname = new GridBagConstraints();
                        gbc_lblTeamname.gridwidth = 2;
                        gbc_lblTeamname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblTeamname.insets = new Insets(0, 0, 5, 5);
                        gbc_lblTeamname.gridx = 1;
                        gbc_lblTeamname.gridy = 4;
                        panelAddBot.add(lblTeamname, gbc_lblTeamname);
                        
                        JLabel lblAiClass = new JLabel("AI class");
                        GridBagConstraints gbc_lblAiClass = new GridBagConstraints();
                        gbc_lblAiClass.gridwidth = 3;
                        gbc_lblAiClass.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblAiClass.insets = new Insets(0, 0, 5, 5);
                        gbc_lblAiClass.gridx = 3;
                        gbc_lblAiClass.gridy = 4;
                        panelAddBot.add(lblAiClass, gbc_lblAiClass);
                        
                        txtTeamname = new JTextField();
                        GridBagConstraints gbc_txtTeamname = new GridBagConstraints();
                        gbc_txtTeamname.gridwidth = 2;
                        gbc_txtTeamname.insets = new Insets(0, 0, 5, 5);
                        gbc_txtTeamname.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtTeamname.gridx = 1;
                        gbc_txtTeamname.gridy = 5;
                        panelAddBot.add(txtTeamname, gbc_txtTeamname);
                        txtTeamname.setColumns(10);
                        
                        cmbAiClasses = new JComboBox<String>();
                        cmbAiClasses.setModel(mAiClasses);
                        GridBagConstraints gbc_cmbAiClasses = new GridBagConstraints();
                        gbc_cmbAiClasses.gridwidth = 3;
                        gbc_cmbAiClasses.insets = new Insets(0, 0, 5, 5);
                        gbc_cmbAiClasses.fill = GridBagConstraints.HORIZONTAL;
                        gbc_cmbAiClasses.gridx = 3;
                        gbc_cmbAiClasses.gridy = 5;
                        panelAddBot.add(cmbAiClasses, gbc_cmbAiClasses);
                        
                        JLabel lblRcid = new JLabel("RC-ID");
                        GridBagConstraints gbc_lblRcid = new GridBagConstraints();
                        gbc_lblRcid.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblRcid.insets = new Insets(0, 0, 5, 5);
                        gbc_lblRcid.gridx = 1;
                        gbc_lblRcid.gridy = 6;
                        panelAddBot.add(lblRcid, gbc_lblRcid);
                        
                        JLabel lblVtid = new JLabel("VT-ID");
                        GridBagConstraints gbc_lblVtid = new GridBagConstraints();
                        gbc_lblVtid.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblVtid.insets = new Insets(0, 0, 5, 5);
                        gbc_lblVtid.gridx = 2;
                        gbc_lblVtid.gridy = 6;
                        panelAddBot.add(lblVtid, gbc_lblVtid);
                        
                        JLabel lblTeamColor = new JLabel("Team color");
                        GridBagConstraints gbc_lblTeamColor = new GridBagConstraints();
                        gbc_lblTeamColor.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblTeamColor.insets = new Insets(0, 0, 5, 5);
                        gbc_lblTeamColor.gridx = 3;
                        gbc_lblTeamColor.gridy = 6;
                        panelAddBot.add(lblTeamColor, gbc_lblTeamColor);
                        
                        JLabel lblServerIp = new JLabel("Server IP");
                        GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
                        gbc_lblServerIp.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblServerIp.insets = new Insets(0, 0, 5, 5);
                        gbc_lblServerIp.gridx = 4;
                        gbc_lblServerIp.gridy = 6;
                        panelAddBot.add(lblServerIp, gbc_lblServerIp);
                        
                        JLabel lblServerPort = new JLabel("Server port");
                        GridBagConstraints gbc_lblServerPort = new GridBagConstraints();
                        gbc_lblServerPort.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblServerPort.insets = new Insets(0, 0, 5, 5);
                        gbc_lblServerPort.gridx = 5;
                        gbc_lblServerPort.gridy = 6;
                        panelAddBot.add(lblServerPort, gbc_lblServerPort);
                        
                        txtRcId = new JTextField();
                        txtRcId.setHorizontalAlignment(SwingConstants.CENTER);
                        txtRcId.setText( Integer.toString(mRcId) );
                        GridBagConstraints gbc_txtRcId = new GridBagConstraints();
                        gbc_txtRcId.insets = new Insets(0, 0, 5, 5);
                        gbc_txtRcId.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtRcId.gridx = 1;
                        gbc_txtRcId.gridy = 7;
                        panelAddBot.add(txtRcId, gbc_txtRcId);
                        txtRcId.setColumns(10);
                        
                        txtVtId = new JTextField();
                        txtVtId.setHorizontalAlignment(SwingConstants.CENTER);
                        txtVtId.setText( Integer.toString(mVtId) );
                        GridBagConstraints gbc_txtVtId = new GridBagConstraints();
                        gbc_txtVtId.insets = new Insets(0, 0, 5, 5);
                        gbc_txtVtId.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtVtId.gridx = 2;
                        gbc_txtVtId.gridy = 7;
                        panelAddBot.add(txtVtId, gbc_txtVtId);
                        txtVtId.setColumns(10);
                        
                        cmbTeam = new JComboBox<Teams>();
                        cmbTeam.setModel(new DefaultComboBoxModel<Teams>(Teams.values()));
                        GridBagConstraints gbc_cmbTeam = new GridBagConstraints();
                        gbc_cmbTeam.insets = new Insets(0, 0, 5, 5);
                        gbc_cmbTeam.fill = GridBagConstraints.HORIZONTAL;
                        gbc_cmbTeam.gridx = 3;
                        gbc_cmbTeam.gridy = 7;
                        panelAddBot.add(cmbTeam, gbc_cmbTeam);
                        
                        txtServerIp = new JTextField();
                        txtServerIp.setText("127.0.0.1");
                        GridBagConstraints gbc_txtServerIp = new GridBagConstraints();
                        gbc_txtServerIp.insets = new Insets(0, 0, 5, 5);
                        gbc_txtServerIp.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtServerIp.gridx = 4;
                        gbc_txtServerIp.gridy = 7;
                        panelAddBot.add(txtServerIp, gbc_txtServerIp);
                        txtServerIp.setColumns(10);
                        
                        txtServerPort = new JTextField();
                        txtServerPort.setText("3311");
                        GridBagConstraints gbc_txtServerPort = new GridBagConstraints();
                        gbc_txtServerPort.insets = new Insets(0, 0, 5, 5);
                        gbc_txtServerPort.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtServerPort.gridx = 5;
                        gbc_txtServerPort.gridy = 7;
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
                        gbc_bntStartBot.gridy = 8;
                        panelAddBot.add(btnStartBot, gbc_bntStartBot);
                        
                        JLabel lblSpaceHolder1 = new JLabel("");
                        GridBagConstraints gbc_lblSpaceHolder1 = new GridBagConstraints();
                        gbc_lblSpaceHolder1.gridx = 6;
                        gbc_lblSpaceHolder1.gridy = 10;
                        panelAddBot.add(lblSpaceHolder1, gbc_lblSpaceHolder1);
                
                        mPanelContent = new JPanel();
                        
                        JScrollPane vScrollPane = new JScrollPane(mPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        GridBagLayout gbl_mPanelContent = new GridBagLayout();
                        gbl_mPanelContent.columnWidths = new int[]{0, 0};
                        gbl_mPanelContent.rowHeights = new int[]{0, 0};
                        gbl_mPanelContent.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                        gbl_mPanelContent.rowWeights = new double[]{0.0, Double.MIN_VALUE};
                        mPanelContent.setLayout(gbl_mPanelContent);
                        
                        
                        GridBagConstraints gbc_vScrollPane = new GridBagConstraints();
                        gbc_vScrollPane.insets = new Insets(0, 0, 5, 0);
                        gbc_vScrollPane.fill = GridBagConstraints.BOTH;
                        gbc_vScrollPane.gridx = 0;
                        gbc_vScrollPane.gridy = 1;
                        mFrameBotcontrol.getContentPane().add(vScrollPane, gbc_vScrollPane);
                        
                        JButton btnStopAllProcesses = new JButton("Stop all bots");
                        btnStopAllProcesses.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent arg0) {
                        		BotLoader.stopRunningProcesses();
                        		System.out.println("removed");
                        		mPanelContent.removeAll();
                        		mPanelContent.validate();
                        		mPanelContent.repaint();
                        	}
                        });
                        GridBagConstraints gbc_btnStopAllProcesses = new GridBagConstraints();
                        gbc_btnStopAllProcesses.gridx = 0;
                        gbc_btnStopAllProcesses.gridy = 2;
                        mFrameBotcontrol.getContentPane().add(btnStopAllProcesses, gbc_btnStopAllProcesses);
                        
                        lblStatus = new JLabel(" ");
                        lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
                        GridBagConstraints gbc_lblStatus = new GridBagConstraints();
                        gbc_lblStatus.insets = new Insets(0, 0, 5, 0);
                        gbc_lblStatus.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblStatus.gridx = 0;
                        gbc_lblStatus.gridy = 3;
                        mFrameBotcontrol.getContentPane().add(lblStatus, gbc_lblStatus);
                        
                        

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
                        
                        ((BotFrame)vComponent).close();
                        
                    }
                    
                }
                
            }
        }
        );
        
        // Try to load data
        preloadData();
        
    }
    
    /**
     * Starts a new bot
     */
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
					
					// disable start button
					btnStartBot.setEnabled(false);
					
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
					boolean botRegistered = false;
					try{			                        					
    					long tm = System.currentTimeMillis();
    					while( !botRegistered
    							&& (System.currentTimeMillis()-tm < 5000)){
    						
    						String[] vListOfBots = Core.getInstance().getListOfBots("//localhost:1099/");
    						
    						if( vListOfBots != null ){
	    						for( String url : vListOfBots ){
	    							if( url.equals(botRemoteURL) ){
	    								botRegistered = true;
	    								Core.getLogger().debug("Found {} = {}", url, botRemoteURL);
	    								break;
	    							}
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
    	                
		                try {
	
		                    new RemoteBot( botRemoteURL, vNewBotFrame, botLoader );
		                    Botcontrol.getInstance().addBotFrame( vNewBotFrame );
		                    
		                } catch ( RemoteException | MalformedURLException | NotBoundException e1 ) {
		                	e1.printStackTrace();
		                    vNewBotFrame.close();  
		                }
	        	                
	    	                    
	                   	Core.getLogger().debug("Added bot {} to remote control.", botRemoteURL);
	                   	lblStatus.setText("Registered bot " + vBot.getBotname()
	                   			+ " (" + vBot.getRcId() + "-" + vBot.getVtId() + ")");
    	                
					}else {
						// not registered > stop bot
						Core.getLogger().debug("Bot {} not found", botRemoteURL);
						botLoader.stopBot();
					}
				}
				
			}
		
		
		}catch (IllegalFormatException err){
			Core.getLogger().error("Can not convert string to number. " + err.getLocalizedMessage());
		} catch (UnknownHostException e2) {
			Core.getLogger().error("Could not resolve server ip.");
		}
    	
    	// Enable start button
    	btnStartBot.setEnabled(true);
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
    	mBotFile = new File("ais/example_ai.jar");
    	
    	// try to load default ai classes
    	mCoreJarFile = new File( "bot_mr.jar" );
    	
    	// update gui
    	updateFileLoadedButtons();
    	
    		
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
    
    private void updateFileLoadedButtons(){  
    	boolean vFilesLoaded = false;
    	
    	// check core file
    	if( mCoreJarFile != null && mCoreJarFile.exists() ){
    		btnLoadCoreFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/green_signal.gif")));
    		vFilesLoaded = true;
    	} else {
    		btnLoadCoreFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/red_signal.gif")));
    	}
    	
    	// check bot file
    	if( mBotFile != null && mBotFile.exists() ){
    		updateBotFileClasses(mBotFile);
    		btnLoadBotFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/green_signal.gif")));
    		vFilesLoaded = true;
    	} else {
    		btnLoadBotFile.setIcon(new ImageIcon(Botcontrol.class.getResource("/res/red_signal.gif")));
    		txtBotFile.setText("");
    	}
    	
    	// check if all files loaded
    	if( vFilesLoaded ){
    		btnStartBot.setEnabled(true);
    	} else {
    		btnStartBot.setEnabled(false);
    	}
    }
}
