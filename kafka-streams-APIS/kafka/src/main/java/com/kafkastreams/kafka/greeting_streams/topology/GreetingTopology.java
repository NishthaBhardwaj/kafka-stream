package com.kafkastreams.kafka.greeting_streams.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

import com.kafkastreams.kafka.greeting_streams.domian.Greeting;
import com.kafkastreams.kafka.greeting_streams.serdes.SerdesFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingTopology {

    public static final String GREETINGS = "greetings-1";
    public static final String GREETINGS_UPPERCASE = "greetings_uppercase-2";
    public static final String GREETINGS_SPANISH = "greetings_spanish-1";

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();


        var stringGreetingKStream = getCustomGreetingKStream(streamsBuilder);

        stringGreetingKStream.print(Printed.<String, Greeting>toSysOut().withLabel("merged Stream"));


        var greetingStreamUpperCase = stringGreetingKStream
              .mapValues((key, value) -> new Greeting(value.message().toUpperCase(), value.timestamp()));


        greetingStreamUpperCase
              .to(GREETINGS_UPPERCASE,
                    Produced.with(Serdes.String(),
                          SerdesFactory.greetingSerdeUsingGeneric()));

        greetingStreamUpperCase.print(Printed.<String, Greeting>toSysOut().withLabel("modified stream"));

        return streamsBuilder.build();

    }

    private static KStream getStringGreetingKStream(StreamsBuilder streamsBuilder){

        KStream<String, String> greetingStream = streamsBuilder
              .stream(GREETINGS);

        KStream<String, String> greetingSpanish = streamsBuilder.stream(GREETINGS_SPANISH);

        return greetingStream.merge(greetingSpanish);

    }

    private static KStream<String, Greeting> getCustomGreetingKStream(StreamsBuilder streamsBuilder){

        var greetingStream = streamsBuilder
              .stream(GREETINGS,
                    Consumed.with(Serdes.String(), SerdesFactory.greetingSerdeUsingGeneric()));

        var greetingSpanish = streamsBuilder
              .stream(GREETINGS_SPANISH,
                    Consumed.with(Serdes.String(), SerdesFactory.greetingSerdeUsingGeneric()));

        return greetingStream.merge(greetingSpanish);



    }
}