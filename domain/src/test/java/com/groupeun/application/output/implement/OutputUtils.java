package com.groupeun.application.output.implement;

import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.application.ports.output.OrderOutputPort;
import com.groupeun.order.application.ports.output.SettingOutputPort;
import com.groupeun.order.domain.model.Item;
import com.groupeun.order.domain.model.Order;
import com.groupeun.order.domain.service.ItemService;
import com.groupeun.order.domain.service.OrderService;
import com.groupeun.order.domain.service.SettingService;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OutputUtils {

    public static OrderService orderService;
    public static ItemService itemService;
    public static SettingService settingService;

    static {
        OrderOutputPort orderOutputPort = OrderOutputPortImplement.getInstance();
        ItemOutputPort itemOutputPort = ItemOutputPortImplement.getInstance();
        SettingOutputPort settingOutPutPort = SettingOutPutPortImplement.getInstance();

        settingService = new SettingService(settingOutPutPort);
        itemService = new ItemService(itemOutputPort);
        orderService = new OrderService(orderOutputPort, itemService);
    }

    public static void initializeOutputPortImplement () {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            Set<Item> items = createItemList(3);
            Order order = createOrder(
                    UUID.randomUUID(),
                    items,
                    UUID.randomUUID(),
                    false,
                    null,
                    false,
                    Instant.now(),
                    Instant.now()
            );
            ItemOutputPortImplement.getStore().put(order.getId(), items);
            OrderOutputPortImplement.getStore().put(order.getId(), order);
        }
    }

    public static Order createOrder(UUID id, Set<Item> items, UUID buyer, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered, Instant creationTimestamp, Instant lastUpdateTimestamp) {
        Order order = new Order();
        order.setId(id);
        order.setBuyer(buyer);
        order.setItems(items);
        order.setPaid(isPaid);
        order.setDelivered(isDelivered);
        order.setDeliveryDatetime(deliveryDatetime);
        order.setCreationTimestamp(creationTimestamp);
        order.setLastUpdateTimestamp(lastUpdateTimestamp);
        return order;
    }

    public static Set<Item> createItemList (int number) {
        Random random = new Random();
        return Stream.iterate(0, i -> i + 1).limit(number)
                .map(v -> createItem(UUID.randomUUID(), Math.abs(random.nextInt() % 10) + 1))
                .collect(Collectors.toSet());
    }

    public static Item createItem (UUID id, int quantity) {
        Item item = new Item();
        item.setId(id);
        item.setQuantity(quantity);
        return item;
    }

    public static String generateString (int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

}
