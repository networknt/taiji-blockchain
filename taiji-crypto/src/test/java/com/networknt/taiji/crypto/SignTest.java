package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.math.BigInteger;
import java.security.SignatureException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SignTest {

    private static final byte[] TEST_MESSAGE = "A test message".getBytes();

    @Test
    public void testSignMessage() {
        Sign.SignatureData signatureData = Sign.signMessage(TEST_MESSAGE, SampleKeys.KEY_PAIR);

        Sign.SignatureData expected = new Sign.SignatureData(
                (byte) 27,
                Numeric.hexStringToByteArray(
                        "0x9631f6d21dec448a213585a4a41a28ef3d4337548aa34734478b563036163786"),
                Numeric.hexStringToByteArray(
                        "0x2ff816ee6bbb82719e983ecd8a33a4b45d32a4b58377ef1381163d75eedc900b")
        );

        assertThat(signatureData, is(expected));
    }

    @Test
    public void testSignedMessageToKey() throws SignatureException {
        Sign.SignatureData signatureData = Sign.signMessage(TEST_MESSAGE, SampleKeys.KEY_PAIR);
        BigInteger key = Sign.signedMessageToKey(TEST_MESSAGE, signatureData);
        assertThat(key, equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test
    public void testPublicKeyFromPrivateKey() {
        assertThat(Sign.publicKeyFromPrivate(SampleKeys.PRIVATE_KEY),
                equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test
    public void testInvalidSignature() throws SignatureException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Sign.signedMessageToKey(
                    TEST_MESSAGE, new Sign.SignatureData((byte) 27, new byte[]{1}, new byte[]{0}));
        });
    }
}
