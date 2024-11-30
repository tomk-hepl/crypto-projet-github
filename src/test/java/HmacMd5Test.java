import org.crypto_project.utils.HMACHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HmacMd5Test {

    @ParameterizedTest
    @MethodSource("hmacMd5CompareData")
    public void hmacMd5TESTCompare(String payload, String secretKey, String HashToCompare) throws NoSuchAlgorithmException, InvalidKeyException {

        String hmacMD5Algorithm = "HmacMD5"; // for the second method
        HMACHelper helper = new HMACHelper();

        // 1st method
        String result1 = helper.encrypt(payload, secretKey);

        // 2nd method
        String result2 = helper.hmacWithApacheCommons(hmacMD5Algorithm, payload, secretKey);

        // Compare hardcoded values with the 2 algorithms
        Assertions.assertEquals(result1, HashToCompare);
        Assertions.assertEquals(result2, HashToCompare);

        // Compare the 2 methods between themselves...
        Assertions.assertEquals(result1, result2);
    }

    public Stream<Arguments> hmacMd5CompareData() {
        return Stream.of(
                Arguments.of("bonjour", "S€cretK€y", "1384db23d5d8df8b840efcbbfa2a1297")
                //...

        );
    }
}
