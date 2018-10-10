package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.Credentials;

public class AddrGenTest {

    @Test
    public void testGenerateCredentials() throws Exception {
        Credentials c01 = AddrGen.generateCredentials("01");
        System.out.println(c01.getAddress());
        Credentials c02 = AddrGen.generateCredentials("02");
        System.out.println(c02.getAddress());
        Credentials c03 = AddrGen.generateCredentials("03");
        System.out.println(c03.getAddress());
    }
}
