package org.crypto_project.utils;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TripleDESTest extends AbstractEncryptionTest implements IEncryptionTest {
    @Override
    protected IEncryption getAlgorithm() {
        return new TripleDES();
    }

    protected Stream<Arguments> wrongSizeKey() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("hello"),
                Arguments.of("9mng65v8jf4lxn93nabf981mhfgyyijd"),
                Arguments.of("101010101156418345316831765")
        );
    }

    protected Stream<Arguments> rightSizeKey() {
        return Stream.of(
                Arguments.of("+bBr7/FcxYA=", "ABCDEfghijkLmnopkrstuvyx"),
                Arguments.of("+bBr7/FcxYA=", "9mng65v8jf4lxn93nabf981m"),
                Arguments.of("+bBr7/FcxYA=", "aaaaaaaaaaaaaaaaaaaaaaaa")
        );
    }

    protected Stream<Arguments> encryptionTestData() {
        return Stream.of(
                Arguments.of("HelloWorld123", "123456789012345678901234", "3icuyJ8eHcBZBo8X7Pc4eQ=="),
                Arguments.of("I'm testing the relevance of this Algorithm", "ABCDEFGHIJKLMNOPQRSTUVWX", "O6PY3xulkyh9i3mfH0MjWK4U21Lt6djNQ3Smb7cyL7poC6ll3vzrugr0RBVvmH2y"),
                Arguments.of("Here is the secret", "098765432109876543210987", "LxMzB/qmzbUN1w3ijy5QrTjAUCK5kCed"),
                Arguments.of("Thank you OpenAI for the tests!", "QWERTYUIOPASDFGHJKLZXCVB", "sCjagZxZJOEaenKesLPh3/aZxkIUNBsrzptHvu16kEk=")
        );
    }

    protected Stream<Arguments> decryptionTestData() {
        return Stream.of(
                Arguments.of("3icuyJ8eHcBZBo8X7Pc4eQ==", "123456789012345678901234", "HelloWorld123"),
                Arguments.of("O6PY3xulkyh9i3mfH0MjWK4U21Lt6djNQ3Smb7cyL7poC6ll3vzrugr0RBVvmH2y", "ABCDEFGHIJKLMNOPQRSTUVWX", "I'm testing the relevance of this Algorithm"),
                Arguments.of("LxMzB/qmzbUN1w3ijy5QrTjAUCK5kCed", "098765432109876543210987", "Here is the secret"),
                Arguments.of("sCjagZxZJOEaenKesLPh3/aZxkIUNBsrzptHvu16kEk=", "QWERTYUIOPASDFGHJKLZXCVB", "Thank you OpenAI for the tests!")
        );
    }
}