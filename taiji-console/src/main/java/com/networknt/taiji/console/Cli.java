package com.networknt.taiji.console;


import org.web3j.codegen.Console;
import org.web3j.utils.Version;

import static org.web3j.utils.Collection.tail;

public class Cli {
    private static String USAGE = "Usage: taiji version|wallet";
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
            Console.exitError(USAGE);
        } else {
            switch (args[0]) {
                case "wallet":
                    WalletRunner.run(tail(args));
                    break;
                case "version":
                    Console.exitSuccess("Version: " + Version.getVersion() + "\n"
                            + "Build timestamp: " + Version.getTimestamp());
                    break;
                default:
                    Console.exitError(USAGE);
            }
        }
    }

}
