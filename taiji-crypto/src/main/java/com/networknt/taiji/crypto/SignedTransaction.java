package com.networknt.taiji.crypto;

import java.util.ArrayList;
import java.util.List;

public class SignedTransaction {
    List<byte[]> signedDebitEntries = new ArrayList<>();
    List<byte[]> signedCreditEntries = new ArrayList<>();

    public void addSignedDebitEntry(byte[] signedDebitEntry) {
        signedDebitEntries.add(signedDebitEntry);
    }

    public void addSignedCreditEntry(byte[] signedCreditEntry) {
        signedCreditEntries.add(signedCreditEntry);
    }

    public List<byte[]> getSignedDebitEntries() {
        return signedDebitEntries;
    }

    public void setSignedDebitEntries(List<byte[]> signedDebitEntries) {
        this.signedDebitEntries = signedDebitEntries;
    }

    public List<byte[]> getSignedCreditEntries() {
        return signedCreditEntries;
    }

    public void setSignedCreditEntries(List<byte[]> signedCreditEntries) {
        this.signedCreditEntries = signedCreditEntries;
    }
}
