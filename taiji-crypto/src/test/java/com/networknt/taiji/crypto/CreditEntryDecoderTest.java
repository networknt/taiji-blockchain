package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CreditEntryDecoderTest {
    @Test
    public void testDecodingWithoutCommentData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        CreditEntry creditEntry = new CreditEntry(to, value);

        byte[] encodedMessage = CreditEntryEncoder.encode(creditEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        CreditEntry result = CreditEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals("", result.getComment());
        assertEquals("", result.getData());
    }

    @Test
    public void testDecodingWithData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        String comment = "This is just a test";
        CreditEntry creditEntry = new CreditEntry(to, value, comment);

        byte[] encodedMessage = CreditEntryEncoder.encode(creditEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        CreditEntry result = CreditEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals(Numeric.cleanHexPrefix(Numeric.toHexString("This is just a test".getBytes())), result.getComment());
    }

    @Test
    public void testDecodingSigned() throws Exception {
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        CreditEntry creditEntry = new CreditEntry(to, value, "");
        byte[] signedMessage = CreditEntryEncoder.signMessage(
                creditEntry, SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);

        CreditEntry result = CreditEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());

        assertTrue(result instanceof SignedCreditEntry);
        SignedCreditEntry signedResult = (SignedCreditEntry) result;
        assertNotNull(signedResult.getSignatureData());
        Sign.SignatureData signatureData = signedResult.getSignatureData();
        byte[] encodedCreditEntry = CreditEntryEncoder.encode(creditEntry);
        BigInteger key = Sign.signedMessageToKey(encodedCreditEntry, signatureData);
        assertEquals(key, SampleKeys.PUBLIC_KEY);
        assertEquals(SampleKeys.ADDRESS, signedResult.getFrom());
        signedResult.verify(SampleKeys.ADDRESS);
    }


    @Test
    public void testRSize31() throws Exception {
        //CHECKSTYLE:OFF
        String hexCreditEntry = "0xf853840add5355887fffffffffffffff80801ca0e65d7e323b755e1e95bd87de0413ba63fba772dbba7a5cabf81cdded4e71355fa0606b8e3fff026ce76efc93f5e7266493ecdce3cccb57c04b66faa913d0f17603";
        //CHECKSTYLE:ON
        CreditEntry result = CreditEntryDecoder.decode(hexCreditEntry);
        SignedCreditEntry signedResult = (SignedCreditEntry) result;
        assertEquals("0xef678007d18427e6022059dbc264f27507cd1ffc", signedResult.getFrom());
    }

}
