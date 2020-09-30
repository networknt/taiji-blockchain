package com.networknt.taiji.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressUtilTest {
    @Test
    public void testSammeChain() {
        String address1 = "000030A2B585F481BEB76536fb2E9E9b4aD69e65";
        String address2 = "00004ced067923Cf7884B9364E2d6996f73aFcF9";
        Assertions.assertTrue(AddressUtil.sameChain(address1, address2));
    }

    @Test
    public void testOneNullAddress() {
        String address1 = "000030A2B585F481BEB76536fb2E9E9b4aD69e65";
        String address2 = null;
        Assertions.assertFalse(AddressUtil.sameChain(address1, address2));
    }

    @Test
    public void testNotSameChain() {
        String address1 = "000030A2B585F481BEB76536fb2E9E9b4aD69e65";
        String address2 = "00024ced067923Cf7884B9364E2d6996f73aFcF9";
        Assertions.assertFalse(AddressUtil.sameChain(address1, address2));
    }

}
