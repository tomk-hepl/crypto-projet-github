package org.crypto_project.utils;

public interface ISymmetricAlgorithm {
    String encrypt(final String message, String key) throws Exception;

    String decrypt(final String secret, String key) throws Exception;
}