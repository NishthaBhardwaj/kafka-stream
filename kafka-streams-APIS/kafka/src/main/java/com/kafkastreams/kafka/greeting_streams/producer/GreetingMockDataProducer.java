package com.kafkastreams.kafka.greeting_streams.producer;

import static com.kafkastreams.kafka.greeting_streams.producer.ProducerUtil.publishMessageSync;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafkastreams.kafka.greeting_streams.domian.Greeting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingMockDataProducer {

    static String GREETINGS = "greetings-1";
    static String GREETINGS_SPANISH = "greetings_spanish-1";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper()
              .registerModule(new JavaTimeModule())
              .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        englishGreetings(objectMapper);
        spanishGreetings(objectMapper);

    }

    private static void englishGreetings(ObjectMapper objectMapper) {
        var spanishGreetings = List.of(
              new Greeting("Hello, Good Morning!", LocalDateTime.now()),
              new Greeting("Hello, Good Evening!", LocalDateTime.now()),
              new Greeting("Hello, Good Night!", LocalDateTime.now())
        );
        spanishGreetings
              .forEach(greeting -> {
                  try {
                      var greetingJSON = objectMapper.writeValueAsString(greeting);
                      var recordMetaData = publishMessageSync(GREETINGS, null, greetingJSON);
                      log.info("Published the alphabet message : {} ", recordMetaData);
                  } catch (JsonProcessingException e) {
                      throw new RuntimeException(e);
                  }
              });
    }

    private static void spanishGreetings(ObjectMapper objectMapper) {
        var spanishGreetings = List.of(
              new Greeting("¡Hola buenos dias!", LocalDateTime.now()),
              new Greeting("¡Hola buenas tardes!", LocalDateTime.now()),
              new Greeting("¡Hola, buenas noches!", LocalDateTime.now())
        );
        spanishGreetings
              .forEach(greeting -> {
                  try {
                      var greetingJSON = objectMapper.writeValueAsString(greeting);
                      var recordMetaData = publishMessageSync(GREETINGS_SPANISH, null, greetingJSON);
                      log.info("Published the alphabet message : {} ", recordMetaData);
                  } catch (JsonProcessingException e) {
                      throw new RuntimeException(e);
                  }
              });
    }

}
