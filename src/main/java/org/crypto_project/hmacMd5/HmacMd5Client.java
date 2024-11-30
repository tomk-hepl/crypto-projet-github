package org.crypto_project.hmacMd5;
import org.crypto_project.utils.*;

/**
 * HMAC - MD5
 * Classe prévue pour montrer l'authentification d'un message avec MD5
 */
public class HmacMd5Client {

    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;
    private final static String secretKey = "S€cretK€y";

    public static void main(String[] args) throws Exception {
        TCPClient newClient = Utilities.clientInit("HmacMD5", IP, PORT);
        Utilities.scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(newClient, new HMACHelper(), secretKey);
        Utilities.closeClient(newClient);
    }
}
