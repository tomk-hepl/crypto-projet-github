import org.crypto_project.utils.HMACHelper;
import org.junit.Test;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.*;

public class HmacMd5Test {

    @Test
    public void hmacMd5TESTCompare() throws NoSuchAlgorithmException, InvalidKeyException {

        String hmacMD5Algorithm = "HmacMD5"; // for the second method
        String payload = "bonjour";
        String secretKey = "S€cretK€y";
        String HashToCompare = "1384db23d5d8df8b840efcbbfa2a1297";

        HMACHelper helper = new HMACHelper();

        // 1st method
        String result1 = helper.encrypt(payload, secretKey);

        // 2nd method
        String result2 = helper.hmacWithApacheCommons(hmacMD5Algorithm, payload, secretKey);

        // Compare hardcoded values with the 2 algorithms
        assertEquals(result1, HashToCompare);
        assertEquals(result2, HashToCompare);

        // Compare the 2 methods between themselves...
        assertEquals(result1, result2);
    }
}
