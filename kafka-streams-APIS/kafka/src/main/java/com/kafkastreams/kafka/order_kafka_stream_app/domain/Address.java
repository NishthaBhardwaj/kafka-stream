package com.kafkastreams.kafka.order_kafka_stream_app.domain;

public record Address(String addressLine1,
                      String addressLine2,
                      String city,
                      String state,
                      String zip) {
}
