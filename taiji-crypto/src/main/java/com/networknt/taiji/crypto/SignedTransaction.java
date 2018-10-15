package com.networknt.taiji.crypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class model a signed transaction which contains a list of debit entries
 * and a list of credit entries. The reason that the list use d and c is to make
 * the final json body to be validated by the server schema.
 *
 * @author Steve Hu
 */
public class SignedTransaction {
    List<Map<String, byte[]>> d = new ArrayList<>();
    List<Map<String, byte[]>> c = new ArrayList<>();

    public void addSignedDebitEntry(String fromAddress, byte[] signedDebitEntry) {
        Map<String, byte[]> entryMap = new HashMap<>();
        entryMap.put(fromAddress, signedDebitEntry);
        d.add(entryMap);
    }

    public void addSignedCreditEntry(String toAddress, byte[] signedCreditEntry) {
        Map<String, byte[]> entryMap = new HashMap<>();
        entryMap.put(toAddress, signedCreditEntry);
        c.add(entryMap);
    }

    public List<Map<String, byte[]>> getD() {
        return d;
    }

    public void setD(List<Map<String, byte[]>> signedDebitEntries) {
        this.d = signedDebitEntries;
    }

    public List<Map<String, byte[]>> getC() {
        return c;
    }

    public void setC(List<Map<String, byte[]>> signedCreditEntries) {
        this.c = signedCreditEntries;
    }

}