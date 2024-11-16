package org.crypto_project.triple_des;

import org.crypto_project.utils.AES;
import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.TripleDES;
import org.crypto_project.utils.Utilities;

import java.io.IOException;

public class ServerTripleDES {
    private final static int PORT = 6666;
    private final static String KEY = "9mng65v8jf4lxn93nabf981m";

    public static void main(String[] args) throws Exception {
        TCPServer server = Utilities.serverInit("3DES", PORT);

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                server.listenToNewClient();

                // listen in loop for client messages until it disconnect
                Utilities.readAndDecryptEveryClientMsgUntilItDisconnects(server, new TripleDES(), KEY);
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
