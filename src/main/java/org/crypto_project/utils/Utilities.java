package org.crypto_project.utils;

import java.io.IOException;
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

    public static void closeClient(TCPClient client) throws IOException {
        client.stopConnection();
        System.out.println("Client closed");
    }
}
