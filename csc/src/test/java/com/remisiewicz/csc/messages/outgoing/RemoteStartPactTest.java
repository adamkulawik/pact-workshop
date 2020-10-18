package com.remisiewicz.csc.messages.outgoing;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@MicronautTest
@Provider("pw-gr1-csc")
@PactFolder("pacts")
class RemoteStartPactTest {

    @Inject
    private EmbeddedServer embeddedServer;

    @BeforeEach
    void setUpTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget(embeddedServer.getHost(), embeddedServer.getPort()));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
