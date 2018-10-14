package com.networknt.taiji.crypto;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class LedgerEntryEncoderTest {
    @Test
    public void testSignMessage() {
        byte[] signedMessage = LedgerEntryEncoder.signMessage(
                createLedgerEntry(), SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);
        assertThat(hexMessage,
                is("0xf84a84002dd53501801ca09d17720ec8a82fe00f267fae6261501598b80fa43dfff3dd3834259cae2a3c43a0138e8b7b2d2fdd455af124f9d453b7984be001e100bd215ed5d9be205cd1b4be"));
    }

    @Test
    public void testLedgerEntryAsRlpValues() {
        List<RlpType> rlpStrings = LedgerEntryEncoder.asRlpValues(createLedgerEntry(),
                new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(6));
        for(int i = 0; i < rlpStrings.size(); i++) {
            System.out.println(((RlpString)rlpStrings.get(i)).asString());
        }
        assertThat(rlpStrings.get(0), equalTo(RlpString.create(Numeric.hexStringToByteArray("0x02dd535"))));
        assertThat(rlpStrings.get(1), equalTo(RlpString.create(BigInteger.ONE)));
        assertThat(rlpStrings.get(2), equalTo(RlpString.create("")));
    }

    private static LedgerEntry createLedgerEntry() {
        return new LedgerEntry("0x02dd535", BigInteger.ONE, "");
    }
    
}
