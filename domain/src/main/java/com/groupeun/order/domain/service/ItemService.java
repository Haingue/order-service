package com.groupeun.order.domain.service;

import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ItemService {

    private ItemOutputPort itemOutputPort;

    public boolean checkItemExist(Set<Item> items) {
        if (items.isEmpty()) return true;
        Set<Item> existingItems = itemOutputPort.getItemDetails(items);
        return items.size() == existingItems.size();
    }

    public Set<Item> getItemDetails(Set<Item> items) {
        if (items.isEmpty()) return Collections.emptySet();
        return itemOutputPort.getItemDetails(items);
    }

    public Set<Item> getItemByOrderId(UUID orderId) {
        return itemOutputPort.getItemListByOrderId(orderId);
    }

    public void updateItemList (UUID orderId, Set<Item> items) {
        Set<Item> existingItems = itemOutputPort.getItemListByOrderId(orderId);
        for (Item item : items) {
            itemOutputPort.save(orderId, item.getId(), item.getQuantity());
            if (existingItems.contains(item)) {
                existingItems.remove(item);
            }
        }
        itemOutputPort.deleteAll(orderId,
                existingItems.stream().map(Item::getId)
                        .collect(Collectors.toSet()));
    }
}
