package com.remisiewicz.cp.infrastructure.csc;

import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;

import javax.inject.Singleton;

@Factory
class CscConfiguration {

    @Singleton
    CscRestClient cscClient(@Client("http://localhost:9100") HttpClient client) {
        return new CscRestClient(client);
    }
}
