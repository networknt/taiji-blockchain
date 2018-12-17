package com.networknt.taiji.crypto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.chain.utility.Numeric;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static com.networknt.chain.utility.Hash.sha256;
import static com.networknt.config.Config.LIGHT_4J_CONFIG_DIR;
import static com.networknt.taiji.crypto.Keys.ADDRESS_LENGTH_IN_HEX;
import static com.networknt.taiji.crypto.Keys.PRIVATE_KEY_LENGTH_IN_HEX;


public class WalletUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String generateFullNewWalletFile(String password, File destinationDirectory, String chainId)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {

        return generateNewWalletFile(password, destinationDirectory, chainId, true);
    }

    public static String generateNewWalletFile(String password, File destinationDirectory, String chainId)
            throws CipherException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return generateFullNewWalletFile(password, destinationDirectory, chainId);
    }

    public static String generateNewWalletFile(
            String password, File destinationDirectory, String chainId, boolean useFullScrypt)
            throws CipherException, IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        AddressGenerator generator = new AddressGenerator(chainId);
        ECKeyPair ecKeyPair = generator.generate();
        KeyPair encryptingKeyPair = Keys.createCipherKeyPair();
        return generateWalletFile(password, ecKeyPair, encryptingKeyPair, destinationDirectory, useFullScrypt);
    }

    public static String generateWalletFile(
            String password, ECKeyPair ecKeyPair, KeyPair encryptingKeyPair, File destinationDirectory, boolean useFullScrypt)
            throws CipherException, IOException {

        WalletFile walletFile;
        walletFile = Wallet.createStandard(password, ecKeyPair, encryptingKeyPair);
        String fileName = getWalletFileName(walletFile);
        File destination = new File(destinationDirectory, fileName);

        objectMapper.writeValue(destination, walletFile);

        return fileName;
    }

    public static Credentials loadCredentials(String password, String source)
            throws IOException, CipherException, NoSuchAlgorithmException, InvalidKeySpecException {
        return loadCredentials(password, new File(source));
    }

    public static Credentials loadCredentials(String password, File source)
            throws IOException, CipherException, NoSuchAlgorithmException, InvalidKeySpecException {
        WalletFile walletFile = objectMapper.readValue(source, WalletFile.class);
        return Credentials.create(Wallet.decryptSigningKeyPair(password, walletFile), Wallet.decryptEncryptingKeyPair(password, walletFile));
    }

    public static Credentials loadCredentials(String password, InputStream is)
            throws IOException, CipherException, NoSuchAlgorithmException, InvalidKeySpecException {
        WalletFile walletFile = objectMapper.readValue(is, WalletFile.class);
        return Credentials.create(Wallet.decryptSigningKeyPair(password, walletFile), Wallet.decryptEncryptingKeyPair(password, walletFile));
    }

    private static String getWalletFileName(WalletFile walletFile) {
        return walletFile.getAddress() + ".json";
    }

    public static String getDefaultKeyDirectory() {
        return System.getProperty(LIGHT_4J_CONFIG_DIR);
    }

    public static boolean isValidPrivateKey(String privateKey) {
        String cleanPrivateKey = Numeric.cleanHexPrefix(privateKey);
        return cleanPrivateKey.length() == PRIVATE_KEY_LENGTH_IN_HEX;
    }
}
