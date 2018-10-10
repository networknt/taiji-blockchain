package com.networknt.taiji.crypto;

import org.web3j.crypto.Credentials;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {

    public static SignedTransaction signTransaction(RawTransaction rawTransaction, Credentials credentials) {
        SignedTransaction signedTransaction = new SignedTransaction();
        List<byte[]> signedCreditEntries = rawTransaction.creditEntries.stream()
                .map(c -> CreditEntryEncoder.signMessage(c, credentials))
                .collect(Collectors.toList());
        signedTransaction.setSignedCreditEntries(signedCreditEntries);

        List<byte[]> signedDebitEntries = rawTransaction.debitEntries.stream()
                .map(d -> DebitEntryEncoder.signMessage(d, credentials))
                .collect(Collectors.toList());
        signedTransaction.setSignedDebitEntries(signedDebitEntries);
        return signedTransaction;
    }

}
