package org.crypto_project.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public interface IEncryptionTest {
    static Stream<Arguments> wrongSizeKey() {
        return null;
    }

    static Stream<Arguments> rightSizeKey() {
        return null;
    }

    static Stream<Arguments> decryptionTestData() {
        return null;
    }

    static Stream<Arguments> encryptionTestData() {
        return null;
    }
}
