package com.groupeun.order.domain.exception;

import java.util.UUID;

public class OrderNotPaid extends DomainException {

    public OrderNotPaid() {
        super("The order is not paid");
    }

    public OrderNotPaid(UUID id) {
        super(String.format("The order[id=%s] is not paid", id));
    }

}
