package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * ensure that the from account has enough fee to execute the transaction.
     *
     * @return true if valid.
     */
    public static boolean verifyTransaction(SignedTransaction stx) throws SignatureException {
        // decode ledger entry list d and calculate the amount.
        long balance = 0;
        List<Map<String, byte[]>> d = stx.getD();
        for(int i = 0; i < stx.getD().size(); i++) {
            Map<String, byte[]> dmap = d.get(i);
            Map.Entry<String,byte[]> entry = dmap.entrySet().iterator().next();
            String fromAddress = entry.getKey();
            byte[] signedLedger = entry.getValue();
            SignedLedgerEntry sd = (SignedLedgerEntry) LedgerEntryDecoder.decode(Numeric.toHexString(signedLedger));
            sd.verify(fromAddress);
            balance = balance - sd.value;
        }
        List<Map<String, byte[]>> c = stx.getC();
        for(int i = 0; i < stx.getC().size(); i++) {
            Map<String, byte[]> cmap = c.get(i);
            Map.Entry<String,byte[]> entry = cmap.entrySet().iterator().next();
            String toAddress = entry.getKey();
            byte[] signedLedger = entry.getValue();
            SignedLedgerEntry sc = (SignedLedgerEntry) LedgerEntryDecoder.decode(Numeric.toHexString(signedLedger));
            Sign.SignatureData signatureData = sc.getSignatureData();
            LedgerEntry ce = new LedgerEntry(sc.toAddress, sc.value, sc.data);
            byte[] encoded = LedgerEntryEncoder.encode(ce);
            // get public key from the signed message.
            BigInteger key = Sign.signedMessageToKey(encoded, signatureData);
            String address = "0x" + Keys.getAddress(key);
            sc.verify(address);
            balance = balance + sc.value;
        }
        if(balance == 0) return true;
        else return false;
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
}
