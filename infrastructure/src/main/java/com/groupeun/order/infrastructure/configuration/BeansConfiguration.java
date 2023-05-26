package com.groupeun.order.infrastructure.configuration;

import com.groupeun.order.application.ports.input.OrderInputPort;
import com.groupeun.order.application.ports.input.SettingInputPort;
import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.application.ports.output.OrderOutputPort;
import com.groupeun.order.application.ports.output.SettingOutputPort;
import com.groupeun.order.domain.service.ItemService;
import com.groupeun.order.domain.service.OrderService;
import com.groupeun.order.domain.service.SettingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public SettingInputPort settingInputPort (SettingOutputPort settingOutputPort) {
        return new SettingService(settingOutputPort);
    }

    @Bean
    public ItemService itemService (ItemOutputPort itemOutputPort) {
        return new ItemService(itemOutputPort);
    }

    @Bean
    public OrderInputPort orderInputPort (OrderOutputPort orderOutputPort, ItemService itemService) {
        return new OrderService(orderOutputPort, itemService);
    }

}
