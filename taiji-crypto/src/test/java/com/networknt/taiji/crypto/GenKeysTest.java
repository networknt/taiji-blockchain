package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class GenKeysTest {
    @Test
    public void testGenKeys() throws Exception {
        ECKeyPair pair = Keys.createEcKeyPair();
        byte[] keys = Keys.serialize(pair);
        for(int i = 0; i < keys.length; i++ ) {
            System.out.format("%02X ", keys[i]);
        }
        System.out.println();


        String sAddress = Keys.getAddress(pair);
        System.out.println("sAddress = " + sAddress);

        String sChecksum = Keys.toChecksumAddress(sAddress);
        System.out.println("sChecksum = " + sChecksum);
    }



}
