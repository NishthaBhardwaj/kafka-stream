package com.kafkastreams.kafka.order_kafka_stream_app.domain;

import java.math.BigDecimal;

public record Revenue(String locationId,
                      BigDecimal finalAmount) {
}
