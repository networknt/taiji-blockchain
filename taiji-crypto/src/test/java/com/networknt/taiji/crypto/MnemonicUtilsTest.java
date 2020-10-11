package com.networknt.taiji.crypto;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for {@link MnemonicUtils} utility class.
 */
public class MnemonicUtilsTest {

    /**
     * The initial entropy for the current test vector. This entropy should be used
     * to generate mnemonic and seed.
     */
    private byte[] initialEntropy;

    /**
     * Expected mnemonic for the given {@link #initialEntropy}.
     */
    private String mnemonic;

    /**
     * Expected seed based on the calculated {@link #mnemonic} and default passphrase.
     */
    private byte[] seed;

    public MnemonicUtilsTest(String initialEntropy, String mnemonic, String seed) {
        this.initialEntropy = Hex.decode(initialEntropy);
        this.mnemonic = mnemonic;
        this.seed = Hex.decode(seed);
    }

    @Test
    public void generateMnemonicShouldGenerateExpectedMnemonicWords() {
        String actualMnemonic = MnemonicUtils.generateMnemonic(initialEntropy);

        assertEquals(mnemonic, actualMnemonic);
    }

    @Test
    public void generateSeedShouldGenerateExpectedSeeds() {
        byte[] actualSeed = MnemonicUtils.generateSeed(mnemonic, "TREZOR");

        assertArrayEquals(seed, actualSeed);
    }

    @Test
    public void generateEntropyShouldGenerateExpectedEntropy() {
        byte[] actualEntropy = MnemonicUtils.generateEntropy(mnemonic);

        assertArrayEquals(initialEntropy, actualEntropy);
    }
}
