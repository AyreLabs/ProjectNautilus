/*----------------------------------------------------------------------------------------
    PROJECT
    -------
    Project Nautilus

    DESCRIPTION
    -----------
    Websocket server connection to Nautilus Application Server

    AUTHOR
    ------
    Ayre Labs (2018)
----------------------------------------------------------------------------------------*/
import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.*;
import java.util.ArrayList;

public class NautilusServerConnection implements NautilusKeyListener {
    
    private boolean isConnected;
    private WebSocketClient webSocketToServerConnection;
    private ConcurrentLinkedQueue<KeyEvent> keyEventQueueBuffer;

    /*----------------------------------------------------------------------------------------
    Public Methods
    ----------------------------------------------------------------------------------------*/
    public boolean isConnectedToServer() {
        return this.isConnected;
    }

    public NautilusKeyListener listenerToSendKeyPressesToNautilusServer() {
        return this;
    }

    /*----------------------------------------------------------------------------------------
    Private Methods
    ----------------------------------------------------------------------------------------*/
    private NautilusServerConnection(URI nautilusServerURI) {
        this.isConnected = false;
        this.webSocketToServerConnection = null;
        this.keyEventQueueBuffer = new ConcurrentLinkedQueue<KeyEvent>(); 
        this.connectToWebSocket(nautilusServerURI);
    }

        private void connectToWebSocket(URI nautilusServerURI) {
            try {
                this.attemptConnectionToWebSocket(nautilusServerURI);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        private void attemptConnectionToWebSocket(URI nautilusServerURI) throws Exception {
            this.webSocketToServerConnection = new NautilusWebSocket( nautilusServerURI );
            this.webSocketToServerConnection.connect();
            this.isConnected = true;
            this.startRunningThreadToContinuouslySendKeyEventsToServer();
        }

    private void startRunningThreadToContinuouslySendKeyEventsToServer() {
        Thread threadForSendingKeyEventsToServer = this.createNewThreadForSendingKeyEventsToServer();
        threadForSendingKeyEventsToServer.start();
    }

        private Thread createNewThreadForSendingKeyEventsToServer() {
            return new Thread() {
                public void run() {
                   NautilusServerConnection.this.sendKeyEventsToServerContinuously();
                }
            };
        }

        private void sendKeyEventsToServerContinuously() {
            while(true) {
                this.attemptThreadSleep();
                this.sendKeyEventsToServer();
            }
        }

        private void attemptThreadSleep() {
            try {
                Thread.sleep(500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    private void sendKeyEventsToServer() {
        ArrayList<KeyEvent> keyEventsToSendToServer = this.addKeyEventsToSendList();
        if (keyEventsToSendToServer.size() > 0) {
            String keyPressesMessageToSend = NautilusKeyboardProtocol.keyPressesEventMessageStringWithListOfKeyEvents(keyEventsToSendToServer);
            System.out.printf("asd: %s\n", keyPressesMessageToSend);
            this.webSocketToServerConnection.send(keyPressesMessageToSend);
        }
    }

    private ArrayList<KeyEvent> addKeyEventsToSendList() {
        ArrayList<KeyEvent> keyEventsToSendToServer = new ArrayList<KeyEvent>();
        boolean validEntryWasAdded = true;
        while (validEntryWasAdded) {
            validEntryWasAdded = this.attemptToAddKeyEventToSendList(keyEventsToSendToServer);
        }
        return keyEventsToSendToServer;
    }

    private boolean attemptToAddKeyEventToSendList(ArrayList<KeyEvent> keyEventsToSendToServer) {
        KeyEvent nextKeyEvent = this.keyEventQueueBuffer.poll();
        if (nextKeyEvent != null) {
            keyEventsToSendToServer.add(nextKeyEvent);
            return true;
        } else {
            return false;
        }
    }

    /*----------------------------------------------------------------------------------------
    Static Public Methods
    ----------------------------------------------------------------------------------------*/
    public static NautilusServerConnection attemptToConnectToNautilusServerWithURI(URI nautilusServerURI) {
        return new NautilusServerConnection(nautilusServerURI);
    }

    /*----------------------------------------------------------------------------------------
    Nautilus KeyListener Implemented Methods
    ----------------------------------------------------------------------------------------*/
    public void listenToKeyPressEvent(KeyEvent keyEvent) {
        this.keyEventQueueBuffer.add(keyEvent);
    }
}
