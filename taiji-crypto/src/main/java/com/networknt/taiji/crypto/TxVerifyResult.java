package com.networknt.taiji.crypto;

import java.util.List;
import java.util.Map;

/**
 * This is used to return an validation error or return a structure that is
 * used to generate snapshot for the transaction.
 *
 * @author Steve Hu
 */
public class TxVerifyResult {
    String error;
    String currency; // for the same transaction there is only one currency.
    String fromAddress;
    long debitAmount;
    List<Map<String, Long>> credits; // a list of address to amount mappings for credit entries

    public TxVerifyResult() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public long getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(long debitAmount) {
        this.debitAmount = debitAmount;
    }

    public List<Map<String, Long>> getCredits() {
        return credits;
    }

    public void setCredits(List<Map<String, Long>> credits) {
        this.credits = credits;
    }

}
