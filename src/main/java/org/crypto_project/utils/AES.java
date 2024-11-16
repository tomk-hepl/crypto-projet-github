package org.crypto_project.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES implements IAlgorithm {
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5PADDING";
	private static final String CIPHER_NAME = "AES";
	private static final IvParameterSpec VECTOR = new IvParameterSpec("1234567890123456".getBytes());
	
    public String encrypt(String message, String key) throws Exception {
		// key
        byte[] secretKey = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, CIPHER_NAME);

        // message to bytes
        byte[] secretMessagesBytes = message.getBytes(StandardCharsets.UTF_8);

		// vector init
        Cipher encryptCipher = Cipher.getInstance(CIPHER_TYPE);
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, VECTOR);


		// encrypt
		byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessagesBytes);

		// to base64
		return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    }
	
	public String decrypt(String secreteMessage, String key) throws Exception {
		// key
		byte[] secretKey = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, CIPHER_NAME);

		// decode from base64
		byte[] decodedMessageBytes = Base64.getDecoder().decode(secreteMessage);

		// vector init
		Cipher decryptCipher = Cipher.getInstance(CIPHER_TYPE);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, VECTOR);

		// decrypt
		byte[] decryptedMessageBytes = decryptCipher.doFinal(decodedMessageBytes);
		return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
	}
}