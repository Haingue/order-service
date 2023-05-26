package com.groupeun.order.domain.model;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Order {

    private UUID id;
    private Set<Item> items;
    private UUID buyer;
    private boolean paid;
    private LocalDateTime deliveryDatetime;
    private boolean delivered;
    private Instant creationTimestamp;
    private Instant lastUpdateTimestamp;

    public Order() {
        this.items = new HashSet<>();
        this.creationTimestamp = Instant.now();
        this.lastUpdateTimestamp = this.creationTimestamp;
    }

    public int getItemNumber () {
        return this.items.size();
    }

    public double getTotalPrice () {
        return this.items.stream().mapToDouble(Item::getPrice).sum();
    }

    private boolean addItem (Item item) {
        boolean added = this.items.add(item);
        this.lastUpdateTimestamp = Instant.now();
        return added;
    }

    private boolean removeItem (Item item) {
        boolean removed = this.items.remove(item);
        this.lastUpdateTimestamp = Instant.now();
        return removed;
    }
}
