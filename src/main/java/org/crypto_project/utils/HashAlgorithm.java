package org.crypto_project.utils;

public interface HashAlgorithm {

    String hash(final String message) throws  Exception;

    String compare(final  String message) throws Exception;

}
