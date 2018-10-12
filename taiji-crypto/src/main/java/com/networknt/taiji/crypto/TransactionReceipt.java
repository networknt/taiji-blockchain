package com.networknt.taiji.crypto;

/**
 * This represents the response from the partner server after a transaction is processed.
 *
 * For each entries in the transaction, there would be a chain id and sequence id in the
 * chain attached. It should be an array of map and each map has the submitted entry as
 * the key and chain id + sequence in the chain as the value.
 *
 * @author Steve Hu
 */
public class TransactionReceipt {

}
