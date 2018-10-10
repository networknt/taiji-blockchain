package com.networknt.taiji.crypto;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Objects;

/**
 * An debit entry on from account as part of the transaction. There is at least
 * one debit entry for one transaction but sometimes there are more debit entries.
 *
 * @author Steve Hu
 *
 */
public class DebitEntry {
    String toAddress;     // the credit address or account
    BigInteger value;     // debit amount for the fromAddress
    BigInteger gasPrice;  // transaction fee charged from the debit address
    BigInteger gasLimit;  // transaction fee limit
    String comment;       // comment or memo
    String data;          // memo for this debit entry

    public DebitEntry(String toAddress, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, String comment, String data) {
        this.toAddress = toAddress;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        if(comment != null) this.comment = Numeric.cleanHexPrefix(comment);
        if(data != null) this.data = Numeric.cleanHexPrefix(data);
    }

    public DebitEntry(String toAddress, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, String comment) {
        this(toAddress, value, gasPrice, gasLimit, comment, "");
    }

    public DebitEntry(String toAddress, BigInteger value, BigInteger gasPrice, BigInteger gasLimit) {
        this(toAddress, value, gasPrice, gasLimit, "", "");
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
