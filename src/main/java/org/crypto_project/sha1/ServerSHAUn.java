package org.crypto_project.sha1;

import org.crypto_project.utils.SHAUn;
import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.Utilities;

import java.io.IOException;

public class ServerSHAUn {


    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPServer server = Utilities.serverInit("SHA-1", PORT);

        // The server will listen on loop
        boolean done = false;
        while (!done) {
            try {
                server.listenToNewClient();

                // listen in loop for client messages until it disconnect
                Utilities.readAndCompareEveryClientHashedMsgUntilItDisconnects(server, new SHAUn());
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
