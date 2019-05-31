package com.networknt.taiji.crypto;

import com.networknt.chain.utility.Numeric;
import com.networknt.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyPair;

import static com.networknt.taiji.crypto.SampleKeys.CREDENTIALS;
import static com.networknt.taiji.crypto.SampleKeys.KEY_PAIR;
import static com.networknt.taiji.crypto.SampleKeys.PASSWORD;
import static com.networknt.taiji.crypto.WalletUtils.isValidPrivateKey;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class WalletUtilsTest {

    private File tempDir;
    private String chainId = "0000";

    @BeforeEach
    public void setUp() throws Exception {
        tempDir = createTempDir();
    }

    @AfterEach
    public void tearDown() throws Exception {
        for (File file:tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
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

    private void testGeneratedNewWalletFile(String fileName) throws Exception {
        WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
    }

    @Test
    public void testGenerateFullWalletFile() throws Exception {
        KeyPair keyPair = Keys.createCipherKeyPair();
        String fileName = WalletUtils.generateWalletFile(PASSWORD, KEY_PAIR, keyPair, tempDir, true);
        testGenerateWalletFile(fileName);
    }

    private void testGenerateWalletFile(String fileName) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD, new File(tempDir, fileName));

        assertEquals(credentials, CREDENTIALS);
    }

    @Test
    public void testLoadCredentialsFromStream() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                Config.getInstance().getInputStreamFromFile("ef678007D18427E6022059Dbc264f27507CD1ffC.json"));
        assertEquals(credentials, CREDENTIALS);
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

}
