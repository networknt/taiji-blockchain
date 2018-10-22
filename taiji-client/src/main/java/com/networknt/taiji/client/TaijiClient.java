package com.networknt.taiji.client;

import com.networknt.config.Config;
import com.networknt.taiji.crypto.SignedTransaction;
import com.networknt.taiji.crypto.TransactionReceipt;

public class TaijiClient {

    public static TransactionReceipt postTx(SignedTransaction stx) {
        try {
            System.out.println("stx = " + Config.getInstance().getMapper().writeValueAsString(stx));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
