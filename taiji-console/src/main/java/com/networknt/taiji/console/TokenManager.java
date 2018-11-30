package com.networknt.taiji.console;

import com.networknt.config.Config;
import com.networknt.taiji.crypto.CipherException;
import com.networknt.taiji.crypto.Credentials;
import com.networknt.taiji.crypto.WalletUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.networknt.chain.utility.Console.exitError;

public class TokenManager {

    final IODevice console;

    TokenManager() {
        console = new ConsoleDevice();

        if (console == null) {
            exitError("Unable to access console - please ensure you are running "
                    + "from the command line");
        }
    }

    TokenManager(IODevice console) {
        this.console = console;
    }


    String getCurrency() {
        String currency = console.readLine("What currency chain do you want your token to attach [taiji]: ")
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

    String getOwnerAddress() {
        String ownerAddress = console.readLine("What address (source address) to own the token: ").trim();
        if(ownerAddress.equals("")) {
            exitError("You need to enter a real source address.");
        }
        return ownerAddress;
    }

    String getTokenAddress() {
        String tokenAddress = console.readLine("What is the token address: ").trim();
        if(tokenAddress.equals("")) {
            exitError("You need to enter a real token address.");
        }
        return tokenAddress;
    }

    String getApprovedAddress() {
        String approvedAddress = console.readLine("What is the address to grant allowance: ").trim();
        if(approvedAddress.equals("")) {
            exitError("You need to enter a real approved address.");
        }
        return approvedAddress;
    }

    String getTransferredAddress() {
        String transferredAddress = console.readLine("What is the address to transfer: ").trim();
        if(transferredAddress.equals("")) {
            exitError("You need to enter a real transfer address.");
        }
        return transferredAddress;
    }

    String getWithdrawFromAddress() {
        String withdrawAddress = console.readLine("What is the address to withdraw from: ").trim();
        if(withdrawAddress.equals("")) {
            exitError("You need to enter a real withdraw address.");
        }
        return withdrawAddress;
    }

    Long getApproveAmount() {
        String amount = console.readLine("What amount would you like to approve "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new Long(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    Long getTransferredAmount() {
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

    Long getWithdrawnAmount() {
        String amount = console.readLine("What amount would you like to withdraw "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new Long(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    Credentials loadWalletFromAddress(String address) {
        InputStream is = Config.getInstance().getInputStreamFromFile(address + ".json");
        while (true) {
            char[] password = console.readPassword(
                    "Please enter your existing wallet file password: ");
            String currentPassword = new String(password);
            try {
                return WalletUtils.loadCredentials(currentPassword, is);
            } catch (CipherException e) {
                console.printf("Invalid password specified\n");
            } catch (IOException e) {
                exitError("Unable to load wallet file for address : " + address + "\n" + e.getMessage());
            }
        }
    }
}
