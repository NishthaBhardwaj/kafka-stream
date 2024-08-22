package com.kafkastreams.kafka.greeting_streams.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafkastreams.kafka.greeting_streams.domian.Greeting;

public class GreetingSerdes implements Serde<Greeting> {

    private final ObjectMapper objectMapper
          = new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    public Serializer serializer() {
        return new GreetingSerializer(objectMapper);
    }

    @Override
    public Deserializer deserializer() {
        return new GreetingDeserializer(objectMapper);
    }
}
