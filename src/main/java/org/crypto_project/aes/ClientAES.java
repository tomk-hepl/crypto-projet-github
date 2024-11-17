package org.crypto_project.aes;

import org.crypto_project.utils.*;
import org.crypto_project.utils.Utilities;

public class ClientAES {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    private final static int x = 1;

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("AES", IP, PORT);

        String key = DiffieHellmanExchange.clientExchange(client);

        Utilities.scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(client, new AES(), key);

        Utilities.closeClient(client);
    }
}
