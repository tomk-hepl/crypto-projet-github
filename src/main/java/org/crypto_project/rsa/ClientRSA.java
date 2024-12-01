package org.crypto_project.rsa;
import org.crypto_project.utils.KeyStoreRSAUtil;
import org.crypto_project.utils.TCPClient;
import org.crypto_project.utils.Utilities;
import java.nio.file.Paths;
import java.security.PublicKey;

public class ClientRSA {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;

    public static void main(String[] args) throws Exception {
        TCPClient client = Utilities.clientInit("RSA", IP, PORT);

        // Get keystore DB file
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String subRepoPrefix = "/src/main/java/org/crypto_project/rsa/";
        currentDir += subRepoPrefix;
        String keystorePath = Paths.get(currentDir, "keystoreTEST.jks").toString();
        String keystorePassword = "cryptoProject";
        String alias = "mainKey";

        // Get public key
        KeyStoreRSAUtil keystoreUtil = new KeyStoreRSAUtil();
        PublicKey publicKey = keystoreUtil.getPublicKeyFromKeyStore(keystorePath, keystorePassword, alias);

        Utilities.sendRSAMessageWithKeystore(client, publicKey);
        Utilities.closeClient(client);
    }
}
