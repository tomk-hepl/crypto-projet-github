package org.crypto_project.sha1;

import org.crypto_project.utils.SHAUn;
import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.Utilities;

public class ClientSHAUn {


    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("SHA-1", IP, PORT);

        Utilities.scanUserMsgThenHashItAndSendItToServerWhileItIsNotEmpty(client,new SHAUn());

        Utilities.closeClient(client);
    }

}
