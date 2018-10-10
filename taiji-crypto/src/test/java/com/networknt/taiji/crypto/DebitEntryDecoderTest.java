package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DebitEntryDecoderTest {

    @Test
    public void testDecodingWithoutCommentData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        DebitEntry debitEntry = new DebitEntry(to, value, gasPrice, gasLimit);

        byte[] encodedMessage = DebitEntryEncoder.encode(debitEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        DebitEntry result = DebitEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals(gasPrice, result.getGasPrice());
        assertEquals(gasLimit, result.getGasLimit());
        assertEquals("", result.getComment());
        assertEquals("", result.getData());
    }

    @Test
    public void testDecodingWithData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String comment = "This is just a test";
        DebitEntry debitEntry = new DebitEntry(to, value, gasPrice, gasLimit, comment);

        byte[] encodedMessage = DebitEntryEncoder.encode(debitEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        DebitEntry result = DebitEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals(Numeric.cleanHexPrefix(Numeric.toHexString("This is just a test".getBytes())), result.getComment());
    }

    @Test
    public void testDecodingSigned() throws Exception {
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;

        DebitEntry debitEntry = new DebitEntry(to, value, gasPrice, gasLimit, "");
        byte[] signedMessage = DebitEntryEncoder.signMessage(
                debitEntry, SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);

        DebitEntry result = DebitEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());

        assertTrue(result instanceof SignedDebitEntry);
        SignedDebitEntry signedResult = (SignedDebitEntry) result;
        assertNotNull(signedResult.getSignatureData());
        Sign.SignatureData signatureData = signedResult.getSignatureData();
        byte[] encodedDebitEntry = DebitEntryEncoder.encode(debitEntry);
        BigInteger key = Sign.signedMessageToKey(encodedDebitEntry, signatureData);
        assertEquals(key, SampleKeys.PUBLIC_KEY);
        assertEquals(SampleKeys.ADDRESS, signedResult.getFrom());
        signedResult.verify(SampleKeys.ADDRESS);
    }


    @Test
    public void testRSize31() throws Exception {
        //CHECKSTYLE:OFF
        String hexDebitEntry = "0xf855840add5355887fffffffffffffff010a80801ba01e4558bf49b5898494ad3802f1f4f1bb3724ca7854a4bbc1d9a859959c92abffa01ebf243fbc49cd20605f22a8ddf1c77ccae33595d56fbfb7d99ff5645c6d3e1f";
        //CHECKSTYLE:ON
        DebitEntry result = DebitEntryDecoder.decode(hexDebitEntry);
        SignedDebitEntry signedResult = (SignedDebitEntry) result;
        assertEquals("0xef678007d18427e6022059dbc264f27507cd1ffc", signedResult.getFrom());
    }

}
