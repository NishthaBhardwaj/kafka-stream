package com.kafkastreams.kafka.order_kafka_stream_app.domain;

public record TotalRevenueWithAddress(TotalRevenue totalRevenue,
                                      Store store) {
}
