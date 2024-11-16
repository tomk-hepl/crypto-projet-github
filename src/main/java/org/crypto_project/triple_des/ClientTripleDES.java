package org.crypto_project.triple_des;

import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.TripleDES;
import org.crypto_project.utils.Utilities;

public class ClientTripleDES {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;
    private final static String KEY = "9mng65v8jf4lxn93nabf981m";

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("3DES", IP, PORT);

        Utilities.scanUserMsgThenEncryptItAndSendItToServerWhileItIsNotEmpty(client, new TripleDES(), KEY);

        Utilities.closeClient(client);
    }
}
