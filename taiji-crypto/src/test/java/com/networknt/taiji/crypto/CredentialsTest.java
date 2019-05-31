package com.networknt.taiji.crypto;

import org.junit.jupiter.api.Test;
import java.security.KeyPair;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CredentialsTest {

    @Test
    public void testCredentialsFromString() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        Credentials credentials = Credentials.create(SampleKeys.KEY_PAIR, keyPair);
        verify(credentials);
    }

    private void verify(Credentials credentials) {
        assertThat(credentials.getAddress(), is(SampleKeys.ADDRESS));
        assertThat(credentials.getEcKeyPair(), is(SampleKeys.KEY_PAIR));
    }
}
