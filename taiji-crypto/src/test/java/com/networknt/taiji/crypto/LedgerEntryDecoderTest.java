package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LedgerEntryDecoderTest {
    @Test
    public void testDecodingWithoutCommentData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        LedgerEntry ledgerEntry = new LedgerEntry(to, value);

        byte[] encodedMessage = LedgerEntryEncoder.encode(ledgerEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        LedgerEntry result = LedgerEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());
    }

    @Test
    public void testDecodingWithData() throws Exception {
        String to = "0x0114f873b010081f3057963709a6b2462c1206cb";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        String comment = "This is just a test";
        LedgerEntry ledgerEntry = new LedgerEntry(to, value, comment);

        byte[] encodedMessage = LedgerEntryEncoder.encode(ledgerEntry);
        String hexMessage = Numeric.toHexString(encodedMessage);

        LedgerEntry result = LedgerEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
    }

    @Test
    public void testDecodingSigned() throws Exception {
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        LedgerEntry ledgerEntry = new LedgerEntry(to, value, "");
        byte[] signedMessage = LedgerEntryEncoder.signMessage(
                ledgerEntry, SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);

        LedgerEntry result = LedgerEntryDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(to, result.getToAddress());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());

        assertTrue(result instanceof SignedLedgerEntry);
        SignedLedgerEntry signedResult = (SignedLedgerEntry) result;
        assertNotNull(signedResult.getSignatureData());
        Sign.SignatureData signatureData = signedResult.getSignatureData();
        byte[] encodedLedgerEntry = LedgerEntryEncoder.encode(ledgerEntry);
        BigInteger key = Sign.signedMessageToKey(encodedLedgerEntry, signatureData);
        assertEquals(key, SampleKeys.PUBLIC_KEY);
        assertEquals(SampleKeys.ADDRESS, signedResult.getFrom());
        signedResult.verify(SampleKeys.ADDRESS);
    }


    @Test
    public void testRSize31() throws Exception {
        //CHECKSTYLE:OFF
        String hexLedgerEntry = "0xf852840add5355887fffffffffffffff801ba054f6637de97d1ffc2a94f18db668e1af8e0c6f92eccea7d0007fd14d6e566563a004238ab261ffafd90f5083a312522825557f9e1f13a883d3d7c70b62da186f36";
        //CHECKSTYLE:ON
        LedgerEntry result = LedgerEntryDecoder.decode(hexLedgerEntry);
        SignedLedgerEntry signedResult = (SignedLedgerEntry) result;
        assertEquals("0xef678007d18427e6022059dbc264f27507cd1ffc", signedResult.getFrom());
    }
    
}
