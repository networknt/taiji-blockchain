package com.networknt.taiji.crypto;

import com.networknt.config.Config;
import org.junit.Assert;
import org.junit.Test;

public class FeeConfigTest {

    @Test
    public void testLoadConfig() {
        FeeConfig config = (FeeConfig)Config.getInstance().getJsonObjectConfig("fee", FeeConfig.class);
        Assert.assertNotNull(config);
        Fee fee = config.getCurrencies().get("taiji");
        Assert.assertTrue(fee.getInnerChain() == 1000);
    }
}
