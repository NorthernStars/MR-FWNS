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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BotSearchWindow extends JFrame {

    private JPanel contentPane;
    private JTextField txtlocalhost;
    private JList<String> list;

    /**
     * Create the frame.
     */
    public BotSearchWindow() {
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
        setBounds( 100, 100, 486, 362 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( contentPane );
        
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(400, 300));
        panel.setPreferredSize(new Dimension(400, 300));
        scrollPane.setViewportView(panel);
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel panelConnectToRegistry = new JPanel();
        panel.add(panelConnectToRegistry, BorderLayout.NORTH);
        panelConnectToRegistry.setLayout(new BorderLayout(0, 0));
        
        JButton btnConnectToRegistry = new JButton("Connect to registry");
        btnConnectToRegistry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String[] vListOfBots = Core.getInstance().getListOfBots( txtlocalhost.getText() );
                
                if( vListOfBots != null ){
                    
                    list.setListData( vListOfBots );
                    
                }
                
            }
        });
        panelConnectToRegistry.add(btnConnectToRegistry, BorderLayout.EAST);
        
        txtlocalhost = new JTextField();
        txtlocalhost.setText("//localhost:1099/");
        panelConnectToRegistry.add(txtlocalhost, BorderLayout.CENTER);
        txtlocalhost.setColumns(10);
        
        JPanel panelOptions = new JPanel();
        panel.add(panelOptions, BorderLayout.SOUTH);
        panelOptions.setLayout(new BorderLayout(0, 0));
        
        JPanel panelOptions2 = new JPanel();
        panelOptions2.setMaximumSize(new Dimension(200, 28));
        panelOptions2.setPreferredSize(new Dimension(200, 28));
        panelOptions2.setMinimumSize(new Dimension(200, 28));
        panelOptions.add(panelOptions2, BorderLayout.EAST);
        panelOptions2.setLayout(null);
        
        JButton btnClose = new JButton("Close");
        btnClose.setBounds(132, 3, 68, 23);
        panelOptions2.add(btnClose);
        
        JButton btnConnectToBot = new JButton("Connect to bot");
        btnConnectToBot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Botcontrol.getInstance().addBotFrame( new BotFrame() );
                
            }
        });
        btnConnectToBot.setBounds(10, 3, 112, 23);
        panelOptions2.add(btnConnectToBot);

        list = new JList<String>();
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setViewportView(list);
        
        JPanel panel_4 = new JPanel();
        panel.add(panel_4, BorderLayout.CENTER);
        panel_4.setLayout(new BorderLayout());
        panel_4.add(scrollPane_1, BorderLayout.CENTER);
        
    }
}
