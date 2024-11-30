package org.crypto_project.utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.crypto.BadPaddingException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractEncryptionTest {

    public abstract IEncryption getAlgorithm(); // Method to be implemented by subclasses

    public abstract Stream<Arguments> wrongSizeKey();

    public abstract Stream<Arguments> rightSizeKey();

    public abstract Stream<Arguments> decryptionTestData();

    public abstract Stream<Arguments> encryptionTestData();

    @ParameterizedTest
    @MethodSource("wrongSizeKey")
    void wrongSizeKey(String key) {
        assertThrows(IllegalArgumentException.class, () -> getAlgorithm().encrypt("Hello", key));
        assertThrows(IllegalArgumentException.class, () -> getAlgorithm().decrypt("Hello", key));
    }

    @ParameterizedTest
    @MethodSource("rightSizeKey")
    void rightSizeKeyDecrypte(String encryption, String key) {
        try {
            getAlgorithm().decrypt(encryption, key);
        } catch (BadPaddingException _) {} // success because it's not a key size error
        catch (Exception e) {
            fail("Unexpected this exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("encryptionTestData")
    void testEncrypt(String plaintext, String key, String expected) {
        try {
            String encrypted = getAlgorithm().encrypt(plaintext, key);
            assertEquals(expected, encrypted, "Encrypted text does not match the expected output");
        } catch (Exception e) {
            fail("Encryption failed with exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("decryptionTestData")
    void testDecrypt(String encrypted, String key, String expected) {
        try {
            String decrypted = getAlgorithm().decrypt(encrypted, key);
            assertEquals(expected, decrypted, "Decrypted text does not match the original plaintext");
        } catch (Exception e) {
            fail("Decryption failed with exception: " + e.getMessage());
        }
    }
}
