package com.groupeun.order.infrastructure.input.mapper;

import com.groupeun.order.domain.model.Order;
import com.groupeun.order.infrastructure.input.dto.OrderDto;

public class OrderInputMapper {

    public static OrderDto modelToDto (Order model) {
        OrderDto dto = new OrderDto();
        dto.setId(model.getId());
        dto.setBuyer(model.getBuyer());
        dto.setPaid(model.isPaid());
        dto.setDeliveryDatetime(model.getDeliveryDatetime());
        dto.setDelivered(model.isDelivered());
        dto.setCreationTimestamp(model.getCreationTimestamp());
        dto.setLastUpdateTimestamp(model.getLastUpdateTimestamp());

        model.getItems().stream()
                .map(ItemInputMapper::modelToDto)
                .forEach(dto.getItems()::add);
        return dto;
    }

    public static Order dtoToModel (OrderDto dto) {
        Order model = new Order();
        model.setId(dto.getId());
        model.setBuyer(dto.getBuyer());
        model.setPaid(dto.isPaid());
        model.setDeliveryDatetime(dto.getDeliveryDatetime());
        model.setDelivered(dto.isDelivered());
        model.setCreationTimestamp(dto.getCreationTimestamp());
        model.setLastUpdateTimestamp(dto.getLastUpdateTimestamp());

        dto.getItems().stream()
                .map(ItemInputMapper::dtoToModel)
                .forEach(model.getItems()::add);
        return model;
    }

}
