package com.networknt.taiji.crypto;

import com.networknt.blockchain.rlp.RlpDecoder;
import com.networknt.blockchain.rlp.RlpList;
import com.networknt.blockchain.rlp.RlpString;
import com.networknt.chain.utility.Numeric;

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

        // the decoded result for to address is all lower case, need to convert to checksum.
        String to = Keys.toChecksumAddress(((RlpString) values.getValues().get(0)).asString());
        Long value = ((RlpString) values.getValues().get(1)).asPositiveLong();
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
