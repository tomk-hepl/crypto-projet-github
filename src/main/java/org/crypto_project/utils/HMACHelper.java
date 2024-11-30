package org.crypto_project.utils;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

// Implementation tutorial : https://www.baeldung.com/java-hmac
public class HMACHelper implements IHMAC {

    String algorithm = "HmacMD5";

    // 1st method (with JDK API)
    public String hash(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException  {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac hmacMD5 = Mac.getInstance(algorithm);
        hmacMD5.init(secretKeySpec);
        byte[] hmacResult = hmacMD5.doFinal(data.getBytes());

        StringBuilder signatureHex = new StringBuilder();
        for (byte b : hmacResult) {
            signatureHex.append(String.format("%02x", b));
        }

        return signatureHex.toString();
    }

    // 2nd method (use of commons-codec dependancy)
    public String hmacWithApacheCommons(String algorithm, String data, String key) {
        return new HmacUtils(algorithm, key).hmacHex(data);
    }

}
