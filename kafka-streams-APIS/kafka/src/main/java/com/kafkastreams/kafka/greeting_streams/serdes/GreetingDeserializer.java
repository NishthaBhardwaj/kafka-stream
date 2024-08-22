package com.kafkastreams.kafka.greeting_streams.serdes;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkastreams.kafka.greeting_streams.domian.Greeting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingDeserializer implements Deserializer<Greeting> {

    private ObjectMapper objectMapper;

    public GreetingDeserializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Greeting deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Greeting.class);
        }
        catch (IOException e) {
            log.error("IOException : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
