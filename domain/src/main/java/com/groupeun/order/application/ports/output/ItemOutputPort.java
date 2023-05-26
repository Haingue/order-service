package com.groupeun.order.application.ports.output;

import com.groupeun.order.domain.model.Item;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ItemOutputPort {
    Set<Item> getItemDetails(Set<Item> itemIds);

    Set<Item> getItemListByOrderId (UUID orderId);

    Optional<Item> save(UUID orderId, UUID itemId, int quantity);

    void delete (UUID orderId, UUID itemId);
    void deleteAll (UUID orderId, Set<UUID> itemIds);
    void deleteAllByOrder (UUID orderId);
}
