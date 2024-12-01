package org.crypto_project.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IRSA {

    String encryptWithRSA(String message, PublicKey publicKey) throws Exception;
    String decryptWithRSA(String encryptedMessage, PrivateKey privateKey) throws Exception;
    PublicKey getPublicKeyFromKeyStore(String keystorePath, String keystorePassword, String alias) throws Exception;
    PrivateKey getPrivateKeyFromKeyStore(String keystorePath, String keystorePassword, String alias) throws Exception;
}
