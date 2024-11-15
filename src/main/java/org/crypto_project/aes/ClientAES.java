package org.crypto_project.aes;

import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.TripleDES;

// Symbols meaning
// K=key
// B=Bob's pub key
// A=Alice's pub key
// b=Bob's private key
// a=Alice's private key
// G=Generator/Base
// P=Prime number/Modulus
//
// Exchange steps :
// 1. The server generates G and P
//      G and P are sent to the client
// 2. The server and client generates their Key a and b
// 3. The server does this operation B = G^b mod P and send B to the client.
//      The client does this operation A = G^a mod P and send A to the server
// 4. The server does this operation K = (A^b mod P)
//      The client does this operation K = (B^a mod P)
// => both client and server have the same key K
//
// K = (B^a mod P)
// K = (A^b mod P)
// B = G^b mod P
// A = G^a mod P
//
public class ClientAES {
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
