package com.networknt.taiji.console;

import static com.networknt.chain.utility.Collection.tail;
import static com.networknt.chain.utility.Console.exitError;

public class WalletRunner {

    private static final String USAGE = "wallet create|update|send|fromkey|innertransfer|intertransfer";

    public static void run(String[] args) {
        main(args);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            exitError(USAGE);
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
                    exitError(USAGE);
            }
        }
    }

}
