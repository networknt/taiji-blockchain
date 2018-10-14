package com.networknt.taiji.console;

import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.infura.InfuraHttpService;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.web3j.codegen.Console.exitError;

public class WalletSendFunds extends WalletManager {

    private static final String USAGE = "send <walletfile> <destination-address>";

    public static void main(String[] args) {
        if (args.length != 2) {
            exitError(USAGE);
        } else {
            new WalletSendFunds().run(args[0], args[1]);
        }
    }

    private void run(String walletFileLocation, String destinationAddress) {
        File walletFile = new File(walletFileLocation);
        Credentials credentials = getCredentials(walletFile);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");

        if (!WalletUtils.isValidAddress(destinationAddress)
                && !EnsResolver.isValidEnsName(destinationAddress)) {
            exitError("Invalid destination address specified");
        }

        BigDecimal amountToTransfer = getAmountToTransfer();
        Convert.Unit transferUnit = getTransferUnit();
        BigDecimal amountInWei = Convert.toWei(amountToTransfer, transferUnit);

        confirmTransfer(amountToTransfer, transferUnit, amountInWei, destinationAddress);

        // here we just create a simple transaction with one debit entry and one credit entry.
        LedgerEntry ledgerEntry = new LedgerEntry(destinationAddress, amountInWei.toBigIntegerExact());
        RawTransaction rtx = new RawTransaction();
        rtx.addCreditEntry(destinationAddress, ledgerEntry);
        rtx.addDebitEntry(credentials.getAddress(), ledgerEntry);
        SignedTransaction stx = TransactionManager.signTransaction(rtx, credentials);

        TransactionReceipt transactionReceipt = TaijiClient.postTx(stx);
        console.printf("Funds have been successfully transferred from %s to %s%n"
                        + "RawTransaction hash: %s%nMined block number: %s%n",
                credentials.getAddress(),
                destinationAddress);
    }

    private BigDecimal getAmountToTransfer() {
        String amount = console.readLine("What amound would you like to transfer "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    private Convert.Unit getTransferUnit() {
        String unit = console.readLine("Please specify the unit (ether, wei, ...) [ether]: ")
                .trim();

        Convert.Unit transferUnit;
        if (unit.equals("")) {
            transferUnit = Convert.Unit.ETHER;
        } else {
            transferUnit = Convert.Unit.fromString(unit.toLowerCase());
        }

        return transferUnit;
    }

    private void confirmTransfer(
            BigDecimal amountToTransfer, Convert.Unit transferUnit, BigDecimal amountInWei,
            String destinationAddress) {

        console.printf("Please confim that you wish to transfer %s %s (%s %s) to address %s%n",
                amountToTransfer.stripTrailingZeros().toPlainString(), transferUnit,
                amountInWei.stripTrailingZeros().toPlainString(),
                Convert.Unit.WEI, destinationAddress);
        String confirm = console.readLine("Please type 'yes' to proceed: ").trim();
        if (!confirm.toLowerCase().equals("yes")) {
            exitError("OK, some other time perhaps...");
        }
    }

    private Web3j getTaijiNode() {
        String clientAddress = console.readLine(
                "Please confirm address of running Ethereum client you wish to send "
                        + "the transfer request to [" + HttpService.DEFAULT_URL + "]: ")
                .trim();

        Web3j web3j;
        if (clientAddress.equals("")) {
            web3j = Web3j.build(new HttpService());
        } else if (clientAddress.contains("infura.io")) {
            web3j = Web3j.build(new InfuraHttpService(clientAddress));
        } else {
            web3j = Web3j.build(new HttpService(clientAddress));
        }

        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
            if (web3ClientVersion.hasError()) {
                exitError("Unable to process response from client: "
                        + web3ClientVersion.getError());
            } else {
                console.printf("Connected successfully to client: %s%n",
                        web3ClientVersion.getWeb3ClientVersion());
                return web3j;
            }
        } catch (InterruptedException | ExecutionException e) {
            exitError("Problem encountered verifying client: " + e.getMessage());
        }
        throw new RuntimeException("Application exit failure");
    }

}
