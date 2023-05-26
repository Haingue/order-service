package com.groupeun.order.infrastructure.input.controller;

import com.groupeun.order.application.ports.input.OrderInputPort;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.infrastructure.input.dto.OrderDto;
import com.groupeun.order.infrastructure.input.mapper.OrderInputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/services/orders")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderInputPort orderInputPort;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOneOrder (@PathVariable Optional<String> orderId, Authentication authentication) {
        // TODO add user/roles checking (Keycloak object)
        if (orderId.isPresent()) {
            logger.info("Load one order: {}", orderId);
            Order order = orderInputPort.findOne(UUID.fromString(orderId.get()));
            return ResponseEntity.ok(OrderInputMapper.modelToDto(order));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder (@RequestBody OrderDto orderDto, Authentication authentication) {
        logger.info("Add new order {}: {}", authentication.getName(), orderDto.getId());
        Order order = orderInputPort.create(OrderInputMapper.dtoToModel(orderDto));
        return new ResponseEntity(OrderInputMapper.modelToDto(order), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder (@RequestBody OrderDto orderDto, Authentication authentication) {
        logger.info("Update order by {}: {}", authentication.getName(), orderDto.getId());
        Order order = orderInputPort.update(OrderInputMapper.dtoToModel(orderDto));
        return new ResponseEntity(OrderInputMapper.modelToDto(order), HttpStatus.CREATED);
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrderById (@PathVariable String orderId, Authentication authentication) {
        if (orderId != null) {
            logger.info("Delete order: {}", orderId);
            orderInputPort.delete(UUID.fromString(orderId));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping
    public ResponseEntity deleteOrderById (@RequestBody OrderDto orderDto, Authentication authentication) {
        if (orderDto != null) {
            logger.info("Delete order: {}", orderDto.getId());
            orderInputPort.delete(orderDto.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
