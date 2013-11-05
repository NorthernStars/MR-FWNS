package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Component;

import javax.swing.JSeparator;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import core.Core;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class Botcontrol {

    private JFrame mFrameBotcontrol;

    private static Botcontrol INSTANCE;
    private JPanel mPanelContent;
    private JPanel mPanelFiller = new JPanel();
    
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
        mFrameBotcontrol.setBounds( 100, 100, 450, 300 );
        mFrameBotcontrol.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JMenuBar vMenuBar = new JMenuBar();
        mFrameBotcontrol.setJMenuBar(vMenuBar);
        
        JMenu vFileMenue = new JMenu("File");
        vMenuBar.add(vFileMenue);
        
        JMenuItem vMenueItemConnectToBot = new JMenuItem("Connect to Bot");
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
        vFileMenue.add(vMenueItemConnectToBot);
        
        JSeparator vMenueItemSeparator = new JSeparator();
        vFileMenue.add(vMenueItemSeparator);
        
        JMenuItem vMenueItemExit = new JMenuItem("Exit");
        vMenueItemExit.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
                mFrameBotcontrol.dispatchEvent( new WindowEvent( mFrameBotcontrol, WindowEvent.WINDOW_CLOSING ));
                mFrameBotcontrol.dispose();
                
            }
        });
        vFileMenue.add(vMenueItemExit);
        mFrameBotcontrol.getContentPane().setLayout(new BorderLayout() );

        mPanelContent = new JPanel();
        
        JScrollPane vScrollPane = new JScrollPane(mPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagLayout gbl_mPanelContent = new GridBagLayout();
        gbl_mPanelContent.columnWidths = new int[]{0};
        gbl_mPanelContent.rowHeights = new int[]{0};
        gbl_mPanelContent.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_mPanelContent.rowWeights = new double[]{Double.MIN_VALUE};
        mPanelContent.setLayout(gbl_mPanelContent);
        mFrameBotcontrol.getContentPane().add(vScrollPane, BorderLayout.CENTER);

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

}
