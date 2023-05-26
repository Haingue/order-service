package com.groupeun.domain;

import com.groupeun.application.output.implement.ItemOutputPortImplement;
import com.groupeun.application.output.implement.OrderOutputPortImplement;
import com.groupeun.application.output.implement.OutputUtils;
import com.groupeun.order.domain.model.Item;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.domain.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class OrderServiceTest {

    private OrderService orderService = OutputUtils.orderService;

    @BeforeAll
    public static void prepareTest () {
        OutputUtils.initializeOutputPortImplement();
    }

    @Test
    public void shouldInsertOrder () {
        UUID buyerId = UUID.randomUUID();
        Set<Item> items = OutputUtils.createItemList(3);
        LocalDateTime deliveryDatetime = LocalDateTime.now();
        Assertions.assertDoesNotThrow(() -> {
            orderService.create(buyerId, items, deliveryDatetime);
        }, "Error to create new Order");
        Order createdOrder = OrderOutputPortImplement.getStore()
                .values().stream()
                .filter(recipe -> recipe.getBuyer().equals(buyerId))
                .findFirst().get();

        Assertions.assertDoesNotThrow(() -> {
            Order firstOrder = orderService.findOne(createdOrder.getId());
            Assertions.assertNotNull(firstOrder);
            Assertions.assertEquals(firstOrder.getBuyer(), buyerId);
            Assertions.assertEquals(firstOrder.getDeliveryDatetime(), deliveryDatetime);
            Assertions.assertEquals(firstOrder.getItems().size(), items.size());

            Assertions.assertTrue(firstOrder.getItems().stream()
                    .allMatch(item -> ItemOutputPortImplement.getStore().values().stream()
                            .anyMatch(itemStored -> itemStored.contains(item))));
            Assertions.assertTrue(firstOrder.getItems().stream()
                    .allMatch(item -> items.stream()
                            .anyMatch(itemStored -> itemStored.getId().equals(item.getId()))));
        }, "Error to find order by id");
    }

    @Test
    public void shouldUpdateOrder () {
        // TODO
    }

    @Test
    public void shouldDeleteOrder () {
        // TODO
    }
}
