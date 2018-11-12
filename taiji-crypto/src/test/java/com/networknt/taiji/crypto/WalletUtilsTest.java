package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;
import com.networknt.config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static com.networknt.chain.utility.Hash.sha256;
import static com.networknt.taiji.crypto.SampleKeys.CREDENTIALS;
import static com.networknt.taiji.crypto.SampleKeys.KEY_PAIR;
import static com.networknt.taiji.crypto.SampleKeys.PASSWORD;
import static com.networknt.taiji.crypto.WalletUtils.isValidAddress;
import static com.networknt.taiji.crypto.WalletUtils.isValidPrivateKey;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@Ignore
public class WalletUtilsTest {

    private File tempDir;
    private String chainId = "0000";

    @Before
    public void setUp() throws Exception {
        tempDir = createTempDir();
    }

    @After
    public void tearDown() throws Exception {
        for (File file:tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
    }

    @Test
    public void testGenerateBip39Wallets() throws Exception {
        Bip39Wallet wallet = WalletUtils.generateBip39Wallet(PASSWORD, tempDir);
        byte[] seed = MnemonicUtils.generateSeed(wallet.getMnemonic(), PASSWORD);
        Credentials credentials = Credentials.create(ECKeyPair.create(sha256(seed)));

        assertEquals(credentials, WalletUtils.loadBip39Credentials(PASSWORD, wallet.getMnemonic()));
    }

    @Test
    public void testGenerateFullNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateFullNewWalletFile(PASSWORD, tempDir, chainId);
        testGeneratedNewWalletFile(fileName);
    }

    @Test
    public void testGenerateNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateNewWalletFile(PASSWORD, tempDir, chainId);
        testGeneratedNewWalletFile(fileName);
    }


    @Test
    public void testGenerateLightNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateLightNewWalletFile(PASSWORD, tempDir, chainId);
        testGeneratedNewWalletFile(fileName);
    }

    private void testGeneratedNewWalletFile(String fileName) throws Exception {
        WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
    }

    @Test
    public void testGenerateFullWalletFile() throws Exception {
        String fileName = WalletUtils.generateWalletFile(PASSWORD, KEY_PAIR, tempDir, true);
        testGenerateWalletFile(fileName);
    }

    @Test
    public void testGenerateLightWalletFile() throws Exception {
        String fileName = WalletUtils.generateWalletFile(PASSWORD, KEY_PAIR, tempDir, false);
        testGenerateWalletFile(fileName);
    }

    private void testGenerateWalletFile(String fileName) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD, new File(tempDir, fileName));

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Test
    public void testLoadCredentialsFromStream() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                Config.getInstance().getInputStreamFromFile("ef678007d18427e6022059dbc264f27507cd1ffc.json"));
        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Ignore  // enable if users need to work with MyWallet
    @Test
    public void testLoadCredentialsMyWallet() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                Config.getInstance().getInputStreamFromFile("4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818.json"));
        assertThat(credentials, equalTo(
                Credentials.create(
                        "6ca4203d715e693279d6cd9742ad2fb7a3f6f4abe27a64da92e0a70ae5d859c9")));
    }

    private static File createTempDir() throws Exception {
        return Files.createTempDirectory(
                WalletUtilsTest.class.getSimpleName() + "-testkeys").toFile();
    }

    @Test
    public void testIsValidPrivateKey() {
        assertTrue(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING));
        assertTrue(isValidPrivateKey(Numeric.prependHexPrefix(SampleKeys.PRIVATE_KEY_STRING)));

        assertFalse(isValidPrivateKey(""));
        assertFalse(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING + "a"));
        assertFalse(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING.substring(1)));
    }

    @Test
    public void testIsValidAddress() {
        assertTrue(isValidAddress(SampleKeys.ADDRESS));
        assertTrue(isValidAddress(SampleKeys.ADDRESS_NO_PREFIX));

        assertFalse(isValidAddress(""));
        assertFalse(isValidAddress(SampleKeys.ADDRESS + 'a'));
        assertFalse(isValidAddress(SampleKeys.ADDRESS.substring(1)));
    }
}
