package com.remisiewicz.csc.messages.incoming;

import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;

import javax.inject.Singleton;

@Factory
class CpConfiguration {

    @Singleton
    CpRestClient cpClient(@Client("${cp.url}") HttpClient client) {
        return new CpRestClient(client);
    }
}
