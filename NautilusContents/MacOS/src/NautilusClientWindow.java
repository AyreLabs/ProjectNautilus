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

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusClientWindow {

    private NautilusWindowKeyListener nautilusWindowKeyListener;
    private NautilusWindowDisplay nautilusWindowDisplay;

    private NautilusClientWindow() {
    	this.nautilusWindowDisplay = NautilusWindowDisplay.createNautilusWindowDisplay();
    	this.nautilusWindowKeyListener = NautilusWindowKeyListener.createNautilusKeyListener();
        this.nautilusWindowDisplay.addNautilusKeyListener(nautilusWindowKeyListener);
    }

    public void setNautilusServerKeyProvider(NautilusServerKeyProvider nautilusServerKeyProvider) {
        this.nautilusWindowKeyListener.setNautilusServerKeyProvider(nautilusServerKeyProvider);
    }

    public static NautilusClientWindow createNautilusClientWindow() {
    	return new NautilusClientWindow();
    }
}