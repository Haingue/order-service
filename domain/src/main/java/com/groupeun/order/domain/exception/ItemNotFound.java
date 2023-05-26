package com.groupeun.order.domain.exception;

import java.util.UUID;

public class ItemNotFound extends DomainException {

    public ItemNotFound() {
        super("Item not fount");
    }

    public ItemNotFound(UUID id) {
        super(String.format("Item[id=%s] not found", id));
    }

}
