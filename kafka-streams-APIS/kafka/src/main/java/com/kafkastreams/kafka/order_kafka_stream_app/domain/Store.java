package com.kafkastreams.kafka.order_kafka_stream_app.domain;

public record Store(String locationId,
                    Address address,
                    String contactNum) {
}
