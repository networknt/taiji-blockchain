package com.networknt.taiji.console;

import com.networknt.config.Config;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.web3j.codegen.Console.exitError;

public class InterChainTransfer extends WalletManager {
    public static String password = "123456";
    public static String wallet1 = "01b928ccc352743b98aba5ef919e0f8731db47d2.json";
    public static String wallet2 = "02fb109a4d091056d811ae022cb8c2a78f050c33.json";
    public static String wallet3 = "0359c5ff6b1f816e8f7ff9e90c8e5abd34927058.json";

    private static final String USAGE = "transfer <1-1|1-N|N-1> <times>";

    public static void main(String[] args) {
        if (args.length != 2) {
            exitError(USAGE);
        } else {
            new InterChainTransfer().run(args[0], args[1]);
        }
    }

    private void run(String mode, String times) {
        // init credentials for three accounts.
        List<Credentials> credentialsList = new ArrayList<>();
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet1)));
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet2)));
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet3)));
        Integer i = Integer.valueOf(times);
        BigDecimal amountInWei = Convert.toWei("1", Convert.Unit.ETHER);
        BigInteger value = amountInWei.toBigIntegerExact();
        switch(mode) {
            case "1-1":
                oneToOne(credentialsList, value, i);
                break;
            case "1-N":
                oneToN(credentialsList, value, i);
                break;
            case "N-1":
                nToOne(credentialsList, value, i);
                break;
            default:
                exitError("Invalid transfer mode. Only 1-1 or 1-N or N-1 is supported");

        }
    }

    private void oneToOne(List<Credentials> list, BigInteger value, int times) {
        for(int i = 0; i < times; i++) {
            Collections.shuffle(list);
            // transfer from first account to the second account in the list.
            DebitEntry debitEntry = new DebitEntry(list.get(1).getAddress(), value, BigInteger.TEN, BigInteger.TEN);
            CreditEntry creditEntry = new CreditEntry(list.get(1).getAddress(), value);
            RawTransaction rtx = new RawTransaction();
            rtx.addCreditEntry(creditEntry);
            rtx.addDebitEntry(debitEntry);
            SignedTransaction stx = TransactionManager.signTransaction(rtx, list.get(0));
            TransactionReceipt transactionReceipt = TaijiClient.postTx(stx);
        }
    }

    private void oneToN(List<Credentials> list, BigInteger value, int times) {
        for(int i = 0; i < times; i++) {
            Collections.shuffle(list);
            // transfer from the first account to second and third accounts.
            DebitEntry debit1 = new DebitEntry(list.get(1).getAddress(), value, BigInteger.TEN, BigInteger.TEN);
            CreditEntry credit1 = new CreditEntry(list.get(1).getAddress(), value);

            DebitEntry debit2 = new DebitEntry(list.get(2).getAddress(), value, BigInteger.TEN, BigInteger.TEN);
            CreditEntry credit2 = new CreditEntry(list.get(2).getAddress(), value);

            RawTransaction rtx = new RawTransaction();
            rtx.addCreditEntry(credit1);
            rtx.addCreditEntry(credit2);
            rtx.addDebitEntry(debit1);
            rtx.addDebitEntry(debit2);
            SignedTransaction stx = TransactionManager.signTransaction(rtx, list.get(0));
            TransactionReceipt transactionReceipt = TaijiClient.postTx(stx);
        }
    }

    private void nToOne(List<Credentials> list, BigInteger value, int times) {
        for(int i = 0; i < times; i++) {
            Collections.shuffle(list);
            // transfer first and second accounts to third account
            DebitEntry debit1 = new DebitEntry(list.get(2).getAddress(), value, BigInteger.TEN, BigInteger.TEN);
            CreditEntry credit1 = new CreditEntry(list.get(2).getAddress(), value);

            DebitEntry debit2 = new DebitEntry(list.get(2).getAddress(), value, BigInteger.TEN, BigInteger.TEN);
            CreditEntry credit2 = new CreditEntry(list.get(2).getAddress(), value);

            List<byte[]> signedCreditEntries = new ArrayList<>();
            signedCreditEntries.add(CreditEntryEncoder.signMessage(credit1, list.get(0)));
            signedCreditEntries.add(CreditEntryEncoder.signMessage(credit2, list.get(1)));

            List<byte[]> signedDebitEntries = new ArrayList<>();
            signedDebitEntries.add(DebitEntryEncoder.signMessage(debit1, list.get(0)));
            signedDebitEntries.add(DebitEntryEncoder.signMessage(debit2, list.get(1)));

            SignedTransaction stx = TransactionManager.createSignedTransacton(signedCreditEntries, signedDebitEntries);
            TransactionReceipt transactionReceipt = TaijiClient.postTx(stx);
        }
    }
}
