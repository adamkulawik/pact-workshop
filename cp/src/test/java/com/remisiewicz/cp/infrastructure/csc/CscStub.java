package com.remisiewicz.cp.infrastructure.csc;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;
import static java.lang.String.format;

class CscStub {

    private final WireMockServer wireMockServer = new WireMockServer(options().port(9003));

    public void stubRemoteStartTransaction(String stationName, String messageId) {
        wireMockServer.stubFor(
                post(urlEqualTo(format("/chargingpoints/%s/actions/RemoteStartTransaction", stationName)))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader(CONTENT_TYPE, "application/json")
                                .withBody("{\n" +
                                        "  \"messageId\": \"" + messageId + "\"\n" +
                                        "}")
                        )
        );
    }

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }
}
