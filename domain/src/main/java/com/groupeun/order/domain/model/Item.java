package com.groupeun.order.domain.model;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Item {

    private UUID id;

    private String label;

    private int quantity;

    private double unitPrice;

    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
