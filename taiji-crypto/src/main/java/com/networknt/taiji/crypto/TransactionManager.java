package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class TransactionManager {

    public static SignedTransaction signTransaction(RawTransaction rawTransaction, Credentials credentials) {
        SignedTransaction signedTransaction = new SignedTransaction(rawTransaction.getCurrency());

        List<Map<String, byte[]>> signedCreditEntries = rawTransaction.getC().stream()
                .map(c -> SignLedgerEntry(c, credentials))
                .collect(Collectors.toList());
        signedTransaction.setC(signedCreditEntries);

        List<Map<String, byte[]>> signedDebitEntries = rawTransaction.getD().stream()
                .map(d -> SignLedgerEntry(d, credentials))
                .collect(Collectors.toList());
        signedTransaction.setD(signedDebitEntries);
        return signedTransaction;
    }

    public static SignedTransaction createSignedTransacton(String currency, List<Map<String, byte[]>> signedCreditEntries, List<Map<String, byte[]>> signedDebitEntries) {
        SignedTransaction signedTransaction = new SignedTransaction(currency);
        signedTransaction.setC(signedCreditEntries);
        signedTransaction.setD(signedDebitEntries);
        return signedTransaction;
    }

    /**
     * Verify each entry signature and the balance of the entire transaction. Also
     * ensure that the from account has enough fee and amount to execute the transaction.
     * Also, it needs to be sure that the address is owned by this bankId.
     *
     * @return String error if not null
     */
    public static TxVerifyResult verifyTransaction(SignedTransaction stx, String bankId) {
        TxVerifyResult result = new TxVerifyResult();
        result.setCurrency(stx.getCurrency());
        // decode ledger entry list d and calculate the amount.
        long balance = 0;
        List<Map<String, byte[]>> d = stx.getD();
        for(int i = 0; i < stx.getD().size(); i++) {
            Map<String, byte[]> dmap = d.get(i);
            Map.Entry<String,byte[]> entry = dmap.entrySet().iterator().next();
            String fromAddress = entry.getKey();
            if(!fromAddress.startsWith(bankId)) {
                result.setError("From address " + fromAddress + " is not owned by bankId " + bankId);
                return result;
            }
            byte[] signedLedger = entry.getValue();
            SignedLedgerEntry sd = (SignedLedgerEntry) LedgerEntryDecoder.decode(Numeric.toHexString(signedLedger));
            try {
                sd.verify(fromAddress);
            } catch (SignatureException e) {
                result.setError("Signature is not matched with from address " + fromAddress);
                return result;
            }
            // make sure that the from address is the same.
            if(result.getFromAddress() == null) {
                result.setFromAddress(fromAddress);
            } else {
                if(!result.getFromAddress().equals(fromAddress)) {
                    result.setError("The entire transaction must have only one debit address");
                    return result;
                }
            }
            balance = balance - sd.value;
        }
        // debit amount is the total of debit entries.
        result.setDebitAmount(abs(balance));

        List<Map<String, Long>> credits = new ArrayList<>();
        List<Map<String, byte[]>> c = stx.getC();
        for(int i = 0; i < stx.getC().size(); i++) {
            Map<String, byte[]> cmap = c.get(i);
            Map.Entry<String,byte[]> entry = cmap.entrySet().iterator().next();
            byte[] signedLedger = entry.getValue();
            SignedLedgerEntry sc = (SignedLedgerEntry) LedgerEntryDecoder.decode(Numeric.toHexString(signedLedger));
            // validate the toAddress with checksum to prevent sending money to an invalid address.
            if(!validateToAddress(sc.toAddress)) {
                result.setError("Invalid to address " + sc.toAddress);
                return result;
            }
            Sign.SignatureData signatureData = sc.getSignatureData();
            LedgerEntry ce = new LedgerEntry(sc.toAddress, sc.value, sc.data);
            byte[] encoded = LedgerEntryEncoder.encode(ce);
            // get public key from the signed message.
            try {
                BigInteger key = Sign.signedMessageToKey(encoded, signatureData);
                String address = Keys.getAddress(key);
                sc.verify(address);
            } catch (SignatureException e) {
                result.setError("Signature is not matched in the signed message");
                return result;
            }
            // snapshot credit entry
            Map<String, Long> addressAmount = new HashMap<>();
            addressAmount.put(sc.toAddress, sc.value);
            credits.add(addressAmount);

            balance = balance + sc.value;
        }
        if(balance != 0) {
            result.setError("Debit and Credit entries are not balanced.");
            return result;
        }
        result.setCredits(credits);
        return result;
    }

    private static Map<String, byte[]> SignLedgerEntry(Map<String, LedgerEntry> ledgerEntryMap, Credentials credentials) {
        return transform(ledgerEntryMap, (ledgerEntry -> LedgerEntryEncoder.signMessage(ledgerEntry, credentials)));

    }
    private static <X, Y, Z> Map<X, Z> transform(Map<X, Y> input, Function<Y, Z> function) {
        return input
            .entrySet()
            .stream()
            .collect(Collectors.toMap((entry) -> entry.getKey(),
                     (entry) -> function.apply(entry.getValue())));
    }

    private static boolean validateToAddress(String toAddress) {
        // the length of the address must be 40.
        if(toAddress == null) return false;
        if(toAddress.length() != 40) return false;
        // if all digits, return false.
        if(isNum(toAddress)) return false;
        // checksum
        if(toAddress.equals(Keys.toChecksumAddress(toAddress.toLowerCase()))) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isNum(String s) {
        boolean b = true;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            b = '0' <= c && c <= '9';
            if(!b) break;
        }
        return b;
    }
}
