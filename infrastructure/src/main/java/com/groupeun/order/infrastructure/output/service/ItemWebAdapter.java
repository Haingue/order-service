package com.groupeun.order.infrastructure.output.service;

import com.groupeun.order.domain.model.Item;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemWebAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Timer latencyMetrics = Metrics.timer("order.infrastructure.ingredient_adapter.latency", "action", "fetchProduct");
    private Counter errorMetrics;
    private WebClient client;

    @Value("${com.groupeun.product.url}")
    private String productServiceUrl;

    @Autowired
    MeterRegistry registry;
    @Autowired
    HttpServletRequest request;

    @PostConstruct
    private void initialize () {
        client = WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
        errorMetrics = Counter.builder("order.infrastructure.ingredient_adapter.error")
                .tag("http_code", "!200").register(registry);
    }

    public Set<Item> getItemDetails(Set<Item> items) {
        try {
            logger.debug("Fetch item api: {}", items);
            LocalDateTime timestamp = LocalDateTime.now();
            ResponseEntity<List<Item>> response = client
                    .post()
                    .uri("/product/details")
                    .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                    .body(BodyInserters.fromValue(items))
                    .retrieve()
                    .toEntityList(Item.class)
                    .block();
            latencyMetrics.record(Duration.between(timestamp, LocalDateTime.now()));
            if (response.getStatusCode().is2xxSuccessful()) {
                return new HashSet<>(response.getBody());
            }
            errorMetrics.increment();
            logger.error("Error to collect item details (http code: {})", response.getStatusCode().value());
        } catch (WebClientRequestException ex) {}
        // throw new DomainException(String.format("Error to collect item details (http code: %s)", response.getStatusCode().value()));
        return Collections.emptySet();
    }

}
