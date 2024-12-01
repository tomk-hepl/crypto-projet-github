package org.crypto_project.bonus;

import org.crypto_project.utils.AES;
import org.crypto_project.utils.DiffieHellmanExchange;
import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.Utilities;

public class Client {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("AES", IP, PORT);

        String key = new DiffieHellmanExchange().clientExchange(client);

        Utilities.scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(client, new AES(), key);

        Utilities.closeClient(client);
    }
}
