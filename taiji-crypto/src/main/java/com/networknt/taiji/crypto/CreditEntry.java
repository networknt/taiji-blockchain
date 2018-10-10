package com.networknt.taiji.crypto;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Objects;

/**
 * A credit entry is applied to the to account as part of the transaction. As transaction
 * fee is paid by the fromAddress, so there is no gas here. For one transaction, there is
 * at least one credit entry. Sometimes, there are even more credit entries in one transaction
 *
 * @author Steve Hu
 *
 */
public class CreditEntry {
    String toAddress;   // the credit address or account
    BigInteger value;   // credit amount for the toAddress
    String comment;     // memo or comment
    String data;        // smart contract data

    public CreditEntry() {
    }

    public CreditEntry(String toAddress, BigInteger value, String comment, String data) {
        this.toAddress = toAddress;
        this.value = value;
        if(comment != null) this.comment = Numeric.cleanHexPrefix(comment);
        if(data != null) this.data = Numeric.cleanHexPrefix(data);
    }

    public CreditEntry(String toAddress, BigInteger value, String comment) {
        this(toAddress, value, comment,"");
    }

    public CreditEntry(String toAddress, BigInteger value) {
        this(toAddress, value, "","");
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
