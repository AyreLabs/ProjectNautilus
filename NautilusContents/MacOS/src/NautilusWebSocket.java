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

//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
public class NautilusWebSocket extends WebSocketClient {

    public NautilusWebSocket(URI nautilusServerURI) {
        super(nautilusServerURI);
    }

    @Override
    public void onMessage( String message ) {
        System.out.printf("Message from server: %s\n", message);
    }

    @Override
    public void onOpen( ServerHandshake handshake ) {
        System.out.printf("Handshake with server successful\n");
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        System.out.printf("Connection closed with server because: %s\n", reason);
    }

    @Override
    public void onError( Exception exception ) {
        exception.printStackTrace();
    }
}