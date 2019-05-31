package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {

    private static final byte[] ENCODED;

    static {
        byte[] privateKey = Numeric.hexStringToByteArray(SampleKeys.PRIVATE_KEY_STRING);
        byte[] publicKey = Numeric.hexStringToByteArray(SampleKeys.PUBLIC_KEY_STRING);
        ENCODED = Arrays.copyOf(privateKey, privateKey.length + publicKey.length);
        System.arraycopy(publicKey, 0, ENCODED, privateKey.length, publicKey.length);
    }

    @Test
    public void testCreateSecp256k1KeyPair() throws Exception {
        KeyPair keyPair = Keys.createSecp256k1KeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        assertNotNull(privateKey);
        assertNotNull(publicKey);

        assertThat(privateKey.getEncoded().length, is(144));
        assertThat(publicKey.getEncoded().length, is(88));
    }

    @Test
    public void testCreateEcKeyPair() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        assertThat(ecKeyPair.getPublicKey().signum(), is(1));
        assertThat(ecKeyPair.getPrivateKey().signum(), is(1));
    }

    @Test
    public void testGetAddressString() {
        assertThat(Keys.getAddress(SampleKeys.PUBLIC_KEY_STRING),
                is(SampleKeys.ADDRESS_NO_PREFIX));
    }

    @Test
    public void testGetAddressZeroPaddedAddress() {
        String publicKey =
                "a1b31be4d58a7ddd24b135db0da56a90fb5382077ae26b250e1dc9cd6232ce22"
                        + "70f4c995428bc76aa78e522316e95d7834d725efc9ca754d043233af6ca90113";
        assertThat(Keys.getAddress(publicKey),
                is("01c52B08330E05D731e38c856C1043288F7D9744"));
    }

    @Test
    public void testGetAddressBigInteger() {
        assertThat(Keys.getAddress(SampleKeys.PUBLIC_KEY),
                is(SampleKeys.ADDRESS_NO_PREFIX));
    }

    /*
    @Test
    public void testGetAddressSmallPublicKey() {
        byte[] address = Keys.getAddress(
                Numeric.toBytesPadded(BigInteger.valueOf(0x1234), Keys.PUBLIC_KEY_SIZE));
        String expected = Numeric.toHexStringNoPrefix(address);

        assertThat(Keys.getAddress("0x1234"), equalTo(expected));
    }

    @Test
    public void testGetAddressZeroPadded() {
        byte[] address = Keys.getAddress(
                Numeric.toBytesPadded(BigInteger.valueOf(0x1234), Keys.PUBLIC_KEY_SIZE));
        String expected = Numeric.toHexStringNoPrefix(address);

        String value = "1234";
        assertThat(Keys.getAddress("0x"
                        + StringUtils.repeat('0', Keys.PUBLIC_KEY_LENGTH_IN_HEX - value.length()) + value),
                equalTo(expected));
    }
    */

    @Test
    public void testToChecksumAddress() {
        // Test cases as per https://github.com/ethereum/EIPs/blob/master/EIPS/eip-55.md#test-cases

        assertThat(Keys.toChecksumAddress("fb6916095ca1df60bb79ce92ce3ea74c37c5d359"),
                is("fB6916095ca1df60bB79Ce92cE3Ea74c37c5d359"));

        // All uppercase
        assertThat(Keys.toChecksumAddress("52908400098527886E0F7030069857D2E4169EE7"),
                is("52908400098527886E0F7030069857D2E4169EE7"));
        assertThat(Keys.toChecksumAddress("8617E340B3D01FA5F11F306F4090FD50E238070D"),
                is("8617E340B3D01FA5F11F306F4090FD50E238070D"));

        // All lowercase
        assertThat(Keys.toChecksumAddress("de709f2102306220921060314715629080e2fb77"),
                is("de709f2102306220921060314715629080e2fb77"));
        assertThat(Keys.toChecksumAddress("27b1fdb04752bbc536007a920d24acb045561c26"),
                is("27b1fdb04752bbc536007a920d24acb045561c26"));

        // Normal
        assertThat(Keys.toChecksumAddress("5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed"),
                is("5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed"));
        assertThat(Keys.toChecksumAddress("fB6916095ca1df60bB79Ce92cE3Ea74c37c5d359"),
                is("fB6916095ca1df60bB79Ce92cE3Ea74c37c5d359"));
        assertThat(Keys.toChecksumAddress("dbF03B407c01E7cD3CBea99509d93f8DDDC8C6FB"),
                is("dbF03B407c01E7cD3CBea99509d93f8DDDC8C6FB"));
        assertThat(Keys.toChecksumAddress("D1220A0cf47c7B9Be7A2E6BA89F429762e7b9aDb"),
                is("D1220A0cf47c7B9Be7A2E6BA89F429762e7b9aDb"));
    }

    @Test
    public void testSerializeECKey() {
        assertThat(Keys.serialize(SampleKeys.KEY_PAIR), is(ENCODED));
    }

    @Test
    public void testDeserializeECKey() {
        assertThat(Keys.deserialize(ENCODED), is(SampleKeys.KEY_PAIR));
    }

    @Test
    public void testDeserializeInvalidKey() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Keys.deserialize(new byte[0]);
        });
    }

    @Test
    public void testIsNum() {
        String s = "0093903490832403290990234";
        assertTrue(Keys.isNum(s));

        s = "0390432490328493a9324832904832";
        assertFalse(Keys.isNum(s));
    }

    @Test
    public void testValidateAddress() {
        String a = "2390490324-03294023940324";
        assertFalse(Keys.validateToAddress(a));
        a = "9032843920843920843902849032840943204303";
        assertFalse(Keys.validateToAddress(a));
        a = "0000344D315050CA9B0938B6511EC342705a1f9c";
        assertFalse(Keys.validateToAddress(a));

        a = "0000344d315050CA9B0938B6511EC342705a1f9c";
        assertTrue(Keys.validateToAddress(a));
    }

}
