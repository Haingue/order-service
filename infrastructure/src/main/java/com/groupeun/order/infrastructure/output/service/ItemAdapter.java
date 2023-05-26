package com.groupeun.order.infrastructure.output.service;

import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.domain.model.Item;
import com.groupeun.order.infrastructure.output.entity.ItemEntity;
import com.groupeun.order.infrastructure.output.entity.id.ItemEntityId;
import com.groupeun.order.infrastructure.output.mapper.ItemOutputMapper;
import com.groupeun.order.infrastructure.output.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemAdapter implements ItemOutputPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemWebAdapter itemWebAdapter;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Set<Item> getItemDetails(Set<Item> items) {
        return itemWebAdapter.getItemDetails(items);
    }

    @Override
    public Set<Item> getItemListByOrderId(UUID orderId) {
        return itemRepository.findAllByIdOrderId(orderId.toString())
                .stream().map(ItemOutputMapper::entityToModel)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Item> save(UUID orderId, UUID itemId, int quantity) {
        ItemEntityId id = new ItemEntityId();
        id.setOrderId(orderId.toString());
        id.setItemId(id.toString());

        ItemEntity entity = new ItemEntity();
        entity.setId(id);
        entity.setQuantity(quantity);
        return Optional.empty();
    }

    @Override
    public void delete(UUID orderId, UUID itemId) {
        ItemEntityId id = new ItemEntityId();
        id.setOrderId(orderId.toString());
        id.setItemId(id.toString());
        itemRepository.deleteById(id);
    }

    @Override
    public void deleteAll(UUID orderId, Set<UUID> itemIds) {
        itemIds.forEach(itemId -> {
            this.delete(orderId, itemId);
        });
    }

    @Override
    public void deleteAllByOrder(UUID orderId) {
        itemRepository.deleteAllByIdOrderId(orderId.toString());
    }

}
