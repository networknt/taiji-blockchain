package com.networknt.taiji.token;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TokenTransactionsTest {
    @Test
    public void testTokenTransactions() {
        TokenTransaction tx1 = TokenTransaction.newBuilder()
                .setAmount(1200)
                .setSymbol("TEST1")
                .setFrom("address1")
                .setTo("address2")
                .setType(TokenTranType.T)
                .setTimestamp(100023093)
                .build();

        TokenTransaction tx2 = TokenTransaction.newBuilder()
                .setAmount(1200)
                .setSymbol("TEST1")
                .setFrom("address1")
                .setTo("address2")
                .setType(TokenTranType.A)
                .setTimestamp(10032939)
                .build();

        List<TokenTransaction> list = new ArrayList<>();
        list.add(tx1);
        list.add(tx2);


        TokenTransactions txs = TokenTransactions.newBuilder()
                .setTokenTransactionArray(list)
                .build();

        System.out.println("transactions = " + txs);
    }
}
