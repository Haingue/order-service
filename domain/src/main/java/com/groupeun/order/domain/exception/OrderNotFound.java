package com.groupeun.order.domain.exception;

import java.util.UUID;

public class OrderNotFound extends DomainException {
    public OrderNotFound() {
        super("Order not found");
    }

    public OrderNotFound(UUID id) {
        super(String.format("Order[id=%s] not found", id));
    }
}
