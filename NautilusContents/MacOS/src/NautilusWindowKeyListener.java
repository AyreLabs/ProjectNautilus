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

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusWindowKeyListener implements KeyListener {

	private NautilusServerKeyProvider nautilusServerKeyProvider;

	private NautilusWindowKeyListener() {
		this.nautilusServerKeyProvider = null;
	}

	public static NautilusWindowKeyListener createNautilusKeyListener() {
		return new NautilusWindowKeyListener();
	}

	public void setNautilusServerKeyProvider(NautilusServerKeyProvider nautilusServerKeyProvider) {
		this.nautilusServerKeyProvider = nautilusServerKeyProvider;
	}

	@Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.printf("Key: %d\n", keyEvent.getKeyCode());
        if (isFunctionKeyEvent(keyEvent)) {
            this.sendFunctionKeyPressEventToServerKeyProvider(keyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        /* Nothing */
    }

    private boolean isFunctionKeyEvent(KeyEvent keyEvent) {
        return (keyEvent.getKeyCode() >= KeyEvent.VK_F1 && keyEvent.getKeyCode() <= KeyEvent.VK_F12) 
        || keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE || keyEvent.getKeyCode() == KeyEvent.VK_LEFT
        || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT || keyEvent.getKeyCode() == KeyEvent.VK_UP
        || keyEvent.getKeyCode() == KeyEvent.VK_DOWN;
    }

    private void sendFunctionKeyPressEventToServerKeyProvider(KeyEvent keyEvent) {
        this.changeKeyEventCodeForFunctionKeyEncoding(keyEvent);
        if(this.nautilusServerKeyProvider != null) {
            this.sendKeyEventToNautilusServerKeyProvider(keyEvent);
        }  
    }

    private static final int NAUTILUS_FUNCTION_KEY_ENCODING_SHIFT_AMOUNT = 1000;
    private void changeKeyEventCodeForFunctionKeyEncoding(KeyEvent keyEvent) {
    	int originalKeyEventCode = keyEvent.getKeyCode();
    	int keyEventCodeWithFunctionKeyShift = originalKeyEventCode + NAUTILUS_FUNCTION_KEY_ENCODING_SHIFT_AMOUNT;
		keyEvent.setKeyCode(keyEventCodeWithFunctionKeyShift);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        this.convertKeyEventToNautilusCodeScheme(keyEvent); 
        if(this.nautilusServerKeyProvider != null) {
            this.sendKeyEventToNautilusServerKeyProvider(keyEvent);
        }
    }

    private void convertKeyEventToNautilusCodeScheme(KeyEvent keyEvent) {
        int keyCodeInNautilusScheme = (int) keyEvent.getKeyChar();
        keyEvent.setKeyCode(keyCodeInNautilusScheme);
    }

    private void sendKeyEventToNautilusServerKeyProvider(KeyEvent keyEvent) {
    	this.nautilusServerKeyProvider.recieveKeyEvent(keyEvent);
    }
}
