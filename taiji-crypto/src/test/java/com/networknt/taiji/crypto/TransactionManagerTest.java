package com.networknt.taiji.crypto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.networknt.config.Config;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TransactionManagerTest {

    @Test
    public void testVerify() throws Exception {
        String s = "{\"currency\":\"taiji\",\"d\":[{\"0x0142166cbfde09d46081196d9428cd44f9534a0a\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}],\"c\":[{\"0x01b928ccc352743b98aba5ef919e0f8731db47d2\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}]}";
        SignedTransaction stx = Config.getInstance().getMapper().readValue(s, SignedTransaction.class);

        boolean b = TransactionManager.verifyTransaction(stx);
        Assert.assertTrue(b);
    }

}
