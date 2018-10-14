package com.networknt.taiji.crypto;

import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Decode a ledger entry
 *
 * @author Steve Hu
 */
public class LedgerEntryDecoder {
    public static LedgerEntry decode(String hexEntry) {
        byte[] entry = Numeric.hexStringToByteArray(hexEntry);
        RlpList rlpList = RlpDecoder.decode(entry);
        RlpList values = (RlpList) rlpList.getValues().get(0);

        String to = ((RlpString) values.getValues().get(0)).asString();
        BigInteger value = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
        String data = ((RlpString) values.getValues().get(2)).asString();
        if (values.getValues().size() > 3) {
            byte v = ((RlpString) values.getValues().get(3)).getBytes()[0];
            byte[] r = Numeric.toBytesPadded(
                    Numeric.toBigInt(((RlpString) values.getValues().get(4)).getBytes()), 32);
            byte[] s = Numeric.toBytesPadded(
                    Numeric.toBigInt(((RlpString) values.getValues().get(5)).getBytes()), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            return new SignedLedgerEntry(to, value, data, signatureData);
        } else {
            return new LedgerEntry(to, value, data);
        }
    }

}
