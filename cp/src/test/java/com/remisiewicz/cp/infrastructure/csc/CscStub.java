package com.remisiewicz.cp.infrastructure.csc;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class CscStub {

    private final WireMockServer wireMockServer = new WireMockServer(options().port(9003));

    public void stubRemoteStartTransaction(String stationName, String messageId) {
//        wireMockServer.stubFor(
//                post(urlEqualTo())
//                        .willReturn(aResponse()
//                        )
//        );
    }

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }
}
