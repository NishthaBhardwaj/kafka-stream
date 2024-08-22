package com.kafkastreams.kafka.order_kafka_stream_app.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.kafkastreams.kafka.greeting_streams.domian.Greeting;
import com.kafkastreams.kafka.order_kafka_stream_app.domain.Order;
import com.kafkastreams.kafka.order_kafka_stream_app.domain.Revenue;

public class SerdesFactory {

    /**
     * A serde for nullable {@code String} type.
     */

    static public Serde<Order> orderSerdes() {
        JsonSerializer<Order> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> jsonDeserializer = new JsonDeserializer<>(Order.class);
        return Serdes.serdeFrom(jsonSerializer,jsonDeserializer);
    }

    static public Serde<Revenue> revenueSerdes() {
        JsonSerializer<Revenue> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Revenue> jsonDeserializer = new JsonDeserializer<>(Revenue.class);
        return Serdes.serdeFrom(jsonSerializer,jsonDeserializer);
    }
}
