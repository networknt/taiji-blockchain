package com.networknt.taiji.crypto;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * This is a generic ledger entry for both credit and debit. Both side have the
 * same entry which is linked to two different accounts/addresses. In the ledger,
 * both entries might scattered into two different blockchains if the transaction
 * is inter chain transaction.
 *
 * Both credit and debit entries are signed by the debit address/account.
 *
 * @author Steve Hu
 */
public class LedgerEntry {
    String toAddress;     // the credit address or account
    BigInteger value;     // debit amount for the fromAddress
    String data;          // transaction data or smart contract data

    public LedgerEntry(String toAddress, BigInteger value, String data) {
        this.toAddress = toAddress;
        this.value = value;
        if(data != null) this.data = Numeric.cleanHexPrefix(data);
    }

    public LedgerEntry(String toAddress, BigInteger value) {
        this(toAddress, value, "");
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
