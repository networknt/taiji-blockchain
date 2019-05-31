package com.networknt.taiji.crypto;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.junit.jupiter.api.Test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ECIESEncryptionTest {

    @Test
    public void testEncryption() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        byte[] message = "Hello".getBytes();
        byte[] cipherText = ECIESEncryption.encrypt(keyPair.getPublic(), message);
        byte[] decrypted = ECIESEncryption.decrypt(keyPair.getPrivate(), cipherText);
        System.out.println("decrypted = " + new String(decrypted));

    }

    @Test
    public void testPublicSerialize() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        BCECPrivateKey privateKey = (BCECPrivateKey)keyPair.getPrivate();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
        KeyFactory factory = KeyFactory.getInstance("ECDH");
        PublicKey publicKey = factory.generatePublic(spec);
        assertEquals(publicKey, keyPair.getPublic());
    }

    @Test
    public void testPrivateSerialize() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        //BCECPrivateKey privateKey = (BCECPrivateKey)keyPair.getPrivate();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
        KeyFactory factory = KeyFactory.getInstance("ECDH");
        PrivateKey privateKey = factory.generatePrivate(spec);
        assertEquals(privateKey, keyPair.getPrivate());
    }

    @Test
    public void testPrivateToPublic() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        BCECPrivateKey privateKey = (BCECPrivateKey)keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey)keyPair.getPublic();

    }
}
