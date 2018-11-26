package com.networknt.taiji.console;

import com.networknt.chain.utility.Console;
import com.networknt.monad.Result;
import com.networknt.status.Status;
import com.networknt.taiji.avro.AvroSerializer;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.event.BlockchainEvent;
import com.networknt.taiji.event.EventId;
import com.networknt.taiji.token.TokenCreatedEvent;

import java.nio.ByteBuffer;
import java.util.List;

import static com.networknt.chain.utility.Console.exitError;

public class TokenCreator extends WalletManager {
    public TokenCreator() {
    }

    public TokenCreator(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        new TokenCreator().run();
    }

    static void main(IODevice console) {
        new TokenCreator(console).run();
    }

    private void run() {
        String currency = getCurrency();
        String ownerAddress = getOwnerAddress();
        Credentials credentials = loadWalletFromAddress(ownerAddress);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");
        console.printf("Generate token address (this may take a few minutes)...\n");
        String tokenAddress = null;
        try {
            tokenAddress = AddrGen.generateAddress(ownerAddress.substring(0, 4));
        } catch(Exception e) {
            exitError(e);
        }
        console.printf("Token address is generated (please write it down): " + tokenAddress + "\n");
        String name = getName();
        String symbol = getSymbol();
        Long l = getTotal();
        Integer decimals = getDecimals();
        long total = l * 10^decimals;

        TokenCreatedEvent tokenCreatedEvent = new TokenCreatedEvent(ownerAddress, tokenAddress, name, symbol, total, decimals);
        AvroSerializer serializer = new AvroSerializer();
        byte[] tokenCreatedEventBytes = serializer.serialize(tokenCreatedEvent);

        // get number of transactions from the chain-reader to generate eventId.
        long nonce = 0;
        Result<List<SignedLedgerEntry>> result = TaijiClient.getTransaction(ownerAddress, currency);
        if(result.isSuccess()) {
            nonce = result.getResult().size();
        } else {
            exitError(result.getError().toString());
        }

        EventId eventId = EventId.newBuilder()
                .setAddress(ownerAddress)
                .setNonce(nonce)
                .build();

        BlockchainEvent blockchainEvent = BlockchainEvent.newBuilder()
                .setEventId(eventId)
                .setEntityAddress(tokenAddress)
                .setEntityType("com.networknt.taiji.entity.Token")
                .setEventType(TokenCreatedEvent.class.getName())
                .setEventData(ByteBuffer.wrap(serializer.serialize(tokenCreatedEvent)))
                .build();

        byte[] bytes = serializer.serialize(blockchainEvent);


        // here we just create a credit entry only on with the toAddress the token address and value 0
        LedgerEntry creditEntry = new LedgerEntry(tokenAddress, 0, bytes);
        RawTransaction rtx = new RawTransaction(currency);
        rtx.addCreditEntry(tokenAddress, creditEntry);
        // calculate the fee for the debit entry
        Result<Fee> feeResult = TaijiClient.getFee(ownerAddress, currency);
        Fee fee = null;
        if(feeResult.isSuccess()) {
            fee = feeResult.getResult();
        } else {
            exitError(feeResult.getError().toString());
        }
        LedgerEntry feeEntry = new LedgerEntry(fee.getBankAddress(), fee.getApplication());
        rtx.addCreditEntry(fee.getBankAddress(), feeEntry);
        rtx.addDebitEntry(ownerAddress, feeEntry);
        SignedTransaction stx = TransactionManager.signTransaction(rtx, credentials);

        Status status = TaijiClient.postTx(credentials.getAddress().substring(0, 4), stx);
        if(status != null && status.getStatusCode() == 200) {
            Console.exitSuccess((String.format("Token has been created successfully for owner address %s, token address %s with status %s%n",
                    ownerAddress,
                    tokenAddress,
                    status.toString())));
        } else {
            if(status == null) {
                Console.exitError("Nothing returned from the API call, check connectivity");
            } else {
                Console.exitError(status.toString());
            }
        }
    }

    private String getCurrency() {
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

    private String getOwnerAddress() {
        String ownerAddress = console.readLine("What address (source address) to own the token: ").trim();
        if(ownerAddress.equals("")) {
            exitError("You need to enter a real source address.");
        }
        return ownerAddress;
    }

    private String getName() {
        String name = console.readLine("What is the token name (it can be a long name or description: ").trim();
        if(name.equals("")) {
            exitError("You need to enter a name of the token.");
        }
        return name;
    }

    private String getSymbol() {
        String symbol = console.readLine("What is the symbol for the token. (like stock symbol one word upper case): ").trim();
        if(symbol.equals("")) {
            exitError("You need to enter a symbol of the token.");
        }
        return symbol;
    }

    private Long getTotal() {
        String total = console.readLine("What is the total supply of the token (please enter a numeric value): ").trim();
        try {
            return new Long(total);
        } catch (NumberFormatException e) {
            exitError("Invalid total specified");
        }
        throw new RuntimeException("Application exit failure");
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

    private Integer getDecimals() {
        String decimals = console.readLine("What is the number of decimals to support (default 9): ").trim();
        if(decimals.equals("")) {
            return new Integer(9);
        } else {
            try {
                return new Integer(decimals);
            } catch (NumberFormatException e) {
                exitError("Invalid decimals specified");
            }
        }
        throw new RuntimeException("Application exit failure");
    }

}
