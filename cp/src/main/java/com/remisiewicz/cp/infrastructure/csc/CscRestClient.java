package com.remisiewicz.cp.infrastructure.csc;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

public class CscRestClient {

    private final Logger logger = LoggerFactory.getLogger(CscRestClient.class);

    private final HttpClient client;

    CscRestClient(HttpClient client) {
        this.client = client;
    }

    public String remoteStartTransaction(String chargingPointName, int connectorId, String idTag, Instant when) {
        String location = "/chargepoints/{chargingPointName}/actions/{action}";
        URI uri = UriBuilder
                .of(location)
                .expand(Map.of(
                        "chargingPointName", chargingPointName,
                        "action", "RemoteStartTransaction"
                ));
        RemoteStartTransactionRequest remoteStartTransactionRequest = new RemoteStartTransactionRequest(connectorId, idTag, when);
        MutableHttpRequest<RemoteStartTransactionRequest> request = HttpRequest.POST(uri, remoteStartTransactionRequest);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        logger.info("Remote start transaction {} sent to {} with result {}", remoteStartTransactionRequest, chargingPointName, response.getBody());
        return response.getBody().get();
    }
}
