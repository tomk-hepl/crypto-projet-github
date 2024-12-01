package org.crypto_project.rsa;
import org.crypto_project.utils.KeyStoreRSAUtil;
import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.Utilities;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.PrivateKey;

public class ServerRSA {
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPServer server = Utilities.serverInit("RSA", PORT);

        // Get keystore DB file
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String subRepoPrefix = "/src/main/java/org/crypto_project/rsa/";
        currentDir += subRepoPrefix;
        String keystorePath = Paths.get(currentDir, "keystoreTEST.jks").toString();
        String keystorePassword = "cryptoProject";
        String alias = "mainKey";

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                server.listenToNewClient();
                // Get private key
                KeyStoreRSAUtil keystoreUtil = new KeyStoreRSAUtil();
                PrivateKey privateKey = keystoreUtil.getPrivateKeyFromKeyStore(keystorePath, keystorePassword, alias);

                //listen in loop for client messages until it disconnect
                Utilities.readRSAMessageWithKeystore(server, privateKey);
            } catch (IOException e) {
                // If current client disconnected, listen to new client
                System.out.println("Error: " + e.getMessage());
                System.out.println("The server is waiting for new client to port " + PORT + "...");
            } catch (Exception e) {
                server.stop();
                System.out.println("Server stopped: "+e.getMessage());
                done = true;
            }
        }
    }
}
