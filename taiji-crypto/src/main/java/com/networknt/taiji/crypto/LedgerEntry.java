package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;

import java.util.Arrays;

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
    long time;            // timestamp of the ledger when it is put on the chain
    String toAddress;     // the credit address or account
    long value;           // debit amount for the fromAddress in shell
    byte[] data;          // transaction data or smart contract data in avro encoding

    public LedgerEntry() {
    }

    public LedgerEntry(String toAddress, long value, byte[] data) {
        this.toAddress = toAddress;
        this.value = value;
        if(data != null) this.data = data;
    }

    public LedgerEntry(String toAddress, long value) {
        this(toAddress, value, new byte[0]);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LedgerEntry{" +
                "id=" + id +
                ", time=" + time +
                ", toAddress='" + toAddress + '\'' +
                ", value=" + value +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
