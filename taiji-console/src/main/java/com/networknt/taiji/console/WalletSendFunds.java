package com.networknt.taiji.console;

import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.utility.Converter;

import java.io.File;

import static com.networknt.chain.utility.Console.exitError;

public class WalletSendFunds extends WalletManager {

    private static final String USAGE = "send <currency> <walletfile> <destination-address>";

    public static void main(String[] args) {
        if (args.length != 2) {
            exitError(USAGE);
        } else {
            new WalletSendFunds().run(args[0], args[1], args[2]);
        }
    }

    private void run(String currency, String walletFileLocation, String destinationAddress) {
        File walletFile = new File(walletFileLocation);
        Credentials credentials = getCredentials(walletFile);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");

        if (!WalletUtils.isValidAddress(destinationAddress)) {
            exitError("Invalid destination address specified");
        }

        long amountToTransfer = getAmountToTransfer();
        Converter.Unit transferUnit = getTransferUnit();
        long amountInShell = Converter.toShell(amountToTransfer, transferUnit);

        confirmTransfer(amountToTransfer, transferUnit, amountInShell, destinationAddress);

        // here we just create a simple transaction with one debit entry and one credit entry.
        LedgerEntry ledgerEntry = new LedgerEntry(destinationAddress, amountInShell);
        RawTransaction rtx = new RawTransaction(currency);
        rtx.addCreditEntry(destinationAddress, ledgerEntry);
        rtx.addDebitEntry(credentials.getAddress(), ledgerEntry);
        SignedTransaction stx = TransactionManager.signTransaction(rtx, credentials);

        TransactionReceipt transactionReceipt = TaijiClient.postTx(stx);
        console.printf("Funds have been successfully transferred from %s to %s%n"
                        + "RawTransaction hash: %s%nMined block number: %s%n",
                credentials.getAddress(),
                destinationAddress);
    }

    private Long getAmountToTransfer() {
        String amount = console.readLine("What amount would you like to transfer "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new Long(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    private Converter.Unit getTransferUnit() {
        String unit = console.readLine("Please specify the unit (shell, kshell, mshell, taiji, ktaiji or mtaiji) [taiji]: ")
                .trim();

        Converter.Unit transferUnit;
        if (unit.equals("")) {
            transferUnit = Converter.Unit.TAIJI;
        } else {
            transferUnit = Converter.Unit.fromString(unit.toLowerCase());
        }

        return transferUnit;
    }

    private void confirmTransfer(
            long amountToTransfer, Converter.Unit transferUnit, long amountInShell,
            String destinationAddress) {

        console.printf("Please confirm that you wish to transfer %s %s (%s %s) to address %s%n",
                amountToTransfer, transferUnit,
                amountInShell,
                Converter.Unit.SHELL, destinationAddress);
        String confirm = console.readLine("Please type 'yes' to proceed: ").trim();
        if (!confirm.toLowerCase().equals("yes")) {
            exitError("OK, some other time perhaps...");
        }
    }
}
