package org.crypto_project.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RSAHashAlgorithm
{
    String rsahash(final String message, PrivateKey privatekey) throws  Exception;

    String rsacompare(final  String message, PublicKey publicKey) throws Exception;
}
