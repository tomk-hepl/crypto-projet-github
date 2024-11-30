package org.crypto_project.sha1rsa;


import org.crypto_project.utils.SHAUnRSA;
import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.Utilities;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class ClientSHAUnRSA {

    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Taille de la clé
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();



        TCPClient client = Utilities.clientInit("SHA1withRSA", IP, PORT);

        //send the public key
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Sending public key: " + publicKeyString);
        client.sendMessage(publicKeyString.toString());

        SHAUnRSA shaUnRSA = new SHAUnRSA();

        Utilities.scanUserMsgThenSignAndSendToServerWhileItIsNotEmpty(client,shaUnRSA,privateKey);

        Utilities.closeClient(client);
    }
}
