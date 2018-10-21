package com.networknt.taiji.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AddrGen {
    /**
     * Generate private key, public key and address
     *
     * @param chainId The first two numbers which is the chain id
     * @return
     */
    public static Credentials generateCredentials(String chainId)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        while(true) {
            ECKeyPair pair = Keys.createEcKeyPair();
            String address = Keys.getAddress(pair);
            if(address.startsWith(chainId)) {
                return Credentials.create(pair);
            }
        }
    }

    /**
     * Generate key pair with the right chain id
     *
     * @param chainId The first two numbers which is the chain id
     * @return
     */
    public static ECKeyPair generateKeyPair(String chainId)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        while(true) {
            ECKeyPair pair = Keys.createEcKeyPair();
            String address = Keys.getAddress(pair);
            if(address.startsWith(chainId)) {
                return pair;
            }
        }
    }

}
