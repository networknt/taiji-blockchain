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

public class CreditEntryEncoderTest {

    @Test
    public void testSignMessage() {
        byte[] signedMessage = CreditEntryEncoder.signMessage(
                createCreditEntry(), SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);
        //System.out.println("hexMessage:" + hexMessage);
        assertThat(hexMessage,
                is("0xf86384002dd5350198506c65617365206c656176652061742074686520646f6f72801ca022837d4d4326a757c2eb99a5d4bd99a463e42e6a47c75f7e6e0092c68ff7964aa04857a0737a944893dfb731426df5f388a162f0baf745b35d710f12630cdfd6c5"));
    }

    @Test
    public void testCreditEntryAsRlpValues() {
        List<RlpType> rlpStrings = CreditEntryEncoder.asRlpValues(createCreditEntry(),
                new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(7));
        for(int i = 0; i < rlpStrings.size(); i++) {
            System.out.println(((RlpString)rlpStrings.get(i)).asString());
        }
        assertThat(rlpStrings.get(0), equalTo(RlpString.create(Numeric.hexStringToByteArray("0x02dd535"))));
        assertThat(rlpStrings.get(1), equalTo(RlpString.create(BigInteger.ONE)));
        assertThat(rlpStrings.get(2), equalTo(RlpString.create("Please leave at the door")));
    }

    private static CreditEntry createCreditEntry() {
        return new CreditEntry("0x02dd535", BigInteger.ONE, "Please leave at the door");
    }

}
