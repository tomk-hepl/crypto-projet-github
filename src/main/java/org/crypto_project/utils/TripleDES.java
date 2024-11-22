package org.crypto_project.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TripleDES implements IEncryption {
	private static final String CIPHER_TYPE = "DESede/ECB/PKCS5Padding";
	private static final String CIPHER_NAME = "DESede";

    public String encrypt(String message, String key) throws Exception {
		checkKey(key);

		// key
        byte[] secretKey = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, CIPHER_NAME);

        // message to bytes
        byte[] secretMessagesBytes = message.getBytes(StandardCharsets.UTF_8);

		// vector init
        Cipher encryptCipher = Cipher.getInstance(CIPHER_TYPE);
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		// encrypte
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessagesBytes);

		// to base64
        return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    }

	public String decrypt(String secreteMessage, String key) throws Exception {
		checkKey(key);

		// key
		byte[] secretKey = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, CIPHER_NAME);

		// decode from base64
		byte[] decodedMessageBytes = Base64.getDecoder().decode(secreteMessage);

		// initialize decryption cipher
		Cipher decryptCipher = Cipher.getInstance(CIPHER_TYPE);
		decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		// decrypt
		byte[] decryptedMessageBytes = decryptCipher.doFinal(decodedMessageBytes);
		return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
	}

	private void checkKey(String key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		} else if (key.isEmpty()) {
			throw new IllegalArgumentException("Key is empty");
		} else if (key.length() != 24) {
			throw new IllegalArgumentException("Wrong length of key");
		}
	}
}