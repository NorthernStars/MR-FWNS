package exampleai.brain;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;

import essentials.communication.worlddata_server2008.ReferencePoint.ReferencePointName;

public class testshell extends Shell {
    
    private Combo combo;

    /**
     * Create the shell.
     * @param display
     */
    public testshell( Display display, AI aAi) {
        super( display, SWT.SHELL_TRIM );
                
        combo = new Combo(this, SWT.READ_ONLY);

        for( ReferencePointName vDingName : ReferencePointName.values()  ){

            combo.add( vDingName.toString() );
            
        }
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                  
               // mAI.mI = ReferencePointName.valueOf( combo.getText() );
                
            }
        });
        combo.setBounds(10, 10, 187, 27);
        combo.select(0);
        createContents();
    }

    /**
     * Create contents of the shell.
     */
    protected void createContents() {
        setText( "SWT Application" );
        setSize( 217, 70 );

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
