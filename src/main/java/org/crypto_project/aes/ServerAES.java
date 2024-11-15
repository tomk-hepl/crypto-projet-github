package org.crypto_project.aes;

import org.crypto_project.utils.TCPServer;
import org.crypto_project.utils.TripleDES;

import java.util.Objects;

public class ServerAES {
    private final static int PORT = 6666;
    private final static String KEY = "9mng65v8jf4lxn93nabf981m";

    public static void main(String[] args) throws Exception {
        System.out.println("AES Server initialisation...");
        TCPServer server = new TCPServer();

        server.start(PORT);
        System.out.println("The server is listening to port " + PORT + "...");

        System.out.println("Waiting for new messages:");
        boolean done = false;
        while (!done) {
            try {
                // Waiting for new clients
                String hello = server.readMessageAndClose();

                if (!Objects.equals(hello, "Hello")) {
                    throw new Exception("Incorrect Hello message");
                }

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
