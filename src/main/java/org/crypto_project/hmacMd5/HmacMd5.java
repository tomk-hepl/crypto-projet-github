package org.crypto_project.hmacMd5;
import org.crypto_project.utils.*;

/**
 * HMAC - MD5
 * Classe prévue pour montrer l'authentification d'un message avec MD5
 */
public class HmacMd5 {

    private final static String localhost = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {

        TCPClient client = Utilities.clientInit("AES", localhost, PORT);
        // TODO
        Utilities.closeClient(client);
    }
}
