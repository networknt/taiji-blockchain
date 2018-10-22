package com.networknt.taiji.crypto;

import com.networknt.config.Config;
import org.junit.Assert;
import org.junit.Test;

public class TransactionManagerTest {

    @Test
    public void testVerify() throws Exception {
        String s = "{\"currency\":\"taiji\",\"d\":[{\"0142166cbfde09d46081196d9428cd44f9534a0a\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}],\"c\":[{\"0x01b928ccc352743b98aba5ef919e0f8731db47d2\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}]}";
        SignedTransaction stx = Config.getInstance().getMapper().readValue(s, SignedTransaction.class);

        TxVerifyResult result = TransactionManager.verifyTransaction(stx, "0142");
        Assert.assertNull(result.getError());
    }

    @Test
    public void testWrongBankId() throws Exception {
        String s = "{\"currency\":\"taiji\",\"d\":[{\"0142166cbfde09d46081196d9428cd44f9534a0a\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}],\"c\":[{\"0x01b928ccc352743b98aba5ef919e0f8731db47d2\":\"+GKUAbkozMNSdDuYq6XvkZ4PhzHbR9KIDeC2s6dkAACAHKAh2FqR3tQLisfkUfvLAZy1rfu9ipj4WFX/fhxCwLQShaBfAq5WjpGu1FVFM86d6ZiBl4Ln+6ittKQXwT7V3kd3MA==\"}]}";
        SignedTransaction stx = Config.getInstance().getMapper().readValue(s, SignedTransaction.class);

        TxVerifyResult result = TransactionManager.verifyTransaction(stx, "0000");
        Assert.assertNotNull(result.getError());
    }

}
