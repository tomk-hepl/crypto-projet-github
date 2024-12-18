package org.crypto_project.recapitulatif;
import org.crypto_project.utils.DiffieHellmanExchange;
import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.Utilities;

public class Client {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("AES", IP, PORT);

        // Génération de la clé
        String key = new DiffieHellmanExchange().clientExchange(client);

        // Message
        Utilities.sendCoucouMessage(client, key);

        Utilities.closeClient(client);
    }
}
