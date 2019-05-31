package com.networknt.chain.utility;


import org.junit.jupiter.api.Test;

import static com.networknt.chain.utility.Assertions.verifyPrecondition;


public class AssertionsTest {

    @Test
    public void testVerifyPrecondition() {
        verifyPrecondition(true, "");
    }

    @Test
    public void testVerifyPreconditionFailure() {
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            verifyPrecondition(false, "");
        });

    }
}
