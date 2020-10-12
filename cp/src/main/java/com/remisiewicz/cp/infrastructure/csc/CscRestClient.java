package com.remisiewicz.cp.infrastructure.csc;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CscRestClient {

    private final Logger logger = LoggerFactory.getLogger(CscRestClient.class);

    private final HttpClient client;

    CscRestClient(HttpClient client) {
        this.client = client;
    }

    public String remoteStartTransaction(String chargingPointName, int connectorId, String idTag, Date when) {
        String location = "";
        URI uri = UriBuilder
                .of(location)
                .expand(Map.of());
//      or  URI uri = UriBuilder
//                .of(location)
//                .build();
        RemoteStartTransactionRequest remoteStartTransactionRequest = new RemoteStartTransactionRequest();
        MutableHttpRequest<RemoteStartTransactionRequest> request = null;//HttpRequest.
        HttpResponse<?> response = client.toBlocking().exchange(request);
        logger.info("Remote start transaction {} sent to {} with result {}", remoteStartTransactionRequest, chargingPointName, response.getBody());
        return response.getBody().get().toString();
    }
}
