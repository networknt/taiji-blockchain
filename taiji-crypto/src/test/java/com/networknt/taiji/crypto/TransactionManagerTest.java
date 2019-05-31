package com.networknt.taiji.crypto;

import com.networknt.config.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionManagerTest {

    static final FeeConfig feeConfig = (FeeConfig)Config.getInstance().getJsonObjectConfig("fee", FeeConfig.class);

    @Test
    public void testFeeEntryMissing() throws Exception {

        String s = "{\"currency\":\"taiji\",\"d\":[{\"0000344d315050CA9B0938B6511EC342705a1f9c\":\"+F6UAABQ8TS4sNs9nMP2yyYtTURF7iiEBfXhAIAboHMG81J1ViyyG55TpucTZ8SN7O2UJ4dTyyWZF2NGGkCwoHVixWu0T1GByN8E4YPDkKiCj7ubnUFBjKNFUJgVjUYm\"}],\"c\":[{\"000050f134b8B0Db3D9Cc3F6cB262D4d4445Ee28\":\"+F6UAABQ8TS4sNs9nMP2yyYtTURF7iiEBfXhAIAboHMG81J1ViyyG55TpucTZ8SN7O2UJ4dTyyWZF2NGGkCwoHVixWu0T1GByN8E4YPDkKiCj7ubnUFBjKNFUJgVjUYm\"}]}";
        SignedTransaction stx = Config.getInstance().getMapper().readValue(s, SignedTransaction.class);

        TxVerifyResult result = TransactionManager.verifyTransaction(stx, "0000", feeConfig);
        assertNotNull(result.getError());
        assertEquals("Fee entry is missing", result.getError());
    }

    @Test
    public void testWrongBankId() throws Exception {
        String s = "{\"currency\":\"taiji\",\"d\":[{\"0000344d315050CA9B0938B6511EC342705a1f9c\":\"+F6UAABQ8TS4sNs9nMP2yyYtTURF7iiEBfXhAIAboHMG81J1ViyyG55TpucTZ8SN7O2UJ4dTyyWZF2NGGkCwoHVixWu0T1GByN8E4YPDkKiCj7ubnUFBjKNFUJgVjUYm\"}],\"c\":[{\"000050f134b8B0Db3D9Cc3F6cB262D4d4445Ee28\":\"+F6UAABQ8TS4sNs9nMP2yyYtTURF7iiEBfXhAIAboHMG81J1ViyyG55TpucTZ8SN7O2UJ4dTyyWZF2NGGkCwoHVixWu0T1GByN8E4YPDkKiCj7ubnUFBjKNFUJgVjUYm\"}]}";
        SignedTransaction stx = Config.getInstance().getMapper().readValue(s, SignedTransaction.class);

        TxVerifyResult result = TransactionManager.verifyTransaction(stx, "0001", feeConfig);
        assertNotNull(result.getError());
    }
}
