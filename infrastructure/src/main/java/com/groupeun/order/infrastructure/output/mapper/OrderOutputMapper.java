package com.groupeun.order.infrastructure.output.mapper;

import com.groupeun.order.domain.model.Order;
import com.groupeun.order.infrastructure.output.entity.OrderEntity;

import java.util.UUID;

public class OrderOutputMapper {

    public static OrderEntity modelToEntity (Order model) {
        OrderEntity entity = new OrderEntity();
        entity.setId(model.getId().toString());
        entity.setBuyer(model.getBuyer().toString());

        entity.setPaid(model.isPaid());
        entity.setDeliveryDatetime(model.getDeliveryDatetime());
        entity.setDelivered(model.isDelivered());
        entity.setLastUpdateTimestamp(model.getLastUpdateTimestamp());
        entity.setCreationTimestamp(model.getCreationTimestamp());
        return entity;
    }

    public static Order entityToModel (OrderEntity entity) {
        Order model = new Order();
        model.setId(UUID.fromString(entity.getId()));
        model.setBuyer(UUID.fromString(entity.getBuyer()));

        model.setPaid(entity.isPaid());
        model.setDeliveryDatetime(entity.getDeliveryDatetime());
        model.setDelivered(entity.isDelivered());
        model.setLastUpdateTimestamp(entity.getLastUpdateTimestamp());
        model.setCreationTimestamp(entity.getCreationTimestamp());
        return model;
    }
}
