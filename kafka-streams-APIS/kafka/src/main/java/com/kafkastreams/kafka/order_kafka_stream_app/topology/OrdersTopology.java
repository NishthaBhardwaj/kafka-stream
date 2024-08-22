package com.kafkastreams.kafka.order_kafka_stream_app.topology;

import static org.apache.kafka.streams.kstream.Printed.toSysOut;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;

import com.kafkastreams.kafka.order_kafka_stream_app.domain.Order;
import com.kafkastreams.kafka.order_kafka_stream_app.domain.OrderType;
import com.kafkastreams.kafka.order_kafka_stream_app.domain.Revenue;
import com.kafkastreams.kafka.order_kafka_stream_app.serdes.SerdesFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrdersTopology {
    public static final String ORDERS = "orders";
    public static final String RESTAURANT_ORDERS = "restaurant_orders";
    public static final String GENERAL_ORDERS = "general_orders";
    public static final String STORES = "stores";

    public static Topology buildTopology() {
        Predicate<String, Order> generalPredicate = (key, order) -> order.orderType().equals(OrderType.GENERAL);
        Predicate<String, Order> restaurantPredicate = (key, order) -> order.orderType().equals(OrderType.RESTAURANT);
        ValueMapper<Order, Revenue> revenueValueMapper = order -> new Revenue(order.locationId(), order.finalAmount());

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        var orderStream = streamsBuilder
              .stream(ORDERS,
                    Consumed.with(Serdes.String(), SerdesFactory.orderSerdes()));

        orderStream
              .print(Printed.<String, Order>toSysOut().withLabel("orders"));

        orderStream
              .split(Named.as("General-restaurant-stream"))
              .branch(generalPredicate,
                    Branched.withConsumer(generalOrderStream -> {
                        generalOrderStream.print(Printed.<String, Order>toSysOut().withLabel("general-OrderStream"));
                        generalOrderStream
                              .mapValues((readOnlyKey, order) -> revenueValueMapper.apply(order))
                              .to(GENERAL_ORDERS,
                                    Produced.with(Serdes.String(), SerdesFactory.revenueSerdes()));

                    })
              )
              .branch(restaurantPredicate,
                    Branched.withConsumer(restaurantOrderStream -> {
                        restaurantOrderStream.print(Printed.<String, Order>toSysOut().withLabel("restaurant-OrderStream"));

                        restaurantOrderStream
                              .mapValues((readOnlyKey, order) -> revenueValueMapper.apply(order))
                              .to(RESTAURANT_ORDERS,
                                    Produced.with(Serdes.String(), SerdesFactory.revenueSerdes()));

                    })
              );

        return streamsBuilder.build();
    }
}
