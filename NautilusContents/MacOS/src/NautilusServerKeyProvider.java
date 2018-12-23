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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusServerKeyProvider {

	private ConcurrentLinkedQueue<KeyEvent> keyEventQueueBuffer;

	private NautilusServerKeyProvider() {
		this.keyEventQueueBuffer = new ConcurrentLinkedQueue<KeyEvent>();
	}

	public static NautilusServerKeyProvider createNautilusServerKeyProvider() {
		return new NautilusServerKeyProvider();
	}

	public void recieveKeyEvent(KeyEvent keyEvent) {
		this.keyEventQueueBuffer.add(keyEvent);
	}

	public String retrieveKeyEventsStringToSendToServer() {
		ArrayList<KeyEvent> keyEventsAsList = this.addKeyEventsToSendList();
		String keyEventsAsString = NautilusKeyboardProtocol.keyPressesEventMessageStringWithListOfKeyEvents(keyEventsAsList);
        System.out.printf("asd: %s\n", keyEventsAsString);
        return keyEventsAsString;
	}

    private ArrayList<KeyEvent> addKeyEventsToSendList() {
        ArrayList<KeyEvent> keyEventsToSendToServer = new ArrayList<KeyEvent>();
        KeyEvent nextKeyEvent = null;
        while ((nextKeyEvent = this.keyEventQueueBuffer.poll()) != null) {
            keyEventsToSendToServer.add(nextKeyEvent);
        }
        return keyEventsToSendToServer;
    }
}