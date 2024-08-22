package com.kafkastreams.kafka.order_kafka_stream_app.domain;

import java.math.BigDecimal;

public record OrderLineItem(
      String item,
      Integer count,
      BigDecimal amount) {
}
