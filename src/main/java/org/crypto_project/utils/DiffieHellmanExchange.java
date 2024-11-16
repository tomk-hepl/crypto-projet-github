package org.crypto_project.utils;

import java.math.BigInteger;

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
// 3. The server does this operation (B = G^b mod P) and send B to the client.
//      The client does this operation (A = G^a mod P) and send A to the server
// 4. The server does this operation K = (A^b mod P)
//      The client does this operation K = (B^a mod P)
// => both client and server have the same key K
//
// A = G^a mod P
// B = G^b mod P
// K = (B^a mod P)
// K = (A^b mod P)
//
public class DiffieHellmanExchange {
    private final static int BASE_LENGTH = 8;
    private final static int PRIME_LENGTH = 6;
    private final static int PRIVATE_KEY_LEN = 6;
    private final static int KEY_LENGTH = 128;

    public static String clientExchange(TCPClient client) throws Exception {
        System.out.println("=== Diffie Hellman exchange start ===");

        // 0. client sends the hello message
        System.out.println("  0. Sending hello...");
        client.sendMessage("Hello");

        // 1. get Prime and Generator generated by the server
        final BigInteger BASE = new BigInteger(client.readMessage());
        final BigInteger PRIME = new BigInteger(client.readMessage());
        System.out.println("  1. Received BASE (G)=\n\t"+BASE+"\n\tand PRIME (P)=\n\t"+PRIME);

        // 2. the client generates its private key
        final BigInteger CLIENT_KEY = Generator.randomPrime(PRIVATE_KEY_LEN);
        System.out.println("  2. Generated client private key=\n\t"+CLIENT_KEY);

        // 3a. the client receives server's public key
        final BigInteger SERVER_PUB = new BigInteger(client.readMessage());
        System.out.println("  3a. Received server public key=\n\t"+SERVER_PUB);

        // 3b. the client does this operation (A = G^a mod P) and send the public key to the client.
        final BigInteger CLIENT_PUB = BASE.modPow(CLIENT_KEY, PRIME);
        client.sendMessage(CLIENT_PUB.toString());
        System.out.println("  3b. Sent client public key=\n\t"+CLIENT_PUB);

        // 4. the server calculates the common key K = (A^b mod P)
        final BigInteger KEY = SERVER_PUB.modPow(CLIENT_KEY, PRIME);
        final String HEX_KEY = String.format("%0"+(KEY_LENGTH/4)+"x", KEY);
        System.out.println("  4. Common key generated=\n\t"+KEY);
        System.out.println("     hex form=\n\t"+ HEX_KEY);
        System.out.println("=== Diffie Hellman exchange end ===");

        return HEX_KEY;
    }

    public static String serverExchange(TCPServer server) throws Exception {
        // 0. client's first message : hello
        server.listenToNewClient();
        String hello = server.readMessage();

        if (!hello.equals("Hello")) {
            throw new Exception("Invalid initial message: " + hello);
        }

        System.out.println("=== Diffie Hellman exchange start ===");
        System.out.println("  0. Hello received from client, Starting Diffie Hellman exchange");

        // 1. generates Prime and Generator
        final BigInteger BASE = Generator.randomInteger(BASE_LENGTH);
        final BigInteger PRIME = Generator.randomPrime(PRIME_LENGTH);

        // send to the client
        server.sendMessage(BASE.toString());
        server.sendMessage(PRIME.toString());
        System.out.println("  1. Sending generated BASE (G)=\n\t"+BASE+"\n\tand PRIME (P)=\n\t"+PRIME);

        // 2. the server generates its private key
        final BigInteger SERVER_KEY = Generator.randomPrime(PRIVATE_KEY_LEN);
        System.out.println("  2. Generated server private key=\n\t"+SERVER_KEY);

        // 3a. the server does this operation (B = G^b mod P) and send the public key to the client.
        final BigInteger SERVER_PUB = BASE.modPow(SERVER_KEY, PRIME);
        server.sendMessage(SERVER_PUB.toString());
        System.out.println("  3a. Sent server public key=\n\t"+SERVER_PUB);

        // 3b. the server receives client's public key
        final BigInteger CLIENT_PUB = new BigInteger(server.readMessage());
        System.out.println("  3b. Received client public key=\n\t"+CLIENT_PUB);

        // 4. the server calculates the common key K = (A^b mod P)
        final BigInteger KEY = CLIENT_PUB.modPow(SERVER_KEY, PRIME);
        final String HEX_KEY = String.format("%0"+(KEY_LENGTH/4)+"x", KEY);
        System.out.println("  4. Common key generated=\n\t"+KEY);
        System.out.println("     hex form=\n\t"+ HEX_KEY);
        System.out.println("=== Diffie Hellman exchange end ===");

        return HEX_KEY;
    }
}
