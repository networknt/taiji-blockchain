package com.networknt.taiji.crypto;

import com.networknt.config.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FeeConfigTest {

    @Test
    public void testLoadConfig() {
        FeeConfig config = (FeeConfig)Config.getInstance().getJsonObjectConfig("fee", FeeConfig.class);
        assertNotNull(config);
        Fee fee = config.getCurrencies().get("taiji");
        assertTrue(fee.getInnerChain() == 1000);
    }
}
