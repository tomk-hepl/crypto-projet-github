package org.crypto_project.sha1rsa;

import org.crypto_project.utils.SHAUnRSA;
import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.Utilities;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.crypto_project.utils.Utilities.loadPublicKey;

public class ServerSHAUnRSA {

    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPServer server = Utilities.serverInit("SHA1withRSA", PORT);

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                server.listenToNewClient();

                // Receive the client's public key
                String publicKeyString = server.readMessage();  // read it
                System.out.println("Received public key: " + publicKeyString);  // Vérifiez la clé publique reçue
                PublicKey publicKey = loadPublicKey(publicKeyString); // Convert the key (String) to PublicKey




                // listen in loop for client messages until it disconnect
                Utilities.readAndVerifyEveryClientSignedMsgUntilItDisconnects(server, new SHAUnRSA(),publicKey);
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
