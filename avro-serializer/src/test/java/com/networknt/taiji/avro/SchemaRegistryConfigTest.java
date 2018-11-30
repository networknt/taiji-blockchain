package com.networknt.taiji.avro;

import com.networknt.config.Config;
import org.junit.Assert;
import org.junit.Test;

public class SchemaRegistryConfigTest {
    @Test
    public void testConfig() {
        SchemaRegistryConfig config = (SchemaRegistryConfig)Config.getInstance().getJsonObjectConfig(SchemaRegistryConfig.CONFIG_NAME, SchemaRegistryConfig.class);
        Assert.assertNotNull(config);
    }
}
