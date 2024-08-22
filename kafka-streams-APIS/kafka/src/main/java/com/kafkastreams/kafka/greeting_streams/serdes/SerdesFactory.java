package com.kafkastreams.kafka.greeting_streams.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.kafkastreams.kafka.greeting_streams.domian.Greeting;
import com.kafkastreams.kafka.greeting_streams.serdes.generic.JsonDeserializer;
import com.kafkastreams.kafka.greeting_streams.serdes.generic.JsonSerializer;

public class SerdesFactory {

    /**
     * A serde for nullable {@code String} type.
     */
    static public Serde<Greeting> Greeting() {
        return new GreetingSerdes();
    }

    static public Serde<Greeting> greetingSerdeUsingGeneric() {
        JsonSerializer<Greeting> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Greeting> jsonDeserializer = new JsonDeserializer<>(Greeting.class);
        return Serdes.serdeFrom(jsonSerializer,jsonDeserializer);
    }
}
