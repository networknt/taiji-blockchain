package com.networknt.taiji.console;

import com.networknt.chain.utility.Console;
import com.networknt.config.Config;
import com.networknt.status.Status;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.utility.Converter;

import java.util.*;

import static com.networknt.chain.utility.Console.exitError;


/**
 * Transfer one tjc within the america chain. It will randomly choose two account for
 * fund transfer so that it won't deplete one particular account very soon.
 *
 * @author Steve Hu
 */
public class InnerChainTransfer extends WalletManager {
    public static String password = "123456";
    public static String wallet1 = "00000A3A878440702AbC03d4424d979fc67e2bBa.json";
    public static String wallet2 = "000050f134b8B0Db3D9Cc3F6cB262D4d4445Ee28.json";
    public static String wallet3 = "0000344d315050CA9B0938B6511EC342705a1f9c.json";

    private static final String USAGE = "transfer <currency> <1-1|1-N> <times>";

    public static void main(String[] args) {
        if (args.length != 3) {
            exitError(USAGE);
        } else {
            new InnerChainTransfer().run(args[0], args[1], args[2]);
        }
    }

    private void run(String currency, String mode, String times) {
        // init credentials for three accounts.
        List<Credentials> credentialsList = new ArrayList<>();
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet1)));
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet2)));
        credentialsList.add(getCredentials(password, Config.getInstance().getInputStreamFromFile(wallet3)));
        Integer i = Integer.valueOf(times);
        switch(mode) {
            case "1-1":
                oneToOne(credentialsList, currency, Converter.toShell(1, Converter.Unit.TAIJI), i);
                break;
            case "1-N":
                oneToN(credentialsList, currency, Converter.toShell(2, Converter.Unit.TAIJI), i);
                break;
            default:
                exitError("Invalid transfer mode. Only 1-1 or 1-N is supported");

        }
    }

    private void oneToOne(List<Credentials> list, String currency, long value, int times) {
        for(int i = 0; i < times; i++) {
            Collections.shuffle(list);
            // transfer from first account 0 to the second account 1 in the list.
            LedgerEntry ledgerEntry = new LedgerEntry(list.get(1).getAddress(), value);
            RawTransaction rtx = new RawTransaction(currency);
            rtx.addCreditEntry(list.get(1).getAddress(), ledgerEntry);
            rtx.addDebitEntry(list.get(0).getAddress(), ledgerEntry);
            SignedTransaction stx = TransactionManager.signTransaction(rtx, list.get(0));
            try {System.out.println(Config.getInstance().getMapper().writeValueAsString(stx)); } catch (Exception e) {}
            Status status = TaijiClient.postTx(list.get(0).getAddress().substring(0, 4), stx);
            if(status != null && status.getStatusCode() == 200) {
                Console.exitSuccess((String.format("Funds have been successfully transferred %s from %s to %s with status %s%n",
                        value,
                        list.get(0).getAddress(),
                        list.get(1).getAddress(),
                        status.toString())));
            } else {
                if(status == null) {
                    Console.exitError("Nothing returned from the API call, check connectivity");
                } else {
                    Console.exitError(status.toString());
                }
            }
        }
    }

    private void oneToN(List<Credentials> list, String currency, long value, int times) {
        for(int i = 0; i < times; i++) {
            Collections.shuffle(list);
            // transfer from the first account to second and third accounts.
            LedgerEntry debit1 = new LedgerEntry(list.get(1).getAddress(), value/2);
            LedgerEntry debit2 = new LedgerEntry(list.get(2).getAddress(), value/2);

            LedgerEntry credit1 = new LedgerEntry(list.get(1).getAddress(), value/2);
            LedgerEntry credit2 = new LedgerEntry(list.get(2).getAddress(), value/2);

            RawTransaction rtx = new RawTransaction(currency);
            rtx.addCreditEntry(list.get(1).getAddress(), credit1);
            rtx.addCreditEntry(list.get(2).getAddress(), credit2);
            rtx.addDebitEntry(list.get(0).getAddress(), debit1);
            rtx.addDebitEntry(list.get(0).getAddress(), debit2);
            SignedTransaction stx = TransactionManager.signTransaction(rtx, list.get(0));
            Status status = TaijiClient.postTx(list.get(0).getAddress().substring(0, 4), stx);
            if(status != null && status.getStatusCode() == 200) {
                Console.exitSuccess((String.format("Funds have been successfully transferred %s from %s to %s with status %s%n",
                        value,
                        list.get(0).getAddress(),
                        list.get(1).getAddress() + " and " + list.get(2).getAddress(),
                        status.toString())));
            } else {
                if(status == null) {
                    Console.exitError("Nothing returned from the API call, check connectivity");
                } else {
                    Console.exitError(status.toString());
                }
            }
        }
    }
}
