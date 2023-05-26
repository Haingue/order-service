package com.groupeun.order.infrastructure.input.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private Set<ItemDto> items = new HashSet<>();
    private UUID buyer;
    private boolean paid;
    private LocalDateTime deliveryDatetime;
    private boolean delivered;
    private Instant creationTimestamp;
    private Instant lastUpdateTimestamp;
}
