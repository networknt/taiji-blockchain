package com.networknt.taiji.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.networknt.client.Http2Client;
import com.networknt.cluster.Cluster;
import com.networknt.config.Config;
import com.networknt.exception.ApiException;
import com.networknt.monad.Failure;
import com.networknt.monad.Result;
import com.networknt.monad.Success;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.status.Status;
import com.networknt.taiji.crypto.Fee;
import com.networknt.taiji.crypto.SignedLedgerEntry;
import com.networknt.taiji.crypto.SignedTransaction;
import com.networknt.taiji.crypto.TransactionReceipt;
import com.networknt.taiji.event.JsonMapper;
import io.undertow.UndertowOptions;
import io.undertow.client.ClientConnection;
import io.undertow.client.ClientRequest;
import io.undertow.client.ClientResponse;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.OptionMap;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is the client that is responsible for service discovery and interact with
 * chain-writer and chain-reader services. It uses Consul for service lookup with
 * the bankId.
 *
 * @author Steve Hu
 */
public class TaijiClient {
    static final Logger logger = LoggerFactory.getLogger(TaijiClient.class);
    // service name
    static final String writerServiceId = "com.networknt.chainwriter-1.0.0";
    static final String readerServiceId = "com.networknt.chainreader-1.0.0";
    static final String tokenServiceId = "com.networknt.tokenreader-1.0.0";

    // Get the singleton Cluster instance
    static Cluster cluster = SingletonServiceFactory.getBean(Cluster.class);
    // Get the singleton Http2Client instance
    static Http2Client client = Http2Client.getInstance();

    static final String SUCCESS_OK = "SUC10200";
    static final String GENERIC_EXCEPTION = "ERR10014";
    /**
     * This is the API to write a single transaction to the Taiji blockchain.
     *
     * @param bankId The first 4 digits of the address
     * @param stx Signed transaction
     * @return Status
     */
    public static Status postTx(String bankId, SignedTransaction stx) {
        Status status = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", writerServiceId, bankId, null);
        try {
            String requestBody = Config.getInstance().getMapper().writeValueAsString(stx);
            logger.debug("requestBody = " + requestBody);
            // This is a connection that is shared by multiple requests and won't close until the app exits.
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.POST).setPath("/tx");
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            request.getRequestHeaders().put(Headers.CONTENT_TYPE, "application/json");
            request.getRequestHeaders().put(Headers.TRANSFER_ENCODING, "chunked");
            connection.sendRequest(request, client.createClientCallback(reference, latch, requestBody));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                status = Config.getInstance().getMapper().readValue(body, Status.class);
            } else {
                status = new Status(SUCCESS_OK);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            status = new Status(GENERIC_EXCEPTION, e.getMessage());
        }
        return status;
    }

    /**
     * This is the API to write multiple transactions to the Taiji blockchain in batch.
     *
     * @param bankId The first 4 digits of the address
     * @param stxs A list of signed transactions.
     * @return Status
     */
    public static Status postTxs(String bankId, List<SignedTransaction> stxs) {
        Status status = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", writerServiceId, bankId, null);

        try {
            String requestBody = Config.getInstance().getMapper().writeValueAsString(stxs);
            logger.debug("requestBody = " + requestBody);
            // This is a connection that is shared by multiple requests and won't close until the app exits.
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.POST).setPath("/txs");
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            request.getRequestHeaders().put(Headers.CONTENT_TYPE, "application/json");
            request.getRequestHeaders().put(Headers.TRANSFER_ENCODING, "chunked");
            connection.sendRequest(request, client.createClientCallback(reference, latch, requestBody));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                status = Config.getInstance().getMapper().readValue(body, Status.class);
            } else {
                status = new Status(SUCCESS_OK);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            status = new Status(GENERIC_EXCEPTION, e.getMessage());
        }
        return status;
    }

    /**
     * Get the balance snapshot for the address from the chain-reader.
     *
     * @param address currency address
     * @return Result<Map<String, Long>> of currency and balance
     */

    public static Result<Map<String, Long>> getSnapshot(String address) {
        Result<Map<String, Long>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", readerServiceId, address.substring(0, 4), null);
        try {
            // This is a connection that is shared by multiple requests and won't close until the app exits.
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/account/" + address);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Map<String, Long> currencyMap = Config.getInstance().getMapper().readValue(body, new TypeReference<HashMap<String,Long>>() {});
                result = Success.of(currencyMap);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get transactions on the blockchain for an address and currency combination
     *
     * @param address currency address
     * @param currency currency symbol only taiji is supported now.
     * @return Result<List<SignedLedgerEntry>> of currency and balance
     */

    public static Result<List<SignedLedgerEntry>> getTransaction(String address, String currency) {
        Result<List<SignedLedgerEntry>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", readerServiceId, address.substring(0, 4), null);
        try {
            // This is a connection that is shared by multiple requests and won't close until the app exits.
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/transaction/" + address + "/" + currency);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                List<SignedLedgerEntry> entries = Config.getInstance().getMapper().readValue(body, new TypeReference<List<SignedLedgerEntry>>() {});
                result = Success.of(entries);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get fee structure for a specific currency
     *
     * @param currency currency symbol only taiji is supported now.
     * @return Result<List<SignedLedgerEntry>> of currency and balance
     */
    public static Result<Fee> getFee(String address, String currency) {
        Result<Fee> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", readerServiceId, address.substring(0, 4), null);
        try {
            // This is a connection that is shared by multiple requests and won't close until the app exits.
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/fee/" + currency);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Fee fee = Config.getInstance().getMapper().readValue(body, Fee.class);
                result = Success.of(fee);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token info by symbol
     *
     * @param symbol token symbol
     * @return Result<Map<String, Object>> of token info
     */
    public static Result<Map<String, Object>> getTokenInfoBySymbol(String symbol) {
        Result<Map<String, Object>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/symbol/" + symbol);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Map<String, Object> tokenInfo = JsonMapper.string2Map(body);
                result = Success.of(tokenInfo);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token info by token address
     *
     * @param address token address
     * @return Result<Map<String, Object>> of token info
     */
    public static Result<Map<String, Object>> getTokenInfoByAddress(String address) {
        Result<Map<String, Object>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/token/" + address);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Map<String, Object> tokenInfo = JsonMapper.string2Map(body);
                result = Success.of(tokenInfo);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token account by address
     *
     * @param address owner address
     * @return Result<Map<String, Object>> of token account
     */
    public static Result<Map<String, Object>> getTokenAccountByAddress(String address) {
        Result<Map<String, Object>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/account/" + address);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Map<String, Object> account = JsonMapper.string2Map(body);
                result = Success.of(account);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token account by address and symbol
     *
     * @param address owner address
     * @return Result<Map<String, Object>> of token account for a particular symbol
     */
    public static Result<Map<String, Object>> getTokenAccountByAddressSymbol(String address, String symbol) {
        Result<Map<String, Object>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/account/" + address + "/" + symbol);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                Map<String, Object> account = JsonMapper.string2Map(body);
                result = Success.of(account);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token transactions by address
     *
     * @param address owner address
     * @return Result<Map<String, Object>> of token account
     */
    public static Result<List<Map<String, Object>>> getTokenTransactionByAddress(String address) {
        Result<List<Map<String, Object>>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/transaction/" + address);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                List<Map<String, Object>> transactions = JsonMapper.string2List(body);
                result = Success.of(transactions);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }

    /**
     * Get token transactions by address and symbol
     *
     * @param address owner address
     * @return Result<List<Map<String, Object>>> of token transactions for a particular symbol
     */
    public static Result<List<Map<String, Object>>> getTokenTransactionByAddressSymbol(String address, String symbol) {
        Result<List<Map<String, Object>>> result = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", tokenServiceId, null, null);
        try {
            ClientConnection connection = client.connect(new URI(apiHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
            // Create one CountDownLatch that will be reset in the callback function
            final CountDownLatch latch = new CountDownLatch(1);
            // Create an AtomicReference object to receive ClientResponse from callback function
            final AtomicReference<ClientResponse> reference = new AtomicReference<>();
            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/transaction/" + address + "/" + symbol);
            request.getRequestHeaders().put(Headers.HOST, "localhost");
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            if(statusCode != 200) {
                Status status = Config.getInstance().getMapper().readValue(body, Status.class);
                result = Failure.of(status);
            } else {
                List<Map<String, Object>> transactions = JsonMapper.string2List(body);
                result = Success.of(transactions);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
            Status status = new Status(GENERIC_EXCEPTION, e.getMessage());
            result = Failure.of(status);
        }
        return result;
    }
}
