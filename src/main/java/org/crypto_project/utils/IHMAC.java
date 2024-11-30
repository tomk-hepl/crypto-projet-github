package org.crypto_project.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface IHMAC {

    String hash(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException;
    String hmacWithApacheCommons(String algorithm, String data, String key);
}