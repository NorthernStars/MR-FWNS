package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSeparator;

import java.awt.FlowLayout;

import net.miginfocom.swing.MigLayout;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Botcontrol {

    private JFrame mFrameBotcontrol;

    /**
     * Launch the application.
     */
    public static void startGUI( ) {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                try {
                    Botcontrol window = new Botcontrol();
                    window.mFrameBotcontrol.setVisible( true );
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the application.
     */
    public Botcontrol() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mFrameBotcontrol = new JFrame();
        mFrameBotcontrol.setTitle("Botcontrol");
        mFrameBotcontrol.setBounds( 100, 100, 450, 300 );
        mFrameBotcontrol.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JMenuBar vMenuBar = new JMenuBar();
        mFrameBotcontrol.setJMenuBar(vMenuBar);
        
        JMenu vFileMenue = new JMenu("File");
        vMenuBar.add(vFileMenue);
        
        JMenuItem vMenueItemConnectToBot = new JMenuItem("Connect to Bot");
        vMenueItemConnectToBot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                EventQueue.invokeLater( new Runnable() {
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
        vFileMenue.add(vMenueItemConnectToBot);
        
        JSeparator vMenueItemSeparator = new JSeparator();
        vFileMenue.add(vMenueItemSeparator);
        
        JMenuItem vMenueItemExit = new JMenuItem("Exit");
        vMenueItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                System.exit( 0 );
                
            }
        });
        vFileMenue.add(vMenueItemExit);

        JPanel vPanelContent = new JPanel();
        
        JScrollPane vScrollPane = new JScrollPane(vPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        vPanelContent.setLayout(new BoxLayout(vPanelContent, BoxLayout.Y_AXIS));
        mFrameBotcontrol.getContentPane().add(vScrollPane, BorderLayout.CENTER);
        
        
    }

}
