package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ScrollPaneConstants;
import javax.swing.JSpinner;

import java.awt.FlowLayout;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

import core.Core;
import core.RemoteBot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class BotSearchWindow extends JFrame {

    private JPanel vPanelContent;
    private JTextField vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry;
    private JList mPanelContentPanelMainPanelBotlistListBots;

    /**
     * Create the frame.
     */
    public BotSearchWindow() {
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
        setBounds( 100, 100, 486, 362 );
        vPanelContent = new JPanel();
        vPanelContent.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        vPanelContent.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( vPanelContent );
        
        JScrollPane scrollPane_Main = new JScrollPane();
        vPanelContent.add(scrollPane_Main, BorderLayout.CENTER);
        
        JPanel vPanelContentPanelMain = new JPanel();
        vPanelContentPanelMain.setMinimumSize(new Dimension(400, 300));
        vPanelContentPanelMain.setPreferredSize(new Dimension(400, 300));
        scrollPane_Main.setViewportView(vPanelContentPanelMain);
        vPanelContentPanelMain.setLayout(new BorderLayout(0, 0));
        
        JPanel vPanelContentPanelMainPanelConnectToRegistry = new JPanel();
        vPanelContentPanelMain.add(vPanelContentPanelMainPanelConnectToRegistry, BorderLayout.NORTH);
        vPanelContentPanelMainPanelConnectToRegistry.setLayout(new BorderLayout(0, 0));
        
        JButton vPanelContentPanelMainPanelConnectToRegistryButtonConnectToRegistry = new JButton("Connect to registry");
        vPanelContentPanelMainPanelConnectToRegistryButtonConnectToRegistry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String[] vListOfBots = Core.getInstance().getListOfBots( vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry.getText() );
                
                if( vListOfBots != null ){
                    
                    mPanelContentPanelMainPanelBotlistListBots.setListData( vListOfBots );
                    
                }
                
            }
        });
        vPanelContentPanelMainPanelConnectToRegistry.add(vPanelContentPanelMainPanelConnectToRegistryButtonConnectToRegistry, BorderLayout.EAST);
        
        vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry = new JTextField();
        vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry.setText("//localhost:1099/");
        vPanelContentPanelMainPanelConnectToRegistry.add(vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry, BorderLayout.CENTER);
        vPanelContentPanelMainPanelConnectToRegistryTextfeldRegistry.setColumns(10);
        
        JPanel vPanelContentPanelMainPanelOptions = new JPanel();
        vPanelContentPanelMain.add(vPanelContentPanelMainPanelOptions, BorderLayout.SOUTH);
        vPanelContentPanelMainPanelOptions.setLayout(new BorderLayout(0, 0));
        
        JPanel vPanelContentPanelMainPanelOptionsPanelOptions2 = new JPanel();
        vPanelContentPanelMainPanelOptionsPanelOptions2.setMaximumSize(new Dimension(200, 28));
        vPanelContentPanelMainPanelOptionsPanelOptions2.setPreferredSize(new Dimension(200, 28));
        vPanelContentPanelMainPanelOptionsPanelOptions2.setMinimumSize(new Dimension(200, 28));
        vPanelContentPanelMainPanelOptions.add(vPanelContentPanelMainPanelOptionsPanelOptions2, BorderLayout.EAST);
        vPanelContentPanelMainPanelOptionsPanelOptions2.setLayout(null);
        
        JButton vPanelContentPanelMainPanelOptionsButtonClose = new JButton("Close");
        vPanelContentPanelMainPanelOptionsButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dispose();
                
            }
        });
        vPanelContentPanelMainPanelOptionsButtonClose.setBounds(132, 3, 68, 23);
        vPanelContentPanelMainPanelOptionsPanelOptions2.add(vPanelContentPanelMainPanelOptionsButtonClose);
        
        JButton vPanelContentPanelMainPanelOptionsButtonConnectToBot = new JButton("Connect to bot");
        vPanelContentPanelMainPanelOptionsButtonConnectToBot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                BotFrame vNewBotFrame = new BotFrame();
                RemoteBot vNewRemoteBot = null;
                try {
                    
                    if( mPanelContentPanelMainPanelBotlistListBots.getSelectedValue() != null ){
                        vNewRemoteBot = new RemoteBot( (String) mPanelContentPanelMainPanelBotlistListBots.getSelectedValue(), vNewBotFrame);
                        Botcontrol.getInstance().addBotFrame( vNewBotFrame );
                    }
                    
                } catch ( RemoteException | MalformedURLException | NotBoundException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    vNewRemoteBot.close( false);
                    vNewBotFrame.close( false);
                }
                
                
            }
        });
        vPanelContentPanelMainPanelOptionsButtonConnectToBot.setBounds(10, 3, 112, 23);
        vPanelContentPanelMainPanelOptionsPanelOptions2.add(vPanelContentPanelMainPanelOptionsButtonConnectToBot);

        mPanelContentPanelMainPanelBotlistListBots = new JList();
        
        JScrollPane scrollPane_List = new JScrollPane();
        scrollPane_List.setViewportView(mPanelContentPanelMainPanelBotlistListBots);
        
        JPanel vPanelContentPanelMainPanelBotlist = new JPanel();
        vPanelContentPanelMain.add(vPanelContentPanelMainPanelBotlist, BorderLayout.CENTER);
        vPanelContentPanelMainPanelBotlist.setLayout(new BorderLayout());
        vPanelContentPanelMainPanelBotlist.add(scrollPane_List, BorderLayout.CENTER);
        
    }
}
