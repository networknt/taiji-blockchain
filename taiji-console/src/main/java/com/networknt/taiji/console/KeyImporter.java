package com.networknt.taiji.console;

import com.networknt.chain.utility.Files;
import com.networknt.taiji.crypto.CipherException;
import com.networknt.taiji.crypto.Credentials;
import com.networknt.taiji.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

import static com.networknt.chain.utility.Console.exitError;
import static com.networknt.taiji.crypto.Keys.PRIVATE_KEY_LENGTH_IN_HEX;

/**
 * Create Ethereum wallet file from a provided private key.
 */
public class KeyImporter extends WalletManager {

    public KeyImporter() {
    }

    public KeyImporter(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            new KeyImporter().run(args[0]);
        } else {
            new KeyImporter().run();
        }
    }

    static void main(IODevice console) {
        new KeyImporter(console).run();
    }

    private void run(String input) {
        File keyFile = new File(input);

        if (keyFile.isFile()) {
            String privateKey = null;
            try {
                privateKey = Files.readString(keyFile);
            } catch (IOException e) {
                exitError("Unable to read file " + input);
            }

            createWalletFile(privateKey.trim());
        } else {
            createWalletFile(input.trim());
        }
    }

    private void run() {
        String input = console.readLine(
                "Please enter the hex encoded private key or key file location: ").trim();
        run(input);
    }

    private void createWalletFile(String privateKey) {
        if (!WalletUtils.isValidPrivateKey(privateKey)) {
            exitError("Invalid private key specified, must be "
                    + PRIVATE_KEY_LENGTH_IN_HEX
                    + " digit hex value");
        }

        String password = getPassword("Please enter a wallet file password: ");

        String destinationDir = getDestinationDir();
        File destination = createDir(destinationDir);

        try {
            Credentials credentials = Credentials.create(privateKey);
            String walletFileName = WalletUtils.generateWalletFile(
                    password, credentials.getEcKeyPair(), credentials.getEncryptingKeyPair(), destination, true);
            console.printf("Wallet file " + walletFileName
                    + " successfully created in: " + destinationDir + "\n");
        } catch (Exception e) {
            exitError(e);
        }
    }
}
