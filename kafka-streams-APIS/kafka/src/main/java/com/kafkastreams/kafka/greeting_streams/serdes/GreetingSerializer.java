package com.kafkastreams.kafka.greeting_streams.serdes;

import java.util.Map;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkastreams.kafka.greeting_streams.domian.Greeting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingSerializer implements Serializer<Greeting> {

    private ObjectMapper objectMapper;

    public GreetingSerializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(final String topic, final Greeting data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        }
        catch (JsonProcessingException e) {
            log.error("JsonProcessingException : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new RuntimeException(e);

        }
    }

}
