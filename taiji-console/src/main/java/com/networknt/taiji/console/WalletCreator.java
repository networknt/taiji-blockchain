package com.networknt.taiji.console;

import com.networknt.taiji.crypto.CipherException;
import com.networknt.taiji.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.networknt.taiji.console.Cli.exitError;

public class WalletCreator extends WalletManager {

    public WalletCreator() {
    }

    public WalletCreator(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        new WalletCreator().run();
    }

    static void main(IODevice console) {
        new WalletCreator(console).run();
    }

    private void run() {
        String password = getPassword("Please enter a wallet file password: ");
        String chainId = getChainId("Please choose your chainId: \nAmericas 0000 \nAsia,Oceania 0001\nEurope,Africa 0002\n");
        String destinationDir = getDestinationDir();
        File destination = createDir(destinationDir);

        try {
            String walletFileName = WalletUtils.generateFullNewWalletFile(password, destination, chainId);
            console.printf("Wallet file " + walletFileName
                    + " successfully created in: " + destinationDir + "\n");
        } catch (CipherException | IOException | InvalidAlgorithmParameterException
                | NoSuchAlgorithmException | NoSuchProviderException e) {
            exitError(e);
        }
    }

}
