package com.groupeun.order.infrastructure.output.mapper;

import com.groupeun.order.domain.model.Item;
import com.groupeun.order.infrastructure.output.entity.ItemEntity;
import com.groupeun.order.infrastructure.output.entity.id.ItemEntityId;

import java.util.UUID;

public class ItemOutputMapper {

    public static ItemEntity modelToEntity (UUID orderId, Item model) {
        ItemEntityId id = new ItemEntityId();
        id.setOrderId(orderId.toString());
        id.setItemId(model.getId().toString());

        ItemEntity entity = new ItemEntity();
        entity.setId(id);
        entity.setQuantity(model.getQuantity());
        return entity;
    }

    public static Item entityToModel (ItemEntity entity) {
        Item model = new Item();
        model.setId(UUID.fromString(entity.getId().getItemId()));
        model.setQuantity(entity.getQuantity());
        return model;
    }
}
