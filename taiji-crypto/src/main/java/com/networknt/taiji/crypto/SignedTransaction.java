package com.networknt.taiji.crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * This class model a signed transaction which contains a list of debit entries
 * and a list of credit entries. The reason that the list use d and c is to make
 * the final json body to be validated by the server schema.
 *
 * @author Steve Hu
 */
public class SignedTransaction {
    List<byte[]> d = new ArrayList<>();
    List<byte[]> c = new ArrayList<>();

    public void addSignedDebitEntry(byte[] signedDebitEntry) {
        d.add(signedDebitEntry);
    }

    public void addSignedCreditEntry(byte[] signedCreditEntry) {
        c.add(signedCreditEntry);
    }

    public List<byte[]> getD() {
        return d;
    }

    public void setD(List<byte[]> signedDebitEntries) {
        this.d = signedDebitEntries;
    }

    public List<byte[]> getC() {
        return c;
    }

    public void setC(List<byte[]> signedCreditEntries) {
        this.c = signedCreditEntries;
    }
}
