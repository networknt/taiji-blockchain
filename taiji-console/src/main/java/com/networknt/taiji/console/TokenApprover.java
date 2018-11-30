package com.networknt.taiji.console;

import com.networknt.chain.utility.Console;
import com.networknt.monad.Result;
import com.networknt.status.Status;
import com.networknt.taiji.avro.AvroSerializer;
import com.networknt.taiji.client.TaijiClient;
import com.networknt.taiji.crypto.*;
import com.networknt.taiji.event.EventId;
import com.networknt.taiji.token.TokenApprovedEvent;
import com.networknt.taiji.token.TokenCreatedEvent;

import java.util.List;

import static com.networknt.chain.utility.Console.exitError;

public class TokenApprover extends TokenManager {
    public TokenApprover() {
    }

    public TokenApprover(IODevice console) {
        super(console);
    }

    public static void main(String[] args) {
        new TokenApprover().run();
    }

    static void main(IODevice console) {
        new TokenApprover(console).run();
    }

    private void run() {
        String currency = getCurrency();
        String ownerAddress = getOwnerAddress();
        Credentials credentials = loadWalletFromAddress(ownerAddress);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");
        String tokenAddress = getTokenAddress();
        String approvedAddress = getApprovedAddress();
        Long l = getApproveAmount();
        // TODO we need to get the decimals for the token in order to calculate the exact number.
        long total = l * 10^9;

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

        TokenApprovedEvent tokenApprovedEvent = new TokenApprovedEvent(eventId, tokenAddress, approvedAddress, total);

        AvroSerializer serializer = new AvroSerializer();
        byte[] bytes = serializer.serialize(tokenApprovedEvent);

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
            Console.exitSuccess((String.format("Token has been approved successfully for owner address %s, token address %s to approved address %s with status %s%n",
                    ownerAddress,
                    tokenAddress,
                    approvedAddress,
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
