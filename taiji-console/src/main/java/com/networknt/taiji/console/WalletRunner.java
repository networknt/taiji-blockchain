package com.networknt.taiji.console;

import org.web3j.codegen.Console;
import org.web3j.console.KeyImporter;
import org.web3j.console.WalletSendFunds;
import org.web3j.console.WalletUpdater;

import static org.web3j.utils.Collection.tail;

public class WalletRunner {

    private static final String USAGE = "wallet create|update|send|fromkey|innertransfer|intertransfer";

    public static void run(String[] args) {
        main(args);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            Console.exitError(USAGE);
        } else {
            switch (args[0]) {
                case "create":
                    WalletCreator.main(new String[] {});
                    break;
                case "update":
                    WalletUpdater.main(tail(args));
                    break;
                case "send":
                    WalletSendFunds.main(tail(args));
                    break;
                case "fromkey":
                    KeyImporter.main(tail(args));
                    break;
                case "innertransfer":
                    InnerChainTransfer.main(tail(args));
                    break;
                case "intertransfer":
                    InterChainTransfer.main(tail(args));
                    break;
                default:
                    Console.exitError(USAGE);
            }
        }
    }

}
