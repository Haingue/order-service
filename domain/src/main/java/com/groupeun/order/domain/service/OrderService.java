package com.groupeun.order.domain.service;

import com.groupeun.order.application.ports.input.OrderInputPort;
import com.groupeun.order.application.ports.output.OrderOutputPort;
import com.groupeun.order.domain.exception.DomainException;
import com.groupeun.order.domain.exception.ItemNotFound;
import com.groupeun.order.domain.exception.OrderNotFound;
import com.groupeun.order.domain.model.Item;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.domain.model.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderService implements OrderInputPort {

    private OrderOutputPort orderOutputPort;
    private ItemService itemService;

    @Override
    public Order findOne(UUID id) {
        return orderOutputPort.findOne(id)
                .map(this::loadOrderDetails)
                .orElseThrow(() -> new OrderNotFound(id));
    }

    @Override
    public List<Order> findAllByBuyer(UUID buyer) {
        return orderOutputPort.findAllByBuyer(buyer);
    }

    @Override
    public List<Order> findAllNotDeliveredByBuyer(UUID buyer) {
        return orderOutputPort.findAllByBuyerAndDelivered(buyer);
    }

    @Override
    public Order create(Order order) {
        return this.create(order.getBuyer(), order.getItems(), order.getDeliveryDatetime());
    }

    @Override
    public Order create(UUID buyer, Set<Item> items, LocalDateTime deliveryDatetime) {
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = itemService.checkItemExist(items);
            if (!allIngredientExist) throw new ItemNotFound();
        }
        Instant now = Instant.now();
        Order newOrder = orderOutputPort.save(UUID.randomUUID(), buyer, false, deliveryDatetime, false, now, now)
                .orElseThrow(() -> new DomainException(String.format("Error to create Order[buyer=%s]", buyer)));
        itemService.updateItemList(newOrder.getId(), items);
        return newOrder;
    }

    @Override
    public Order update(Order order) {
        return this.update(order.getId(), order.getBuyer(), order.getItems(), order.isPaid(),
                order.getDeliveryDatetime(), order.isDelivered());
    }

    @Override
    public Order update(UUID orderId, UUID buyer, Set<Item> items, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered) {
        Order existingOrder = orderOutputPort.findOne(orderId)
                .orElseThrow(() -> new OrderNotFound(orderId));
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = itemService.checkItemExist(items);
            if (!allIngredientExist) throw new ItemNotFound();
        }

        Order updatedOrder = orderOutputPort.save(orderId, buyer, isPaid, deliveryDatetime, isDelivered, existingOrder.getCreationTimestamp(), Instant.now())
                .orElseThrow(() -> new DomainException(String.format("Error to update Order[id=%s]", orderId)));
        itemService.updateItemList(updatedOrder.getId(), updatedOrder.getItems());
        return updatedOrder;
    }

    @Override
    public Order pay(UUID id) {
        return null;
    }

    @Override
    public void delete(Order order) {
        this.delete(order.getId());
    }

    @Override
    public void delete(UUID id) {
        if (orderOutputPort.findOne(id).isPresent()) throw new OrderNotFound(id);
        orderOutputPort.delete(id);
    }

    private Order loadOrderDetails (Order order) {
        order.setItems(itemService.getItemByOrderId(order.getId()));
        return order;
    }
}
