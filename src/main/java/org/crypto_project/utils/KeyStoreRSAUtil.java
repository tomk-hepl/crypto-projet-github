package org.crypto_project.utils;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;
import javax.crypto.Cipher;

public class KeyStoreRSAUtil implements IRSA {


    public String encryptWithRSA(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptWithRSA(String encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public PublicKey getPublicKeyFromKeyStore(String keystorePath, String keystorePassword, String alias) throws Exception {

        KeyStore keystore = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream(keystorePath);
        keystore.load(fis, keystorePassword.toCharArray());

        Certificate cert = keystore.getCertificate(alias);
        if (cert == null) {
            throw new Exception("Alias non trouvé dans le keystore.");
        }
        return cert.getPublicKey();
    }

    public PrivateKey getPrivateKeyFromKeyStore(String keystorePath, String keystorePassword, String alias) throws Exception {

        KeyStore keystore = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream(keystorePath);
        keystore.load(fis, keystorePassword.toCharArray());

        PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, keystorePassword.toCharArray());
        if (privateKey == null) {
            throw new Exception("Clé privée non trouvée dans le keystore.");
        }
        return privateKey;
    }

}
