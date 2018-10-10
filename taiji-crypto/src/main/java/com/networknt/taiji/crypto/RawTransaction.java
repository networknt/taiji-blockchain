package com.networknt.taiji.crypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There are several entries in each transaction. These entries can be debit on from account
 * and credit on to account. There might be multiple accounts involved in the same transaction.
 * Every entry in the transaction needs to be signed and all of them add up will be zero.
 *
 * @author Steve Hu
 */
public class RawTransaction {
    List<DebitEntry> debitEntries = new ArrayList<>();
    List<CreditEntry> creditEntries = new ArrayList<>();


    public RawTransaction() {
    }

    public void addDebitEntry(DebitEntry debitEntry) {
        debitEntries.add(debitEntry);
    }

    public void addCreditEntry(CreditEntry creditEntry) {
        creditEntries.add(creditEntry);
    }

    public List<DebitEntry> getDebitEntries() {
        return debitEntries;
    }

    public List<CreditEntry> getCreditEntries() {
        return creditEntries;
    }
}