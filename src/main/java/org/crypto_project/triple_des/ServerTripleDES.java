package org.crypto_project.triple_des;

import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.TripleDES;

public class ServerTripleDES {
    private final static int PORT = 6666;
    private final static String KEY = "9mng65v8jf4lxn93nabf981m";

    public static void main(String[] args) throws Exception {
        System.out.println("3DES Server initialisation...");
        TCPServer server = new TCPServer();

        server.start(PORT);
        System.out.println("The server is listening to port " + PORT + "...");

        System.out.println("Waiting for new messages:");
        boolean done = false;
        while (!done) {
            try {
                // Waiting for new clients
                String encryptedMessage = server.readMessageAndClose();
                String message = TripleDES.decrypt(encryptedMessage, KEY);
                System.out.println("received message: "+encryptedMessage+"\n\t=> decrypted message: "+message);
            } catch (Exception e) {
                server.stop();
                e.printStackTrace();
                done = true;
            }
        }
    }
}
