package com.networknt.taiji.console;

import static com.networknt.chain.utility.Collection.tail;
import static com.networknt.chain.utility.Console.exitError;

public class Cli {
    private static String USAGE = "Usage: taiji wallet";
    private static String LOGO = "\n" // generated at http://patorjk.com/software/taag
         + ".########....###....####.......##.####\n"
         + "....##......##.##....##........##..##.\n"
         + "....##.....##...##...##........##..##.\n"
         + "....##....##.....##..##........##..##.\n"
         + "....##....#########..##..##....##..##.\n"
         + "....##....##.....##..##..##....##..##.\n"
         + "....##....##.....##.####..######..####\n";


    public static void main(String[] args) throws Exception {
        System.out.println(LOGO);

        if (args.length < 1) {
            exitError(USAGE);
        } else {
            switch (args[0]) {
                case "wallet":
                    WalletRunner.run(tail(args));
                    break;
                case "token":
                    TokenRunner.run(tail(args));
                    break;
                default:
                    exitError(USAGE);
            }
        }
    }
}
