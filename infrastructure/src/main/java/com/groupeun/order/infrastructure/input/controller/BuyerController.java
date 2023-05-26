package com.groupeun.order.infrastructure.input.controller;

import com.groupeun.order.application.ports.input.OrderInputPort;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.infrastructure.input.mapper.OrderInputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/services/buyers")
public class BuyerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderInputPort orderInputPort;
    @GetMapping("/{buyerId}/orders")
    public ResponseEntity<?> getAllOrderByBuyer (@PathVariable String buyerId, Authentication authentication) {
        // TODO add user/roles checking (Keycloak object)
        if (buyerId != null) {
            logger.info("Load all order with buyer : {}", buyerId);
            List<Order> orders = orderInputPort.findAllByBuyer(UUID.fromString(buyerId));
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders.stream()
                    .map(OrderInputMapper::modelToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{buyerId}/orders/not-delivered")
    public ResponseEntity<?> getOrderNotDelivered (@PathVariable String buyerId, Authentication authentication) {
        // TODO add user/roles checking (Keycloak object)
        if (buyerId != null) {
            logger.info("Load all order not delivered with buyer : {}", buyerId);
            List<Order> orders = orderInputPort.findAllNotDeliveredByBuyer(UUID.fromString(buyerId));
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders.stream()
                    .map(OrderInputMapper::modelToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }

}
