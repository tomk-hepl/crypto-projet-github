package org.crypto_project.hmacMd5;

import org.crypto_project.utils.*;

import java.io.IOException;

public class HmacMd5Server {

    private final static int PORT = 6666;
    private final static String secretKey = "S€cretK€y";


    public static void main(String[] args) throws Exception {

        TCPServer server = Utilities.serverInit("HmacMD5", PORT);

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                server.listenToNewClient();

                // listen in loop for client messages until it disconnect
               Utilities.readAndHashEveryClientMsgUntilItDisconnects(server, new HMACHelper(), secretKey);
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
