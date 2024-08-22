package com.kafkastreams.kafka.greeting_streams.serdes.generic;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafkastreams.kafka.greeting_streams.domian.Greeting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> destinationClass;

    public JsonDeserializer(final Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    private final ObjectMapper objectMapper
          = new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    public T deserialize(final String topic, final byte[] data) {

        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, destinationClass);
        }
        catch (IOException e) {
            log.error("IOException in Deserializer: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            log.error("Exception in Deserializer: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
