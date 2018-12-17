package com.networknt.taiji.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.NumberFormat;
import java.util.Locale;

public class AddressGen {
    public static final Logger logger = LoggerFactory.getLogger(AddressGen.class);
    /**
     * Generate private key, public key and address
     *
     * @param chainId The first two numbers which is the chain id
     * @return
     * @deprecated
     */
    public static Credentials generateCredentials(String chainId)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        while(true) {
            ECKeyPair signingKeyPair = Keys.createEcKeyPair();
            String address = Keys.getAddress(signingKeyPair);
            KeyPair encryptingKeyPair = Keys.createCipherKeyPair();
            if(address.startsWith(chainId)) {
                return Credentials.create(signingKeyPair, encryptingKeyPair);
            }
        }
    }

    /**
     * Generate key pair with the right chain id
     *
     * @param chainId The first two numbers which is the chain id
     * @return
     * @deprecated
     */
    public static ECKeyPair generateKeyPair(String chainId)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        long l = 0;
        while(true) {
            ECKeyPair pair = Keys.createEcKeyPair();
            String address = Keys.getAddress(pair);
            if(address.startsWith(chainId)) {
                return pair;
            }
            logAttempts(l++);
        }
    }

    /**
     * Generate an address belongs to a chain
     *
     * @param chainId The first four digits of an address
     * @return
     * @deprecated
     */
    public static String generateAddress(String chainId)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        while(true) {
            ECKeyPair pair = Keys.createEcKeyPair();
            String address = Keys.getAddress(pair);
            if(address.startsWith(chainId)) {
                return Keys.toChecksumAddress(address);
            }
        }
    }

    /**
     * For every 1 million attempts, log the total number of attempts
     *
     * @param attempts the current number of attempts
     */
    private static void logAttempts(long attempts) {
        if(logger.isDebugEnabled()) {
            if (attempts == 0) {
                logger.debug("Address generation initiated.");
            } else if (attempts % 1000000 == 0) {
                logger.debug("Address generation in progress, attempts made: " +
                        NumberFormat.getNumberInstance(Locale.US).format(attempts));
            }
        }
    }

}
