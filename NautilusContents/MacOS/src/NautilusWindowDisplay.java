//----------------------------------------------------------------------------------------
//    PROJECT
//    -------
//    Project Nautilus
//
//    AUTHOR
//    ------
//    Ayre Labs (2018)
//----------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------
// IMPORTS
//----------------------------------------------------------------------------------------
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusWindowDisplay extends JPanel {

    private NautilusWindowDisplay() {
    	this.setPanelParameters();
    	this.displayPanelOnFrame();
    }

    public static NautilusWindowDisplay createNautilusWindowDisplay() {
    	return new NautilusWindowDisplay();
    }

    private void setPanelParameters() {
        JLabel labelShowingInstructions = this.createLabelShowingInstructions();
        this.add(labelShowingInstructions);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(500, 500));
    }

    private JLabel createLabelShowingInstructions() {
        JLabel labelShowingInstructions = new JLabel("Instructions: Type Into This Window");
        labelShowingInstructions.setFont(new Font("Verdana",1,20));
        labelShowingInstructions.setBackground(Color.white);
        return labelShowingInstructions;
    }

    private void displayPanelOnFrame() {
        JFrame nautilusDisplayMainFrame = new JFrame("Nautilus Client");
        this.setFrameLayoutConstraints(nautilusDisplayMainFrame);
        this.setMainFrameViewParameters(nautilusDisplayMainFrame);
        nautilusDisplayMainFrame.setVisible(true);
    }

    public void setFrameLayoutConstraints(JFrame nautilusDisplayMainFrame) {
		nautilusDisplayMainFrame.setLayout(new GridBagLayout());
        nautilusDisplayMainFrame.add(this, new GridBagConstraints());
    }

    private void setMainFrameViewParameters(JFrame nautilusDisplayMainFrame) {
    	nautilusDisplayMainFrame.setSize(700, 300);
        nautilusDisplayMainFrame.setLocationRelativeTo(null);
        nautilusDisplayMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nautilusDisplayMainFrame.getContentPane().setBackground(Color.white);
    }

    public void addNautilusKeyListener(NautilusWindowKeyListener keyListenerInQuestion) {
        this.addKeyListener(keyListenerInQuestion);
    }

    //TO REMOVE
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}