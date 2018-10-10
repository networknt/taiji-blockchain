package com.networknt.taiji.crypto;

import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Decode a debit entry
 *
 * @author Steve Hu
 */
public class DebitEntryDecoder {
    public static DebitEntry decode(String hexEntry) {
        byte[] entry = Numeric.hexStringToByteArray(hexEntry);
        RlpList rlpList = RlpDecoder.decode(entry);
        RlpList values = (RlpList) rlpList.getValues().get(0);

        String to = ((RlpString) values.getValues().get(0)).asString();
        BigInteger value = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
        BigInteger gasPrice = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
        BigInteger gasLimit = ((RlpString) values.getValues().get(3)).asPositiveBigInteger();
        String comment = ((RlpString) values.getValues().get(4)).asString();
        String data = ((RlpString) values.getValues().get(5)).asString();
        if (values.getValues().size() > 6) {
            byte v = ((RlpString) values.getValues().get(6)).getBytes()[0];
            byte[] r = Numeric.toBytesPadded(
                    Numeric.toBigInt(((RlpString) values.getValues().get(7)).getBytes()), 32);
            byte[] s = Numeric.toBytesPadded(
                    Numeric.toBigInt(((RlpString) values.getValues().get(8)).getBytes()), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            return new SignedDebitEntry(to, value, gasPrice, gasLimit, comment, data, signatureData);
        } else {
            return new DebitEntry(to, value, gasPrice, gasLimit, comment, data);
        }
    }
    
}
