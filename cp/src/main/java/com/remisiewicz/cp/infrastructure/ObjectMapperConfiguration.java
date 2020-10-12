package com.remisiewicz.cp.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;

import javax.inject.Singleton;

//@Singleton
class ObjectMapperConfiguration {//implements BeanCreatedEventListener<ObjectMapper> {

//    @Override
    public ObjectMapper onCreated(BeanCreatedEvent<ObjectMapper> event) {
        return event.getBean()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}

