package com.networknt.taiji.crypto;

import java.util.Map;

/**
 * This is the interface defines the cache of the snapshots for currencies. There would be different
 * implementations. For example, in-memory or with a local database.
 *
 * @author Steve Hu
 */
public interface Snapshot {
    /**
     * Get balance with an address and currency name
     * @param address address
     * @param currency currency
     * @return balance Long
     */
    Long getBalance(String address, String currency);

    /**
     * Set balance for an address and currency name
     * @param address address
     * @param currency currency
     * @param balance Long
     */
    void setBalance(String address, String currency, Long balance);

    Map<String, Long> getSnapshot(String address);

    void setSnapshot(String address, Map<String, Long> snapshot);

    byte[] serializeSnapshot(Map<String, Long> snapshot);

    Map<String, Long> deserializeSnapshot(byte[] snapshot);
}
