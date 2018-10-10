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

public class DebitEntryEncoderTest {
    @Test
    public void testSignMessage() {
        byte[] signedMessage = DebitEntryEncoder.signMessage(
                createDebitEntry(), SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);
        //System.out.println("hexMessage:" + hexMessage);
        assertThat(hexMessage,
                is("0xf86584002dd535010a8098506c65617365206c656176652061742074686520646f6f72801ca09b750032e3a43ff7df38cf5fa1f9f3645ca36172126f64d2ae4211afbaefbb6ba07b874ede2c820d7e4347e4324d8038a4ead6eba1c62715fa403bf49ec5ea37fa"));
    }

    @Test
    public void testDebitEntryAsRlpValues() {
        List<RlpType> rlpStrings = DebitEntryEncoder.asRlpValues(createDebitEntry(),
                new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(9));
        for(int i = 0; i < rlpStrings.size(); i++) {
            System.out.println(((RlpString)rlpStrings.get(i)).asString());
        }
        assertThat(rlpStrings.get(0), equalTo(RlpString.create(Numeric.hexStringToByteArray("0x02dd535"))));
        assertThat(rlpStrings.get(1), equalTo(RlpString.create(BigInteger.ONE)));
        assertThat(rlpStrings.get(2), equalTo(RlpString.create(BigInteger.TEN)));
        assertThat(rlpStrings.get(3), equalTo(RlpString.create(BigInteger.ZERO)));
        assertThat(rlpStrings.get(4), equalTo(RlpString.create("Please leave at the door")));
    }

    private static DebitEntry createDebitEntry() {
        return new DebitEntry("0x02dd535", BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO, "Please leave at the door");
    }

}
