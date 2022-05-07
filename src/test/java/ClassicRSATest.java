import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClassicRSATest {
    BigInteger p = new BigInteger("61");
    BigInteger q = new BigInteger("53");
    BigInteger e = new BigInteger("17");

    BigInteger message = new BigInteger("65");

    BigInteger cExpected = new BigInteger("2790");

    BigInteger dpExpected = new BigInteger("53");
    BigInteger dqExpected = new BigInteger("49");
    BigInteger qInverseExpected = new BigInteger("38");


    @Test
    void encrypt() {
        ClassicRSA classicRSA = new ClassicRSA(p, q, e);
        BigInteger c = classicRSA.encrypt(message);
        assertEquals(cExpected, c);
    }

    @Test
    void decrypt() {
        ClassicRSA classicRSA = new ClassicRSA(p, q, e);
        BigInteger mDecrypted = classicRSA.decrypt(cExpected);
        assertEquals(mDecrypted, message);
    }

    @Test
    void encryptByCRT() {
        ClassicRSA classicRSA = new ClassicRSA(p, q, e);
        CRTComponents crtComponents = classicRSA.generateCRTComponents();
        BigInteger c = classicRSA.encryptByCRTP(cExpected, crtComponents);

        assertEquals(message, c);

    }

    @Test
    void decryptByCRT() {
        ClassicRSA classicRSA = new ClassicRSA(p, q, e);
        BigInteger mDecrypted = classicRSA.decryptByCRT(cExpected, e);
        assertEquals(mDecrypted, message);
    }

    @Test
    void calculateCRTComponents() {
        ClassicRSA classicRSA = new ClassicRSA(p, q, e);
        CRTComponents crtComponents = classicRSA.generateCRTComponents();

        assertEquals(dpExpected, crtComponents.getDp());
        assertEquals(dqExpected, crtComponents.getDq());
        assertEquals(qInverseExpected, crtComponents.getqInverse());
    }

    @Test
    void addition() {
        assertEquals(2,2);
    }


    @Test
    void probablePrime() {
        BigInteger bi;

        // create and assign value to bitLength
        int bitLength = 100;

        // create a random object
        Random rnd = new Random();

        // assign probablePrime result to bi using bitLength and rnd
        // static method is called using class name
        bi = BigInteger.probablePrime(bitLength, rnd);

        String str = "ProbablePrime of bitlength " + bitLength + " is " +bi;

        // print bi value
        System.out.println( str );
    }

    @Test
    void modularMultiplicativeInverse() {
        BigInteger phi = new BigInteger("780");
        BigInteger e = new BigInteger("17");

        BigInteger result = e.modInverse(phi);

        assertEquals(new BigInteger("413"), result);

    }

    @Test
    void testQInverse() {
        BigInteger qInverse = q.modPow(new BigInteger("-1"), p);
        assertEquals(qInverseExpected, qInverse);
    }


}
