package com.networknt.chain.utility;


import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.networknt.chain.utility.Bytes.trimLeadingZeroes;

public class BytesTest {

    @Test
    public void testTrimLeadingZeroes() {
        assertThat(trimLeadingZeroes(new byte[]{}), is(new byte[]{}));
        assertThat(trimLeadingZeroes(new byte[]{0}), is(new byte[]{0}));
        assertThat(trimLeadingZeroes(new byte[]{1}), is(new byte[]{1}));
        assertThat(trimLeadingZeroes(new byte[]{0, 1}), is(new byte[]{1}));
        assertThat(trimLeadingZeroes(new byte[]{0, 0, 1}), is(new byte[]{1}));
        assertThat(trimLeadingZeroes(new byte[]{0, 0, 1, 0}), is(new byte[]{1, 0}));
    }
}
