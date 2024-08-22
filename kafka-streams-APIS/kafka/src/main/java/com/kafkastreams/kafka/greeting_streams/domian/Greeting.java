package com.kafkastreams.kafka.greeting_streams.domian;

import java.time.LocalDateTime;

public record Greeting(String message,
                       LocalDateTime timestamp
) {
}
