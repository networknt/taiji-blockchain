package com.networknt.taiji.token;

import com.networknt.taiji.event.AvroConverter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenAccountTest {
    @Test
    public void testTokenAccount() {
        Map<CharSequence, Long> allowance11 = new HashMap<>();
        allowance11.put("address1", 1000L);
        allowance11.put("address2", 100L);

        Map<CharSequence, Long> approval11 = new HashMap<>();
        approval11.put("address3", 1000L);
        approval11.put("address4", 3200L);

        Account account1 = Account.newBuilder()
                .setBalance(100)
                .setAllowance(allowance11)
                .setApproval(approval11)
                .build();

        Account account2 = Account.newBuilder()
                .setBalance(200)
                .setAllowance(allowance11)
                .build();

        Account account3 = Account.newBuilder()
                .build();

        Account account4 = Account.newBuilder()
                .setAllowance(allowance11)
                .build();

        Map<CharSequence, Account> accounts = new HashMap<>();
        accounts.put("TEST1", account1);
        accounts.put("TEST2", account2);
        accounts.put("TEST3", account3);
        accounts.put("TEST4", account4);

        TokenAccount tokenAccount = TokenAccount.newBuilder()
                .setSymbols(accounts)
                .build();
        System.out.println("tokenAccount = " + tokenAccount);

        String json = AvroConverter.toJson(tokenAccount, false);
        System.out.println("json = " + json);
    }
}
