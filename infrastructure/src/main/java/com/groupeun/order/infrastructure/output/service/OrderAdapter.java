package com.groupeun.order.infrastructure.output.service;

import com.groupeun.order.application.ports.output.OrderOutputPort;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.infrastructure.output.entity.OrderEntity;
import com.groupeun.order.infrastructure.output.mapper.OrderOutputMapper;
import com.groupeun.order.infrastructure.output.repository.ItemRepository;
import com.groupeun.order.infrastructure.output.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderAdapter implements OrderOutputPort {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Optional<Order> findOne(UUID id) {
        Optional<OrderEntity> entity = orderRepository.findById(id.toString());
        if (entity.isPresent())
            return entity.map(OrderOutputMapper::entityToModel);
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(OrderOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByBuyer(UUID buyer) {
        return orderRepository.findAllByBuyerOrderByCreationTimestampDesc(buyer.toString()).stream()
                .map(OrderOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByBuyerAndDelivered (UUID buyer) {
        return orderRepository.findAllByBuyerAndDeliveredOrderByCreationTimestampDesc(buyer.toString(), false).stream()
                .map(OrderOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Order> save(UUID orderId, UUID buyer, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered, Instant creationTimestamp, Instant lastUpdateTimestamp) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId.toString());
        orderEntity.setBuyer(buyer.toString());
        orderEntity.setPaid(isPaid);
        orderEntity.setDelivered(isDelivered);
        orderEntity = orderRepository.save(orderEntity);
        return Optional.of(OrderOutputMapper.entityToModel(orderEntity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        orderRepository.deleteById(id.toString());
    }
}
