package com.networknt.taiji.crypto;

import org.junit.jupiter.api.Test;


import static com.networknt.taiji.crypto.SecureRandomUtils.isAndroidRuntime;
import static com.networknt.taiji.crypto.SecureRandomUtils.secureRandom;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SecureRandomUtilsTest {

    @Test
    public void testSecureRandom() {
        secureRandom().nextInt();
    }

    @Test
    public void testIsNotAndroidRuntime() {
        assertFalse(isAndroidRuntime());
    }
}
