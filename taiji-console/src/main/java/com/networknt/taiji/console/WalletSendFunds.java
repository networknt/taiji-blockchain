package com.networknt.taiji.console;

import com.networknt.chain.utility.Console;
import com.networknt.status.Status;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.utility.Converter;

import java.io.File;
import java.io.InputStream;

import static com.networknt.chain.utility.Console.exitError;

public class WalletSendFunds extends WalletManager {

    private static final String USAGE = "send <currency> <fromAddress> <toAddress>";

    public static void main(String[] args) {
        new WalletSendFunds().run();
    }

    private void run() {
        String currency = getCurrencyToTransfer();

        String fromAddress = getFromAddress();

        String toAddress = getToAddress();

        Credentials credentials = loadWalletFromAddress(fromAddress);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");

        if (!WalletUtils.isValidAddress(toAddress)) {
            exitError("Invalid destination address specified");
        }

        long amountToTransfer = getAmountToTransfer();
        Converter.Unit transferUnit = getTransferUnit();
        long amountInShell = Converter.toShell(amountToTransfer, transferUnit);

        confirmTransfer(amountToTransfer, transferUnit, amountInShell, toAddress);

        // here we just create a simple transaction with one debit entry and one credit entry.
        LedgerEntry ledgerEntry = new LedgerEntry(toAddress, amountInShell);
        RawTransaction rtx = new RawTransaction(currency);
        rtx.addCreditEntry(toAddress, ledgerEntry);
        rtx.addDebitEntry(credentials.getAddress(), ledgerEntry);
        SignedTransaction stx = TransactionManager.signTransaction(rtx, credentials);

        Status status = TaijiClient.postTx(credentials.getAddress().substring(0, 4), stx);
        if(status != null && status.getStatusCode() == 200) {
            Console.exitSuccess((String.format("Funds have been successfully transferred %s from %s to %s with status %s%n",
                    amountInShell,
                    credentials.getAddress(),
                    toAddress,
                    status.toString())));
        } else {
            if(status == null) {
                Console.exitError("Nothing returned from the API call, check connectivity");
            } else {
                Console.exitError(status.toString());
            }
        }
    }

    private String getCurrencyToTransfer() {
        String currency = console.readLine("What currency or token would you like to transfer [taiji]: ")
                .trim();
        if(currency.equals("")) {
            currency = "taiji";
        } else {
            if(!currency.equals("taiji")) {
                exitError("Unsupported currency or token.");
            }
        }
        return currency;
    }

    private String getFromAddress() {
        String fromAddress = console.readLine("What address (source address) to transfer from: ")
                .trim();
        if(fromAddress.equals("")) {
            exitError("You need to enter a real source address.");
        }
        return fromAddress;
    }

    private String getToAddress() {
        String toAddress = console.readLine("What address (destination address) to transfer to: ")
                .trim();
        if(toAddress.equals("")) {
            exitError("You need to enter a real destination address.");
        }
        return toAddress;
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
