package com.networknt.taiji.console;

import static com.networknt.chain.utility.Collection.tail;
import static com.networknt.chain.utility.Console.exitError;

public class TokenRunner {
    private static final String USAGE = "token create|transfer|approve";

    public static void run(String[] args) {
        main(args);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            exitError(USAGE);
        } else {
            switch (args[0]) {
                case "create":
                    TokenCreator.main(new String[] {});
                    break;
                case "transfer":
                    TokenTransfer.main(tail(args));
                    break;
                case "approve":
                    TokenApprover.main(tail(args));
                    break;
                default:
                    exitError(USAGE);
            }
        }
    }
}
