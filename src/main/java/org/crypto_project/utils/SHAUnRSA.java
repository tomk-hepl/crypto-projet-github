package org.crypto_project.utils;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class SHAUnRSA  implements RSAHashAlgorithm{

    @Override
    public String rsahash(String message, PrivateKey privateKey) throws Exception {

        SHAUn shaUn = new SHAUn();
        String hashTest = shaUn.hash(message);

        // Sign the hash with RSA private key
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(hashTest.getBytes());

        // Return the RSA signature encoded in Base64
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    @Override
    public String rsacompare(String message,PublicKey publicKey) throws Exception {
        // Split received message: 'message::signature'
        String[] parts = message.split("==");
        if (parts.length != 2)
        {
            return "Invalid message format. Expected 'message::signature'.";
        }

        String originalMessage = parts[0];
        String receivedSignature = parts[1];

        // Compute the hash of the original message
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = md.digest(originalMessage.getBytes());

        // Verify the received signature using the public key
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(hashBytes);

        boolean isValid = signature.verify(Base64.getDecoder().decode(receivedSignature));

        if (isValid)
        {

            return "Message valid: " + originalMessage;
        } else
        {
            return "Invalid signature! Message compromised or invalid.";
        }
    }

    public boolean rsaCompareWithHash(String hashR, String signatureR, PublicKey publicKey) throws Exception {

        // Verify the received signature using the public key
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(hashR.getBytes());

        String debug = signature.toString();
        System.out.println("rsaCompareWithHash - signature : " + debug);

        return signature.verify(Base64.getDecoder().decode(signatureR));

    }



}
