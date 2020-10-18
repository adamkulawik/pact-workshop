package com.remisiewicz.csc.messages.outgoing;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

class RemoteStartPactTest {

    @Inject
    private EmbeddedServer embeddedServer;

    @BeforeEach
    void setUpTarget(PactVerificationContext context) {
    }

    void pactVerificationTestTemplate(PactVerificationContext context) {
    }
}
