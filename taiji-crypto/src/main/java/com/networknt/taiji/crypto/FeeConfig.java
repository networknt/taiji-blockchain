package com.networknt.taiji.crypto;

import java.util.Map;

public class FeeConfig {
    Map<String, Fee> currencies;

    public Map<String, Fee> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, Fee> currencies) {
        this.currencies = currencies;
    }
}
