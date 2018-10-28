package com.networknt.taiji.client;

import com.networknt.client.Http2Client;
import com.networknt.cluster.Cluster;
import com.networknt.config.Config;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.status.Status;
import com.networknt.taiji.crypto.SignedTransaction;
import com.networknt.taiji.crypto.TransactionReceipt;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TaijiClient {
    static final Logger logger = LoggerFactory.getLogger(TaijiClient.class);
    // service name
    static final String serviceId = "com.networknt.chainwriter-1.0.0";
    // Get the singleton Cluster instance
    static Cluster cluster = SingletonServiceFactory.getBean(Cluster.class);
    // Get the singleton Http2Client instance
    static Http2Client client = Http2Client.getInstance();

    public static Status postTx(String bankId, SignedTransaction stx) {
        Status status = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", serviceId, bankId, null);
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
                status = new Status();
                status.setStatusCode(200);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
        return status;
    }

    public static Status postTxs(String bankId, List<SignedTransaction> stxs) {
        Status status = null;
        // host name or IP address
        String apiHost = cluster.serviceToUrl("https", serviceId, bankId, null);

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
                status = new Status();
                status.setStatusCode(200);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
        return status;
    }
}
