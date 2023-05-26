package com.groupeun.order.infrastructure.output.repository;

import com.groupeun.order.infrastructure.output.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    List<OrderEntity> findAllByBuyerOrderByCreationTimestampDesc (String buyerId);

    List<OrderEntity> findAllByBuyerAndDeliveredOrderByCreationTimestampDesc (String buyerId, boolean delivered);

}
