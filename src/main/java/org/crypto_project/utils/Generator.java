package org.crypto_project.utils;

import java.math.BigInteger;
import java.util.Random;

public class Generator {
    public static BigInteger randomInteger(int bitLength) {
        Random rand = new Random();
        return new BigInteger(bitLength, rand);
    }

    public static BigInteger randomInteger() {
        return randomInteger(10);
    }

    public static BigInteger randomPrime(int bitLength) {
        Random rand = new Random();
        return BigInteger.probablePrime(bitLength, rand);
    }

    public static BigInteger randomPrime() {
        return randomInteger(128);
    }
}
