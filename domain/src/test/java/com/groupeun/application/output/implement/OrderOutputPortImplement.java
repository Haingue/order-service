package com.groupeun.application.output.implement;

import com.groupeun.order.application.ports.output.OrderOutputPort;
import com.groupeun.order.domain.model.Order;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderOutputPortImplement implements OrderOutputPort {

    private static OrderOutputPortImplement instance = new OrderOutputPortImplement();
    private static HashMap<UUID, Order> store;

    private OrderOutputPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static OrderOutputPort getInstance() {
        return OrderOutputPortImplement.instance;
    }

    public static HashMap<UUID, Order> getStore() {
        return OrderOutputPortImplement.store;
    }

    @Override
    public Optional<Order> findOne(UUID id) {
        if (store.containsKey(id)) return Optional.of(store.get(id));
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Order> findAllByBuyer(UUID buyer) {
        return store.values().stream().filter(order -> order.getBuyer().equals(buyer))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByBuyerAndDelivered(UUID buyer) {
        return store.values().stream().filter(order -> order.isDelivered() && order.getBuyer().equals(buyer))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> save(UUID id, UUID buyer, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered, Instant creationTimestamp, Instant lastUpdateTimestamp) {
        Order order = OutputUtils.createOrder(id, null, buyer, isPaid, deliveryDatetime, isDelivered, creationTimestamp, lastUpdateTimestamp);
        store.put(id, order);
        return Optional.of(order);
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }
}
