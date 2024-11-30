package org.crypto_project.utils;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AESTest extends AbstractEncryptionTest {
    @Override
    public IEncryption getAlgorithm() {
        return new AES();
    }

    public Stream<Arguments> wrongSizeKey() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("hello"),
                Arguments.of("9mng65v8jf4lxn93ddddddd"),
                Arguments.of("9mng65v8jf4lxn93ddddddddf"),
                Arguments.of("9mng65v8jf4lxn93ddddddddddddddd"),
                Arguments.of("9mng65v8jf4lxn93ddddddddddddddddo"),
                Arguments.of("101010101156418345316831765")
        );
    }

    public Stream<Arguments> rightSizeKey() {
        return Stream.of(
                Arguments.of("+zdzVz5BBnhIDv6kVBuqqQ==", "ABCDEfghijkLmnop"),
                Arguments.of("+zdzVz5BBnhIDv6kVBuqqQ==", "9mng65v8jf4lxn93"),
                Arguments.of("+zdzVz5BBnhIDv6kVBuqqQ==", "9mng65v8jf4lxn93dddddddd"),
                Arguments.of("+zdzVz5BBnhIDv6kVBuqqQ==", "9mng65v8jf4lxn93dddddddddddddddd"),
                Arguments.of("+zdzVz5BBnhIDv6kVBuqqQ==", "aaaaaaaaaaaaaaaa")
        );
    }

    public Stream<Arguments> encryptionTestData() {
        return Stream.of(
                Arguments.of("HelloWorld123", "1234567890123456", "URaJPXifgqeloZiWqKStvA=="),
                Arguments.of("I'm testing the relevance of this Algorithm", "ABCDEFGHIJKLMNOP", "BpDwsbChGgnOurfVrvPlVeXri9aGcdPtYwmyYw5V9znKtMQwLEiyEBioeuHaBLfO"),
                Arguments.of("Here is the secret", "0987654321098765", "f9Vp6XDbqRypAvI4ceVLLMWRP0OzAZucNCV8WF9t8Ok="),
                Arguments.of("Thank you OpenAI for the tests!", "QWERTYUIOPASDFGH", "/gqYNqyTYNqubQtoSRVUCCEc0BbK5DMQiy9CEjFNI/Q=")
        );
    }

    public Stream<Arguments> decryptionTestData() {
        return Stream.of(
                Arguments.of("URaJPXifgqeloZiWqKStvA==", "1234567890123456", "HelloWorld123"),
                Arguments.of("BpDwsbChGgnOurfVrvPlVeXri9aGcdPtYwmyYw5V9znKtMQwLEiyEBioeuHaBLfO", "ABCDEFGHIJKLMNOP", "I'm testing the relevance of this Algorithm"),
                Arguments.of("f9Vp6XDbqRypAvI4ceVLLMWRP0OzAZucNCV8WF9t8Ok=", "0987654321098765", "Here is the secret"),
                Arguments.of("/gqYNqyTYNqubQtoSRVUCCEc0BbK5DMQiy9CEjFNI/Q=", "QWERTYUIOPASDFGH", "Thank you OpenAI for the tests!")
        );
    }
}