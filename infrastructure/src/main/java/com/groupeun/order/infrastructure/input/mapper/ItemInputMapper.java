package com.groupeun.order.infrastructure.input.mapper;

import com.groupeun.order.domain.model.Item;
import com.groupeun.order.infrastructure.input.dto.ItemDto;

public class ItemInputMapper {
    
    public static ItemDto modelToDto (Item model) {
        ItemDto dto = new ItemDto();
        dto.setId(model.getId());
        dto.setLabel(model.getLabel());
        dto.setQuantity(model.getQuantity());
        dto.setUnitPrice(model.getUnitPrice());
        dto.setPrice(model.getPrice());
        return dto;
    }

    public static  Item dtoToModel (ItemDto dto) {
        Item model = new Item();
        model.setId(dto.getId());
        model.setLabel(dto.getLabel());
        model.setQuantity(dto.getQuantity());
        model.setUnitPrice(dto.getUnitPrice());
        model.setPrice(dto.getPrice());
        return model;
    }

}
