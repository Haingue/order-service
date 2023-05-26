package com.groupeun.order.infrastructure.input.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemDto {

    private UUID id;

    private String label;

    private int quantity;

    private double unitPrice;

    private double price;
}
