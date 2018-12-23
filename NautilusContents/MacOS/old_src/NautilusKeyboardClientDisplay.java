/*----------------------------------------------------------------------------------------
    PROJECT
    -------
    Project Nautilus

    DESCRIPTION
    -----------
    Client application display GUI 

    AUTHOR
    ------
    Ayre Labs (2018)
----------------------------------------------------------------------------------------*/
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NautilusKeyboardClientDisplay extends JPanel implements KeyListener {

    private NautilusKeyListener nautilusKeyListener;

    public NautilusKeyboardClientDisplay() {
        this.setPanelParameters();
        this.showWindowAndAddPanelAsMainDisplay();
    }

    /*----------------------------------------------------------------------------------------
    Public Methods
    ----------------------------------------------------------------------------------------*/
    public void setNautilusKeyListener(NautilusKeyListener keyListenerInQuestion) {
        this.nautilusKeyListener = keyListenerInQuestion;
    }

    /*----------------------------------------------------------------------------------------
    Private Methods
    ----------------------------------------------------------------------------------------*/
    private void setPanelParameters() {
        JLabel labelShowingInstructions = this.createJLabelForApplicationInstructionDisplay();
        this.add(labelShowingInstructions);
        this.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
        this.setPreferredSize(new Dimension(500, 500));
        addKeyListener(this);
    }

    private JLabel createJLabelForApplicationInstructionDisplay() {
        JLabel labelShowingInstructions = new JLabel("Instructions: Type Into This Window");
        labelShowingInstructions.setFont(new Font("Verdana",1,20));
        labelShowingInstructions.setBackground(Color.white);
        return labelShowingInstructions;
    }

    private void showWindowAndAddPanelAsMainDisplay() {
        JFrame mainFrameContainingNautilusKeyboardClient = new JFrame("Nautilus Client");
        mainFrameContainingNautilusKeyboardClient.setLayout(new GridBagLayout());
        mainFrameContainingNautilusKeyboardClient.add(this, new GridBagConstraints());
        mainFrameContainingNautilusKeyboardClient.setSize(700, 300);
        mainFrameContainingNautilusKeyboardClient.setLocationRelativeTo(null);
        mainFrameContainingNautilusKeyboardClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrameContainingNautilusKeyboardClient.setVisible(true);
        mainFrameContainingNautilusKeyboardClient.getContentPane().setBackground(Color.white);
    }

    /*----------------------------------------------------------------------------------------
    KeyListener Implemented Methods
    ----------------------------------------------------------------------------------------*/
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isFunctionKeyEvent(keyEvent)) {
            this.sendFunctionKeyPressEventToKeyListener(keyEvent);
        }
    }

        private boolean isFunctionKeyEvent(KeyEvent keyEvent) {
            return (keyEvent.getKeyCode() >= KeyEvent.VK_F1 && keyEvent.getKeyCode() <= KeyEvent.VK_F12) 
            || keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE || keyEvent.getKeyCode() == KeyEvent.VK_LEFT
            || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT;
        }

        private void sendFunctionKeyPressEventToKeyListener(KeyEvent keyEvent) {
            keyEvent.setKeyCode(keyEvent.getKeyCode()+1000);
            if(this.nautilusKeyListener != null) {
                this.nautilusKeyListener.listenToKeyPressEvent(keyEvent);
            }  
        }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // Nothing
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        this.convertKeyEventToNautilusCodeScheme(keyEvent); 
        if(this.nautilusKeyListener != null) {
            this.nautilusKeyListener.listenToKeyPressEvent(keyEvent);
        }
    }

        private void convertKeyEventToNautilusCodeScheme(KeyEvent keyEvent) {
            int keyCodeInNautilusScheme = (int) keyEvent.getKeyChar();
            keyEvent.setKeyCode(keyCodeInNautilusScheme);
        }

    /*----------------------------------------------------------------------------------------
    JPanel Implemented Methods
    ----------------------------------------------------------------------------------------*/
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}