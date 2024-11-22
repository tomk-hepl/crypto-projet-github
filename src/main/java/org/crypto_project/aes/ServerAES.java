package org.crypto_project.aes;

import org.crypto_project.utils.*;

import java.io.IOException;

public class ServerAES {
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPServer server = Utilities.serverInit("AES", PORT);

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                String key = new DiffieHellmanExchange().serverExchange(server);

                // listen in loop for client messages until it disconnect
                Utilities.readAndDecryptEveryClientMsgUntilItDisconnects(server, new AES(), key);
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
