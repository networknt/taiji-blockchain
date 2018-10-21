package com.networknt.chain.utility;

import org.junit.Test;

import static com.networknt.chain.utility.Assertions.verifyPrecondition;


public class AssertionsTest {

    @Test
    public void testVerifyPrecondition() {
        verifyPrecondition(true, "");
    }

    @Test(expected = RuntimeException.class)
    public void testVerifyPreconditionFailure() {
        verifyPrecondition(false, "");
    }
}
