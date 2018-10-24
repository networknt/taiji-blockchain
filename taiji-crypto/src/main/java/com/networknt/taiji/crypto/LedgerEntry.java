package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;

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
    long id;              // generated ledger id once it is on the chain
    String toAddress;     // the credit address or account
    long value;           // debit amount for the fromAddress in shell
    String data;          // transaction data or smart contract data

    public LedgerEntry(String toAddress, long value, String data) {
        this.toAddress = toAddress;
        this.value = value;
        if(data != null) this.data = Numeric.cleanHexPrefix(data);
    }

    public LedgerEntry(String toAddress, long value) {
        this(toAddress, value, "");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
