package org.crypto_project.utils;
import java.io.IOException;
import java.security.*;
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
        System.out.println("=== "+algo+" Server ===");
        TCPServer server = new TCPServer();
        server.start(port);
        System.out.println("The server is waiting for new client to port " + port + "...");
        return server;
    }

    public static void scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(TCPClient client, IEncryption algo, String key) throws Exception {
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

    public static void scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(TCPClient client, IHMAC algo, String key) throws Exception {
        String message;
        do {
            // ask for message
            System.out.print("Enter a message: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                continue;
            }

            // encrypt message
            String encryptedMessage = algo.hash(message, key);
            System.out.println("Message "+ message +" encrypted");
            // send message
            client.sendMessage(encryptedMessage);
            System.out.println("Encrypted message sent: " + encryptedMessage);
        } while (!message.isEmpty());
    }

    public static void readAndDecryptEveryClientMsgUntilItDisconnects(TCPServer server, IEncryption algo, String key) throws Exception {
        String encryptedMessage;
        while ((encryptedMessage = server.readMessage()) != null) {
            String message = algo.decrypt(encryptedMessage, key);
            System.out.println("received message: "+encryptedMessage+"\n\t=> decrypted message: "+message);
        }
        System.out.println("=== Client closed ===\n");
    }

    public static void readAndHashEveryClientMsgUntilItDisconnects(TCPServer server, IHMAC algo, String key) throws Exception {
        String hashedMessage;
        while ((hashedMessage = server.readMessage()) != null) {
            String message = algo.hash(hashedMessage, key);
            System.out.println("received message: "+hashedMessage+"\n\t=> decrypted message: "+message);
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

    // -------------------------------------------  RECAP METHOD ------------------------------------------- //

    public static void sendCoucouMessage(TCPClient client, String key) throws Exception {
        String message;
        do {

            // ask for message
            System.out.print("Enter a message: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                continue;
            }

            // SHA with RSA (generate)
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Taille de la clé
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            // Sign the message (hash + RSA)
            String signature =  new SHAUnRSA().rsahash(message,privateKey);

            // Use of AES (cipher)
            AES aes = new AES();
            String cipher = aes.encrypt(message, key);

            System.out.println("Message "+ message +" encrypted");

            // send message
            client.sendMessage(cipher);
            System.out.println("Cipher sent: " + cipher);
            client.sendMessage(signature);
            System.out.println("Signature sent: " + signature);
            client.sendMessage(publicKeyString);
            System.out.println("Public Key String sent: " + publicKeyString);

        } while (!message.isEmpty());
    }

    public static void readCoucouMessage(TCPServer server, String key) throws Exception {

        String encryptedMessage;
        while ((encryptedMessage = server.readMessage()) != null) {

            System.out.println("encryptedMessage : " + encryptedMessage);

            String signature = server.readMessage();
            System.out.println("signature : " + signature);

            String publicKey = server.readMessage();
            System.out.println("publicKey : " + publicKey);

            // AES to decrypt the cipher
            AES aes = new AES();
            String msgDecrpyt = aes.decrypt(encryptedMessage, key);
            System.out.println("msgDecrpyt : " + msgDecrpyt);

            // SHA to hash message
            String hash = new SHAUn().hash(msgDecrpyt);
            System.out.println("hash : " + hash);

            SHAUnRSA shaRsa = new SHAUnRSA();
            PublicKey publicKeyD = loadPublicKey(publicKey);
            System.out.println("publicKeyD : " + publicKeyD);

            // RSA - Check signature
            boolean compareOk = shaRsa.rsaCompareWithHash(hash, signature, publicKeyD);

            if(!compareOk) {
                throw new Exception("compareOk - Hash verification failed");
            }

            System.out.println("message recu");

        }

        System.out.println("=== Client closed ===\n");
    }




    // -------------------------------------------  RECAP METHOD ------------------------------------------- //

}