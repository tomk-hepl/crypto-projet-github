package org.crypto_project.triple_des;

import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.TripleDES;

public class ClientTripleDES {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;
    private final static String MESSAGE = "HELLO";
    private final static String KEY = "9mng65v8jf4lxn93nabf981m";

    public static void main(String[] args) throws Exception {
        System.out.println("Client initialisation...");
        TCPClient client = new TCPClient();

        client.startConnection(IP,PORT);
        System.out.println("Client connected");

        System.out.println("Message to 3DES encryption...");
        String encryptedMessage = TripleDES.encrypt(MESSAGE, KEY);
        System.out.println("Message encrypted: " + encryptedMessage);

        System.out.println("Sending encrypted message...");
        client.sendMessage(encryptedMessage);
        System.out.println("Encrypted message sent");
    }
}
