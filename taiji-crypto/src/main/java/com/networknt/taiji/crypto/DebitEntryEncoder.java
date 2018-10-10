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
 *  Encode a debit entry and sign it. Please be aware that gasPrice and gasLimit are
 *  included in the debit entry.
 *
 * @author Steve Hu
 */
public class DebitEntryEncoder {

    public static byte[] signMessage(DebitEntry debitEntry, Credentials credentials) {
        byte[] encodedDebitEntry = encode(debitEntry);
        Sign.SignatureData signatureData = Sign.signMessage(encodedDebitEntry, credentials.getEcKeyPair());
        return encode(debitEntry, signatureData);
    }

    public static byte[] encode(DebitEntry debitEntry) {
        return encode(debitEntry, null);
    }

    private static byte[] encode(DebitEntry debitEntry, Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(debitEntry, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    static List<RlpType> asRlpValues(DebitEntry debitEntry, Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = debitEntry.getToAddress();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(debitEntry.getValue()));

        // gasPrice and gasLimit
        result.add(RlpString.create(debitEntry.getGasPrice()));
        result.add(RlpString.create(debitEntry.getGasLimit()));


        String comment = debitEntry.getComment();
        if(comment != null && comment.length() > 0) {
            result.add(RlpString.create(comment));
        } else {
            result.add(RlpString.create(""));
        }

        String data = debitEntry.getData();
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
