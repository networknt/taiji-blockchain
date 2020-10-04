package com.networknt.taiji.console;

import com.networknt.chain.utility.Console;
import com.networknt.config.JsonMapper;
import com.networknt.kafka.common.AvroSerializer;
import com.networknt.monad.Result;
import com.networknt.status.Status;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.event.EventId;
import com.networknt.taiji.token.TokenCreatedEvent;
import com.networknt.taiji.utility.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.networknt.chain.utility.Console.exitError;

public class TokenCreator extends TokenManager {
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
            AddressGenerator generator = new AddressGenerator(ownerAddress.substring(0, 4));
            tokenAddress = Keys.getAddress(generator.generate());
        } catch(Exception e) {
            exitError(e);
        }
        console.printf("Token address is generated (please write it down): " + tokenAddress + "\n");
        String name = getName();
        String symbol = getSymbol();
        String description = getDescription();
        Long l = getTotal();
        Integer decimals = getDecimals();
        long factor = Converter.power(10, decimals);
        long total = l * factor;

        // get number of transactions from the chain-reader to generate eventId.
        long nonce = 0;
        Result<String> result = TaijiClient.getTransaction(ownerAddress, currency, 0, 1); // get the last transaction
        if(result.isSuccess()) {
            List<Map<String, Object>> txs = JsonMapper.string2List(result.getResult());
            nonce = (Long)txs.get(0).get("no") + 1;
        } else {
            exitError(result.getError().toString());
        }
        EventId eventId = EventId.newBuilder()
                .setAddress(ownerAddress)
                .setNonce(nonce)
                .build();
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("name", name);
        valueMap.put("description", description);

        TokenCreatedEvent tokenCreatedEvent = TokenCreatedEvent.newBuilder()
            .setEventId(eventId)
            .setCurrency(currency)
            .setEntityAddress(tokenAddress)
            .setValue(JsonMapper.toJson(valueMap))
            .setSymbol(symbol)
            .setTotalSupply(total)
            .setDecimals(decimals)
            .build();

        AvroSerializer serializer = new AvroSerializer();
        byte[] bytes = serializer.serialize(tokenCreatedEvent);

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
