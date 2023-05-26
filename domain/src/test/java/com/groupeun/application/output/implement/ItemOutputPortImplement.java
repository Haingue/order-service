package com.groupeun.application.output.implement;

import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.domain.model.Item;

import java.util.*;
import java.util.stream.Collectors;

public class ItemOutputPortImplement implements ItemOutputPort {

    private static ItemOutputPortImplement instance = new ItemOutputPortImplement();
    private static HashMap<UUID, Set<Item>> store;

    private ItemOutputPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static ItemOutputPort getInstance() {
        return ItemOutputPortImplement.instance;
    }

    public static HashMap<UUID, Set<Item>> getStore() {
        return ItemOutputPortImplement.store;
    }

    @Override
    public Set<Item> getItemDetails(Set<Item> itemList) {
        return store.values().stream()
                .flatMap(Set::stream)
                .filter(itemStored ->
                        itemList.stream().anyMatch(item -> item.getId().equals(itemStored.getId())))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Item> getItemListByOrderId(UUID orderId) {
        if(!store.containsKey(orderId)) store.put(orderId, new HashSet<>());
        return store.entrySet().stream().filter(entry -> entry.getKey().equals(orderId))
                .map(Map.Entry::getValue)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Item> save(UUID orderId, UUID itemId, int quantity) {
        Item item = OutputUtils.createItem(itemId, quantity);
        if (!store.containsKey(orderId)) store.put(orderId, new HashSet<>());
        store.get(orderId).add(item);
        return Optional.of(item);
    }

    @Override
    public void delete(UUID orderId, UUID itemId) {
        store.get(orderId).remove(OutputUtils.createItem(itemId, 0));
    }

    @Override
    public void deleteAll(UUID orderId, Set<UUID> itemIds) {
        itemIds.forEach(itemId -> {
            store.get(orderId).remove(OutputUtils.createItem(itemId, 0));
        });
    }

    @Override
    public void deleteAllByOrder(UUID orderId) {
        store.remove(orderId);
    }
}
