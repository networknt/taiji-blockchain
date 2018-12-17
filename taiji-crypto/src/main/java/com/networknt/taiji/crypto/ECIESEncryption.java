package com.networknt.taiji.crypto;

import org.bouncycastle.jce.spec.IESParameterSpec;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ECIESEncryption {

    public static byte[] encrypt(PublicKey publicKey, byte[] plainText) throws Exception {

        // get ECIES cipher objects
        Cipher acipher = Cipher.getInstance("ECIES");

        //  generate derivation and encoding vectors
        byte[]  d = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        byte[]  e = new byte[] { 8, 7, 6, 5, 4, 3, 2, 1 };
        IESParameterSpec param = new IESParameterSpec(d, e, 256);

        // encrypt the plaintext using the public key
        acipher.init(Cipher.ENCRYPT_MODE, publicKey, param);
        return acipher.doFinal(plainText);
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] cipherText) throws Exception {

        // get ECIES cipher objects
        Cipher bcipher = Cipher.getInstance("ECIES");

        //  generate derivation and encoding vectors
        byte[]  d = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        byte[]  e = new byte[] { 8, 7, 6, 5, 4, 3, 2, 1 };
        IESParameterSpec param = new IESParameterSpec(d, e, 256);

        // decrypt the text using the private key
        bcipher.init(Cipher.DECRYPT_MODE, privateKey, param);
        return bcipher.doFinal(cipherText);
    }

}
