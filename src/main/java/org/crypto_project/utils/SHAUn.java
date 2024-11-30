package org.crypto_project.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SHAUn implements HashAlgorithm  {


    @Override
    public String hash(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest(message.getBytes());

        // Convert byte array into signum representation in case where the size > 32 bits
        // signum : 1 => number must be treated as positive (unsigned)
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value (base16)
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 40 digits long
        while (hashtext.length() < 40) {
            hashtext = "0" + hashtext;
        }

        // return the HashText
        return hashtext;
    }

    @Override
    public String compare(String message) throws Exception {


        // Split the received message to separate the raw message from the hash
        String[] parts = message.split("::"); // Expected: ‘message::hash’
        if (parts.length != 2) {
            return "Invalid message format. Expected 'message::hash'.";
        }

        String originalMessage = parts[0];
        String receivedHash = parts[1];

        // Calculate the message hash
        System.out.println("(SERVER) no hashed : " + originalMessage);
        String calculatedHash = hash(originalMessage);
        System.out.println("(SERVER) hashed : " + calculatedHash + "\n(CLIENT) hashed : " + receivedHash);



        // Compare the server hash with the client hash
        if (calculatedHash.equals(receivedHash))
        {
            return "Message valid : " + originalMessage;
        } else
        {
            return "Hash mismatch! Invalid message.";
        }

    }
}
