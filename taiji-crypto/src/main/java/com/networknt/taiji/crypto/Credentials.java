package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;

import java.security.KeyPair;
import java.util.Objects;

/**
 * Credentials wrapper.
 */
public class Credentials {

    private final ECKeyPair ecKeyPair;
    private final KeyPair encryptingKeyPair;
    private final String address;

    private Credentials(ECKeyPair ecKeyPair, KeyPair encryptingKeyPair, String address) {
        this.ecKeyPair = ecKeyPair;
        this.encryptingKeyPair = encryptingKeyPair;
        this.address = address;
    }

    public ECKeyPair getEcKeyPair() {
        return ecKeyPair;
    }

    public KeyPair getEncryptingKeyPair() {
        return encryptingKeyPair;
    }

    public String getAddress() {
        return address;
    }

    public static Credentials create(ECKeyPair ecKeyPair, KeyPair encryptingKeyPair) {
        String address = Keys.getAddress(ecKeyPair);
        return new Credentials(ecKeyPair, encryptingKeyPair, address);
    }

    public static Credentials create(String privateKey) throws Exception {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        KeyPair encryptingKeyPair = Keys.createCipherKeyPair();
        return create(ecKeyPair, encryptingKeyPair);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(ecKeyPair, that.ecKeyPair) &&
                Objects.equals(encryptingKeyPair, that.encryptingKeyPair) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ecKeyPair, encryptingKeyPair, address);
    }
}
