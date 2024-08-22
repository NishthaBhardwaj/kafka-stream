package com.kafkastreams.kafka.greeting_streams.serdes.generic;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper
          = new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    public byte[] serialize(final String topic, final T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        }
        catch (JsonProcessingException e) {
            log.error("JsonProcessingException in Serializer: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            log.error("Exception in Serializer: {}", e.getMessage(), e);
            throw new RuntimeException(e);

        }
    }
}
