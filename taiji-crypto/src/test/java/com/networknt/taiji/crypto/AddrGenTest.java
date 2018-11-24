package com.networknt.taiji.crypto;

import org.junit.Test;

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

    @Test
    public void testGenerateAddress() throws Exception {
        String address = AddrGen.generateAddress("0000");
        System.out.println("0000 " + address);

        address = AddrGen.generateAddress("0001");
        System.out.println("0001 " + address);

        address = AddrGen.generateAddress("0002");
        System.out.println("0002 " + address);
    }
}
