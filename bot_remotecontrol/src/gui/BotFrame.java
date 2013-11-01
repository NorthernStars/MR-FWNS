package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class BotFrame extends JPanel {

    private static final long serialVersionUID = 21785150238115621L;
    
    private JLabel lblNewLabel;
    private JPanel panel_3;
    private boolean mExpanded = false;
    private JButton btnNewButton;
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JPanel panel_1;

    /**
     * Create the panel.
     */
    public BotFrame() {
        setBorder(new LineBorder(new Color(0, 0, 0), 3));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(451, 303));
        setMinimumSize(new Dimension( 200, 45));
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
        
        JButton btnNewButton_1 = new JButton("");
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
        
        btnNewButton = new JButton("");
        
        btnNewButton.setPreferredSize(new Dimension(28, 28));
        btnNewButton.setMinimumSize(new Dimension(28, 28));
        btnNewButton.setMaximumSize(new Dimension(28, 28));
        btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/expand.gif")));
        panel_2.add(btnNewButton);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);
        
        panel_1 = new JPanel();
        tabbedPane.addTab("Wurst", null, panel_1, null);
        
        btnNewButton.addActionListener(new ActionListener() {
            

            public void actionPerformed(ActionEvent e) {
                
                if( mExpanded == true ){

                    btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/expand.gif")));
                    tabbedPane.setPreferredSize( new Dimension( 200, 0 ) );
                    tabbedPane.setMinimumSize(new Dimension( 0, 0 ));
                    setMinimumSize( new Dimension( 200, 45 ));
                    setPreferredSize( new Dimension( 200, 45 ) );
                    mExpanded = false;
                    
                } else {

                    btnNewButton.setIcon(new ImageIcon(BotFrame.class.getResource("/res/contract.gif")));
                    tabbedPane.setPreferredSize( new Dimension( 200, 300 ) );
                    tabbedPane.setMinimumSize(new Dimension( 0, 300 ));
                    setMinimumSize(new Dimension( 200, 300 ));
                    setPreferredSize( new Dimension( 200, 300 ) );
                    mExpanded = true;
                    
                }
                
                revalidate();
                getParent().revalidate();
                
            }
        });

    }
}
