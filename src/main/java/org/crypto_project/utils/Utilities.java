package org.crypto_project.utils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Utilities {
    private final static Scanner scanner = new Scanner(System.in);

    public static TCPClient clientInit(String algo, String ip, int port) throws IOException {
        System.out.println("=== "+algo+" Client ===");
        TCPClient client = new TCPClient();
        client.startConnection(ip,port);
        System.out.println("Client connected to the port: " + port);
        return client;
    }

    public static TCPServer serverInit(String algo, int port) throws IOException {
        System.out.println("=== "+algo+" Client ===");
        TCPServer server = new TCPServer();
        server.start(port);
        System.out.println("The server is waiting for new client to port " + port + "...");
        return server;
    }

    public static void scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(TCPClient client, IAlgorithm algo, String key) throws Exception {
        String message;
        do {
            // ask for message
            System.out.print("Enter a message: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                continue;
            }

            // encrypt message
            String encryptedMessage = algo.encrypt(message, key);
            System.out.println("Message "+ message +" encrypted");
            // send message
            client.sendMessage(encryptedMessage);
            System.out.println("Encrypted message sent: " + encryptedMessage);
        } while (!message.isEmpty());
    }

    public static void readAndDecryptEveryClientMsgUntilItDisconnects(TCPServer server, IAlgorithm algo, String key) throws Exception {
        String encryptedMessage;
        while ((encryptedMessage = server.readMessage()) != null) {
            String message = algo.decrypt(encryptedMessage, key);
            System.out.println("received message: "+encryptedMessage+"\n\t=> decrypted message: "+message);
        }
        System.out.println("=== Client closed ===\n");
    }

    public static void scanUserMsgThenHashItAndSendItToServerWhileItIsNotEmpty(TCPClient client, HashAlgorithm algo) throws Exception
    {

        String message;
        do {
            //ask for message
            System.out.print("Enter a message: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                continue;
            }

            //Hash message
            String hashedMessage = algo.hash(message);
            System.out.println("Message '" + message + "' hashed");
            //send message
            System.out.println("Message hashed : " + hashedMessage);
            String messageWithHash = message + "==" + hashedMessage;
            client.sendMessage(messageWithHash);
            System.out.println("Message hashed with  == send =>  " + messageWithHash);




        } while(!message.isEmpty());

    }

    public static void readAndCompareEveryClientHashedMsgUntilItDisconnects(TCPServer server,HashAlgorithm algo) throws Exception
    {

        String receivedMessage;

        while ((receivedMessage = server.readMessage()) != null) {
            String result = algo.compare(receivedMessage);

            String cleanResult = result.replace("Message valid : ", "");

            System.out.println("Original message: " + cleanResult);

            // Hashage avec Apache Commons
            String apacheHash = algo.hashWithApacheCommons(cleanResult);
            System.out.println("Apache hash : " + apacheHash);
        }
        System.out.println("=== Client closed ===\n");

    }


    public static void scanUserMsgThenSignAndSendToServerWhileItIsNotEmpty(TCPClient client, RSAHashAlgorithm algo, PrivateKey privateKey) throws Exception {
        String message;
        do {
            // //ask for message
            System.out.print("Enter a message: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                continue;
            }

            // Sign the message (hash + RSA)
            String signature = algo.rsahash(message,privateKey);
            System.out.println("Message signed with RSA: " + signature);

            // Add signature to message
            String messageWithSignature = message + "==" + signature;

            // send the signed message
            client.sendMessage(messageWithSignature);
            System.out.println("Signed message sent: " + messageWithSignature);

        } while (!message.isEmpty());
    }


    public static void readAndVerifyEveryClientSignedMsgUntilItDisconnects(TCPServer server, RSAHashAlgorithm algo, PublicKey publicKey) throws Exception {
        String receivedMessage;
        while ((receivedMessage = server.readMessage()) != null) {
            // Verify the signature
            String result = algo.rsacompare(receivedMessage,publicKey);

            System.out.println(result);
        }
        System.out.println("=== Client closed ===\n");
    }

    // Method for loading a public key from a string
    public static PublicKey loadPublicKey(String publicKeyString) throws Exception {

        // Remove spaces and line breaks
        publicKeyString = publicKeyString.replaceAll("[\\s\\r\\n]+", "");
        System.out.println("Cleaned public key: " + publicKeyString);
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes); //Returns the key bytes,
        // encoded according to the X.509 standard.
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");//KeyFactory, qui est utilisé pour générer des clés
        //à partir de la spécififcation RSA
        return keyFactory.generatePublic(spec);
    }





    public static void closeClient(TCPClient client) throws IOException {
        client.stopConnection();
        System.out.println("Client closed");
    }
}