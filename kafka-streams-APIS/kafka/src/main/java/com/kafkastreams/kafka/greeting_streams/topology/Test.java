package com.kafkastreams.kafka.greeting_streams.topology;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

public class Test {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = streamsBuilder.stream("Test");


        KStream<String, String> stringIntegerKStream =
              stream.mapValues((readOnlyKey, value) -> value.length()+"");

        stringIntegerKStream.to("test-1");
        final Topology topology = streamsBuilder.build();
        stringIntegerKStream.peek((key, value) -> System.out.println(value));

        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        /*streamsBuilder.stream("test1",
              Consumed.with(Serdes.String(), Serdes.String()))
              .mapValues((readOnlyKey, value) -> value.toLowerCase())
              .to("tes-2",Produced.with(Serdes.String(),Serdes.String()));

        KafkaStreams kafkaStreams1 = new KafkaStreams(streamsBuilder.build(), properties);
        kafkaStreams1.start();*/


    }
}
