package gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.JLabel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JSplitPane;
import essentials.core.BotInformation.Teams;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class BotFrame extends JPanel {

    private static final long serialVersionUID = 21785150238115621L;
    
    private String[] filler = new String[1111111];
    
    private JLabel lblNewLabel;
    private JPanel panel_3;
    private boolean mExpanded = true;
    private int mMaxLinesInLog = 1000;
    private JButton btnNewButton;
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JPanel panel_1;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;
    private JPanel panel_9;
    private JPanel panel_10;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JPopupMenu popupMenu;
    private JMenuItem mntmCopy;
    private JMenuItem mntmSetMaxLines;
    private JButton btnNewButton_3;
    private JButton btnNewButton_2;
    private JComboBox comboBox;
    private JPanel panel_11;
    private JPanel panel_12;
    private JTextField textField_1;
    private JTextField txtAisexampleaijar;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    private JTable table;

    /**
     * Create the panel.
     */
    public BotFrame() {
        
        Arrays.fill(filler, "Hullo" );
        
        setBorder(new LineBorder(new Color(0, 0, 0), 3));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(451, 241));
        setMinimumSize(new Dimension(400, 45));
        setMaximumSize(new Dimension(10000, 45));
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 40));
        panel.setMinimumSize(new Dimension(200, 40));
        panel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        panel_3 = new JPanel();
        panel.add(panel_3, BorderLayout.CENTER);
        panel_3.setLayout(null);
        
        setEnabled( false );
        
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setEnabled( false );
                setVisible( false );
                
                Botcontrol.getInstance().removeBotframe( (BotFrame) panel.getParent() );
                
                //TODO
                                
            }
        });
        btnNewButton_1.setMargin(new Insets(0, 0, 0, 0));
        btnNewButton_1.setPressedIcon(new ImageIcon(BotFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/close-pressed.gif")));
        btnNewButton_1.setPreferredSize(new Dimension(28, 28));
        btnNewButton_1.setMinimumSize(new Dimension(28, 28));
        btnNewButton_1.setMaximumSize(new Dimension(28, 28));
        btnNewButton_1.setIconTextGap(0);
        btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNewButton_1.setIcon(new ImageIcon(BotFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
        btnNewButton_1.setBounds(5, 5, 28, 28);
        panel_3.add(btnNewButton_1);
        
        lblNewLabel = new JLabel("New label");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(40, 5, 160, 28);
        panel_3.add(lblNewLabel);

        panel_3.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                
                lblNewLabel.setSize( panel_3.getWidth() - 45, lblNewLabel.getHeight() );
                
            }
        });
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2, BorderLayout.EAST);
        
        JLabel label_8 = new JLabel("");
        label_8.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_8.setPreferredSize(new Dimension(28, 28));
        label_8.setIconTextGap(0);
        label_8.setHorizontalTextPosition(SwingConstants.CENTER);
        label_8.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(label_8);
        
        JLabel label_7 = new JLabel("");
        label_7.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_7.setPreferredSize(new Dimension(28, 28));
        label_7.setIconTextGap(0);
        label_7.setHorizontalTextPosition(SwingConstants.CENTER);
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(label_7);
        
        JLabel label_6 = new JLabel("");
        label_6.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_6.setPreferredSize(new Dimension(28, 28));
        label_6.setIconTextGap(0);
        label_6.setHorizontalTextPosition(SwingConstants.CENTER);
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(label_6);
        
        JLabel label_4 = new JLabel("");
        label_4.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_4.setPreferredSize(new Dimension(28, 28));
        label_4.setIconTextGap(0);
        label_4.setHorizontalTextPosition(SwingConstants.CENTER);
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(label_4);
        
        JLabel label = new JLabel("");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIconTextGap(0);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label.setPreferredSize(new Dimension(28, 28));
        panel_2.add(label);
        
        btnNewButton = new JButton("");
        
        btnNewButton.setPreferredSize(new Dimension(28, 28));
        btnNewButton.setMinimumSize(new Dimension(28, 28));
        btnNewButton.setMaximumSize(new Dimension(28, 28));
        btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/contract.gif")));
        panel_2.add(btnNewButton);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);
        
        panel_1 = new JPanel();
        tabbedPane.addTab("Status", null, panel_1, null);
        panel_1.setLayout(null);
        
        JPanel panel_16 = new JPanel();
        panel_16.setBorder(new TitledBorder(null, "Network", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_16.setBounds(10, 11, 150, 100);
        panel_1.add(panel_16);
        panel_16.setLayout(null);
        
        JLabel lblNewLabel_14 = new JLabel("Connected....................");
        lblNewLabel_14.setBounds(10, 15, 113, 17);
        panel_16.add(lblNewLabel_14);
        
        JLabel lblNewLabel_15 = new JLabel("");
        lblNewLabel_15.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        lblNewLabel_15.setBounds(120, 15, 17, 17);
        panel_16.add(lblNewLabel_15);
        
        JLabel lblTrafficIncoming = new JLabel("Traffic incoming.............");
        lblTrafficIncoming.setBounds(10, 43, 113, 17);
        panel_16.add(lblTrafficIncoming);
        
        JLabel label_1 = new JLabel("");
        label_1.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_1.setBounds(120, 43, 17, 17);
        panel_16.add(label_1);
        
        JLabel lblTrafficOutgoing = new JLabel("Traffic outgoing............");
        lblTrafficOutgoing.setBounds(10, 71, 113, 17);
        panel_16.add(lblTrafficOutgoing);
        
        JLabel label_3 = new JLabel("");
        label_3.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_3.setBounds(120, 71, 17, 17);
        panel_16.add(label_3);
        
        JPanel panel_17 = new JPanel();
        panel_17.setLayout(null);
        panel_17.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "AI", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_17.setBounds(170, 11, 150, 73);
        panel_1.add(panel_17);
        
        JLabel lblRunning = new JLabel("Running.........................");
        lblRunning.setBounds(10, 15, 113, 17);
        panel_17.add(lblRunning);
        
        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_2.setBounds(120, 15, 17, 17);
        panel_17.add(label_2);
        
        JLabel lblLoaded = new JLabel("Loaded..........................");
        lblLoaded.setBounds(10, 43, 113, 17);
        panel_17.add(lblLoaded);
        
        JLabel label_5 = new JLabel("");
        label_5.setIcon(new ImageIcon(BotFrame.class.getResource("/res/red_signal.gif")));
        label_5.setBounds(120, 43, 17, 17);
        panel_17.add(label_5);
        
        JButton btnNewButton_13 = new JButton("Manually check status");
        btnNewButton_13.setBounds(10, 122, 310, 23);
        panel_1.add(btnNewButton_13);
        
        panel_4 = new JPanel();
        tabbedPane.addTab("Daten", null, panel_4, null);
        panel_4.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_14 = new JPanel();
        panel_14.setPreferredSize(new Dimension(200, 10));
        panel_4.add(panel_14, BorderLayout.WEST);
        panel_14.setLayout(null);
        
        JLabel lblNewLabel_8 = new JLabel("Botname");
        lblNewLabel_8.setBounds(10, 11, 180, 14);
        panel_14.add(lblNewLabel_8);
        
        textField_6 = new JTextField();
        textField_6.setBounds(10, 26, 180, 20);
        panel_14.add(textField_6);
        textField_6.setColumns(10);
        
        JLabel lblNewLabel_9 = new JLabel("Botteamname");
        lblNewLabel_9.setBounds(10, 51, 180, 14);
        panel_14.add(lblNewLabel_9);
        
        textField_7 = new JTextField();
        textField_7.setBounds(10, 65, 180, 20);
        panel_14.add(textField_7);
        textField_7.setColumns(10);
        
        JLabel lblNewLabel_10 = new JLabel("RC-ID");
        lblNewLabel_10.setBounds(10, 90, 35, 14);
        panel_14.add(lblNewLabel_10);
        
        JLabel lblNewLabel_11 = new JLabel("VT-ID");
        lblNewLabel_11.setBounds(55, 90, 35, 14);
        panel_14.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("Team");
        lblNewLabel_12.setBounds(100, 90, 90, 14);
        panel_14.add(lblNewLabel_12);
        
        textField_8 = new JTextField();
        textField_8.setText("22");
        textField_8.setBounds(10, 104, 35, 20);
        panel_14.add(textField_8);
        textField_8.setColumns(10);
        
        textField_9 = new JTextField();
        textField_9.setBounds(55, 104, 35, 20);
        panel_14.add(textField_9);
        textField_9.setColumns(10);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setModel(new DefaultComboBoxModel(Teams.values()));
        comboBox_1.setBounds(100, 103, 90, 22);
        panel_14.add(comboBox_1);
        
        JButton btnNewButton_12 = new JButton("Change Data");
        btnNewButton_12.setBounds(10, 135, 180, 23);
        panel_14.add(btnNewButton_12);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        panel_4.add(scrollPane_2, BorderLayout.CENTER);
        
        JPanel panel_15 = new JPanel();
        scrollPane_2.setViewportView(panel_15);
        panel_15.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel_13 = new JLabel("Serverkonstanten");
        panel_15.add(lblNewLabel_13, BorderLayout.NORTH);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "New column", "New column"
            }
        ));
        panel_15.add(table, BorderLayout.CENTER);
        
        panel_5 = new JPanel();
        tabbedPane.addTab("Connection", null, panel_5, null);
        panel_5.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_13 = new JPanel();
        panel_13.setPreferredSize(new Dimension(300, 10));
        panel_5.add(panel_13, BorderLayout.WEST);
        panel_13.setLayout(null);
        
        JLabel lblNewLabel_4 = new JLabel("Server IP-Address");
        lblNewLabel_4.setBounds(10, 11, 150, 14);
        panel_13.add(lblNewLabel_4);
        
        textField_2 = new JTextField();
        textField_2.setBounds(10, 27, 150, 20);
        panel_13.add(textField_2);
        textField_2.setColumns(10);
        
        textField_3 = new JTextField();
        textField_3.setBounds(170, 27, 86, 20);
        panel_13.add(textField_3);
        textField_3.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Server Teamport");
        lblNewLabel_5.setBounds(170, 11, 86, 14);
        panel_13.add(lblNewLabel_5);
        
        JLabel lblNewLabel_6 = new JLabel("Bot IPAddress");
        lblNewLabel_6.setBounds(10, 58, 150, 14);
        panel_13.add(lblNewLabel_6);
        
        textField_4 = new JTextField();
        textField_4.setBounds(10, 75, 150, 20);
        panel_13.add(textField_4);
        textField_4.setColumns(10);
        
        JLabel lblNewLabel_7 = new JLabel("Botport");
        lblNewLabel_7.setBounds(170, 58, 86, 14);
        panel_13.add(lblNewLabel_7);
        
        textField_5 = new JTextField();
        textField_5.setBounds(170, 75, 86, 20);
        panel_13.add(textField_5);
        textField_5.setColumns(10);
        
        JButton btnNewButton_9 = new JButton("Connect");
        btnNewButton_9.setBounds(10, 106, 118, 23);
        panel_13.add(btnNewButton_9);
        
        JButton btnNewButton_10 = new JButton("Reconnect");
        btnNewButton_10.setBounds(138, 106, 118, 23);
        panel_13.add(btnNewButton_10);
        
        JButton btnNewButton_11 = new JButton("Disconnect");
        btnNewButton_11.setVisible(false);
        btnNewButton_11.setEnabled(false);
        btnNewButton_11.setBounds(10, 106, 246, 23);
        panel_13.add(btnNewButton_11);
        
        panel_6 = new JPanel();
        tabbedPane.addTab("AI", null, panel_6, null);
        panel_6.setLayout(new BorderLayout(0, 0));
        
        panel_11 = new JPanel();
        panel_11.setPreferredSize(new Dimension(200, 10));
        panel_11.setSize(new Dimension(200, 0));
        panel_6.add(panel_11, BorderLayout.WEST);
        panel_11.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("Archive");
        lblNewLabel_2.setBounds(10, 11, 180, 14);
        panel_11.add(lblNewLabel_2);
        
        textField_1 = new JTextField();
        textField_1.setBounds(10, 29, 180, 20);
        panel_11.add(textField_1);
        textField_1.setColumns(50);
        
        JLabel lblNewLabel_3 = new JLabel("Classname");
        lblNewLabel_3.setBounds(10, 59, 180, 14);
        panel_11.add(lblNewLabel_3);
        
        txtAisexampleaijar = new JTextField();
        txtAisexampleaijar.setText("ais/example_ai.jar");
        txtAisexampleaijar.setBounds(10, 77, 180, 20);
        panel_11.add(txtAisexampleaijar);
        txtAisexampleaijar.setColumns(10);
        
        JButton btnNewButton_4 = new JButton("Initialise");
        btnNewButton_4.setBounds(10, 108, 180, 23);
        panel_11.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Start");
        btnNewButton_5.setEnabled(false);
        btnNewButton_5.setBounds(10, 142, 180, 23);
        panel_11.add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("Dispose");
        btnNewButton_6.setEnabled(false);
        btnNewButton_6.setVisible(false);
        btnNewButton_6.setBounds(10, 108, 180, 23);
        panel_11.add(btnNewButton_6);
        
        JButton btnNewButton_7 = new JButton("Pause");
        btnNewButton_7.setVisible(false);
        btnNewButton_7.setEnabled(false);
        btnNewButton_7.setBounds(10, 142, 180, 23);
        panel_11.add(btnNewButton_7);
        
        panel_12 = new JPanel();
        panel_6.add(panel_12, BorderLayout.CENTER);
        panel_12.setLayout(new BorderLayout(0, 0));
        
        JButton btnNewButton_8 = new JButton("Send Arguments");
        btnNewButton_8.setEnabled(false);
        panel_12.add(btnNewButton_8, BorderLayout.SOUTH);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        panel_12.add(scrollPane_1, BorderLayout.CENTER);
        
        JTextArea textArea_1 = new JTextArea();
        textArea_1.setLineWrap(true);
        scrollPane_1.setViewportView(textArea_1);
        
        panel_7 = new JPanel();
        tabbedPane.addTab("Logging", null, panel_7, null);
        panel_7.setLayout(new BorderLayout(0, 0));
        
        panel_8 = new JPanel();
        panel_7.add(panel_8, BorderLayout.NORTH);
        panel_8.setLayout(new BorderLayout(0, 0));
        
        panel_9 = new JPanel();
        panel_9.setMinimumSize(new Dimension(100, 25));
        panel_9.setPreferredSize(new Dimension(150, 23));
        panel_9.setSize(new Dimension(100, 23));
        panel_8.add(panel_9, BorderLayout.EAST);
        panel_9.setLayout(null);
        
        btnNewButton_2 = new JButton("Connect Logger");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //TODO
                btnNewButton_2.setEnabled(false);
                btnNewButton_2.setVisible(false);
                btnNewButton_3.setEnabled(true);
                btnNewButton_3.setVisible(true);
                
            }
        });
        btnNewButton_2.setEnabled(false);
        btnNewButton_2.setVisible(false);
        btnNewButton_2.setBounds(0, 0, 150, 23);
        panel_9.add(btnNewButton_2);
        
        btnNewButton_3 = new JButton("Disconnect Logger");
        
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //TODO
                btnNewButton_3.setEnabled(false);
                btnNewButton_3.setVisible(false);
                btnNewButton_2.setEnabled(true);
                btnNewButton_2.setVisible(true);
                
            }
        });
        btnNewButton_3.setBounds(0, 0, 150, 23);
        panel_9.add(btnNewButton_3);
        
        panel_10 = new JPanel();
        panel_8.add(panel_10, BorderLayout.CENTER);
        panel_10.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Loglevel:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(3, 5, 50, 17);
        panel_10.add(lblNewLabel_1);
        
        comboBox = new JComboBox();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //TODO
                System.out.println( comboBox.getSelectedItem() );
                
            }
        });
        comboBox.setModel(new DefaultComboBoxModel(Level.values()));
        comboBox.setBounds(55, 0, 100, 23);
        panel_10.add(comboBox);
        
        scrollPane = new JScrollPane();
        panel_7.add(scrollPane, BorderLayout.CENTER);
        
        textArea = new JTextArea();
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if( e.getButton() == MouseEvent.BUTTON3){
                    
                    popupMenu.show( e.getComponent(), e.getX(), e.getY() );
                    
                }
                
            }
        });
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        
        popupMenu = new JPopupMenu();
        popupMenu.setBounds(0, 0, 119, 25);
        
        mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                textArea.copy();
                
            }
        });
        popupMenu.add(mntmCopy);
        
                JSeparator vMenueItemSeparator = new JSeparator();
                popupMenu.add(vMenueItemSeparator);
                
                mntmSetMaxLines = new JMenuItem("Set Max Lines");
                mntmSetMaxLines.addActionListener(new ActionListener() {
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
                        
                        vMaxLines.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                        vMaxLines.setVisible( true );
                        
                    }
                });
                popupMenu.add(mntmSetMaxLines);
        
        btnNewButton.addActionListener(new ActionListener() {
            
            private int i = 0;
            
            public void actionPerformed(ActionEvent e) {
                
                if( mExpanded == true ){

                    btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/expand.gif")));
                    tabbedPane.setPreferredSize( new Dimension( 200, 0 ) );
                    tabbedPane.setMinimumSize(new Dimension( 0, 0 ));
                    tabbedPane.setEnabled( false );
                    tabbedPane.setVisible( false );
                    setMinimumSize( new Dimension( 200, 45 ));
                    setPreferredSize( new Dimension( 200, 45 ) );
                    mExpanded = false;
                    
                } else {

                    btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/contract.gif")));
                    tabbedPane.setPreferredSize( new Dimension( 200, 300 ) );
                    tabbedPane.setMinimumSize(new Dimension( 0, 300 ));
                    tabbedPane.setEnabled( true );
                    tabbedPane.setVisible( true );
                    setMinimumSize(new Dimension( 200, 240 ));
                    setPreferredSize( new Dimension( 200, 240 ) );
                    mExpanded = true;
                    
                }
                
                revalidate();
                getParent().revalidate();
                
            }
        });

    }
    
    public void addToLog( String aString ){
        
        while( textArea.getLineCount() > mMaxLinesInLog ){
            
            try {
                textArea.replaceRange( null, textArea.getLineStartOffset( 0 ), textArea.getLineEndOffset( 0 ) );
            } catch ( BadLocationException e2 ) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            
        }
        
        textArea.append( aString );
        
    }
}
