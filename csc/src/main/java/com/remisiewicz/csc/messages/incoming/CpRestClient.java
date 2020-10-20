package com.remisiewicz.csc.messages.incoming;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

public class CpRestClient {

    private final Logger logger = LoggerFactory.getLogger(CpRestClient.class);

    private final HttpClient client;

    CpRestClient(HttpClient client) {
        this.client = client;
    }

    public void meterValues(String chargingPointName) {
        String location = "/cp/{chargingPointName}/transactions";
        URI uri = UriBuilder
                .of(location)
                .expand(Map.of("chargingPointName", chargingPointName));
        Map<String, Object> meterValues = body();
        MutableHttpRequest<Map<String, Object>> request = HttpRequest.PUT(uri, meterValues).header(CONTENT_TYPE, "application/json; charset=UTF-8");
        HttpResponse<Void> response = client.toBlocking().exchange(request);
        logger.info("Meter values {} sent to {} with result {}", meterValues, chargingPointName, response);
    }

    private Map<String, Object> body() {
        return Map.of(
                "connectorId", 1,
                "transactionId", 43958743,
                "meterValue", List.of(
                        Map.of(
                                "sampledValue",
                                List.of(
                                        Map.of(
                                                "context", "Sample.Periodic",
                                                "location", "Outlet",
                                                "measurand", "Energy.Active.Import.Register",
                                                "unit", "Wh",
                                                "value", "103",
                                                "format", "Raw",
                                                "phase", "L1"
                                        )
                                ),
                                "timestamp", Instant.now()
                        )
                )
        );
    }
}
