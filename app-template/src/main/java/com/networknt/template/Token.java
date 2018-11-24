package com.networknt.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.networknt.taiji.crypto.AddrGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public class Token implements App {
    static final Logger logger = LoggerFactory.getLogger(Token.class);
    static int VERSION = 1;

    // token specific fields
    String address;
    String owner;
    String name;
    String symbol;
    int decimals;
    long total;

    public Token() {
    }

    public Token(String owner, String name, String symbol, long total) {
        this.owner = owner;
        this.name = name;
        this.symbol = symbol;
        this.total = total;
        this.decimals = 9;

    }

    public Token(String owner, String name, String symbol, long total, int decimals) {
        this.owner = owner;
        this.name = name;
        this.symbol = symbol;
        this.total = total;
        this.decimals = decimals;
    }


    @Override
    @JsonIgnore
    public String getId() {
        return App.ID_TOKEN;
    }

    @Override
    @JsonIgnore
    public int getVersion() {
        return VERSION;
    }

    @Override
    public String getAddress() {
        String address = null;
        try {
            address = AddrGen.generateAddress(owner.substring(0, 4));
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
