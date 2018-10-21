package com.networknt.taiji.crypto;

import org.junit.Test;

import static com.networknt.taiji.crypto.SecureRandomUtils.isAndroidRuntime;
import static com.networknt.taiji.crypto.SecureRandomUtils.secureRandom;
import static org.junit.Assert.assertFalse;

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
