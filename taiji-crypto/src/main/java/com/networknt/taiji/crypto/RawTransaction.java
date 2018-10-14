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
    List<Map<String, LedgerEntry>> d = new ArrayList<>();
    List<Map<String, LedgerEntry>> c = new ArrayList<>();

    public RawTransaction() {}

    public void addDebitEntry(String fromAddress, LedgerEntry debitEntry) {
        Map<String, LedgerEntry> entryMap = new HashMap<>();
        entryMap.put(fromAddress, debitEntry);
        d.add(entryMap);
    }

    public void addCreditEntry(String toAddress, LedgerEntry creditEntry) {
        Map<String, LedgerEntry> entryMap = new HashMap<>();
        entryMap.put(toAddress, creditEntry);
        c.add(entryMap);
    }

    public List<Map<String, LedgerEntry>> getD() {
        return d;
    }

    public List<Map<String, LedgerEntry>> getC() {
        return c;
    }
}
