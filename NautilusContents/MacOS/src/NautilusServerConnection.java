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

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusServerConnection {
    
    private boolean isConnected;
    private WebSocketClient webSocketToServerConnection;
    private NautilusServerKeyProvider nautilusServerKeyProvider;

    /*----------------------------------------------------------------------------------------
    Public Methods
    ----------------------------------------------------------------------------------------*/
    public boolean isConnectedToServer() {
        return this.isConnected;
    }

    /*----------------------------------------------------------------------------------------
    Private Methods
    ----------------------------------------------------------------------------------------*/
    private NautilusServerConnection(URI nautilusServerURI) {
        this.isConnected = false;
        this.webSocketToServerConnection = null;
        this.connectToWebSocket(nautilusServerURI);
        this.nautilusServerKeyProvider = null;
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

    static private final int SLEEP_TIME_BETWEEN_SENDING_KEY_PRESSES = 500;
    private void attemptThreadSleep() {
        try {
            Thread.sleep(SLEEP_TIME_BETWEEN_SENDING_KEY_PRESSES);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void sendKeyEventsToServer() {
        if(this.nautilusServerKeyProvider != null) {
            this.sendKeyEventsToServerIfTheyExist();
        }
    }

    private void sendKeyEventsToServerIfTheyExist() {
        String keyEventsStringToSendToServer = this.nautilusServerKeyProvider.retrieveKeyEventsStringToSendToServer();
        if(!(keyEventsStringToSendToServer.equals(""))) {
            this.webSocketToServerConnection.send(keyEventsStringToSendToServer);
        }
    }

    public void setNautilusServerKeyProvider(NautilusServerKeyProvider nautilusServerKeyProvider) {
        this.nautilusServerKeyProvider = nautilusServerKeyProvider;
    }

    public static NautilusServerConnection attemptToConnectToNautilusServerWithURI(URI nautilusServerURI) {
        return new NautilusServerConnection(nautilusServerURI);
    }
}
