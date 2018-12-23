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


//----------------------------------------------------------------------------------------
// CLASS DEFINITION
//----------------------------------------------------------------------------------------
class NautilusClientApplication {

    public static void main(String[] commandLineArguments) {
        boolean hasParameterForURIConnectionToServer = commandLineArguments.length > 0;
        if (hasParameterForURIConnectionToServer) {
            NautilusClientApplication.createApplicationWithURI(commandLineArguments[0]);
        } else {
            System.out.println("Usage: Nautilus <URI for Nautilus Server>");
        }
    }

    private static void createApplicationWithURI(String uriString) {
        try{
            NautilusClientApplication.setupAndRunNautilusApplicationWithURI(new URI(uriString));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void setupAndRunNautilusApplicationWithURI(URI nautilusServerURI) throws Exception {
        NautilusClientApplication nautilusClientApplication = new NautilusClientApplication(nautilusServerURI);
        nautilusClientApplication.runApplication();
    }

    private URI nautilusServerURI;

    public NautilusClientApplication(URI nautilusServerURI) { 
        this.nautilusServerURI = nautilusServerURI;
    }

    public void runApplication() throws URISyntaxException {
        NautilusServerConnection nautilusServerConnection = NautilusServerConnection.attemptToConnectToNautilusServerWithURI(this.nautilusServerURI);
        if (nautilusServerConnection.isConnectedToServer()) {
            this.createAndRunApplicationWithServerConnection(nautilusServerConnection);
        } else {
            System.out.printf("Failed to connect to Nautilus at: %s\n", nautilusServerURI.toString());
        }
    }

    private void createAndRunApplicationWithServerConnection(NautilusServerConnection nautilusServerConnection){
        NautilusServerKeyProvider newNautilusServerKeyProvider = NautilusServerKeyProvider.createNautilusServerKeyProvider();
        NautilusClientWindow newNautilusClientWindow = NautilusClientWindow.createNautilusClientWindow();
        newNautilusClientWindow.setNautilusServerKeyProvider(newNautilusServerKeyProvider);
        nautilusServerConnection.setNautilusServerKeyProvider(newNautilusServerKeyProvider);
    }
}