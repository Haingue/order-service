package com.groupeun.order.application.ports.input;

import com.groupeun.order.domain.model.Item;
import com.groupeun.order.domain.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderInputPort {

    Order findOne (UUID id);
    List<Order> findAllByBuyer (UUID buyer);
    List<Order> findAllNotDeliveredByBuyer (UUID buyer);

    Order create (Order order);
    Order create (UUID buyer, Set<Item> items, LocalDateTime deliveryDatetime);
    Order update (Order order);
    Order update (UUID id, UUID buyer, Set<Item> items, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered);
    Order pay (UUID id);

    void delete (Order order);
    void delete (UUID id);

}
