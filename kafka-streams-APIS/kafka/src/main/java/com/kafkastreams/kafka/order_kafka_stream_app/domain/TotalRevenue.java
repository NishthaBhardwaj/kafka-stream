package com.kafkastreams.kafka.order_kafka_stream_app.domain;

import java.math.BigDecimal;

public record TotalRevenue(String locationId,
                           Integer runnuingOrderCount,
                           BigDecimal runningRevenue) {
}
