package com.networknt.taiji.crypto;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Encode a ledger entry and sign it.
 *
 * @author Steve Hu
 */
public class LedgerEntryEncoder {

    public static byte[] signMessage(LedgerEntry entry, Credentials credentials) {
        byte[] encodedEntry = encode(entry);
        Sign.SignatureData signatureData = Sign.signMessage(encodedEntry, credentials.getEcKeyPair());
        return encode(entry, signatureData);
    }

    public static byte[] encode(LedgerEntry entry) {
        return encode(entry, null);
    }

    private static byte[] encode(LedgerEntry entry, Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(entry, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    static List<RlpType> asRlpValues(LedgerEntry entry, Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = entry.getToAddress();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(entry.getValue()));

        String data = entry.getData();
        if(data != null && data.length() > 0) {
            result.add(RlpString.create(Numeric.hexStringToByteArray(data)));
        } else {
            result.add(RlpString.create(""));
        }

        if (signatureData != null) {
            result.add(RlpString.create(signatureData.getV()));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS())));
        }
        return result;
    }

}
