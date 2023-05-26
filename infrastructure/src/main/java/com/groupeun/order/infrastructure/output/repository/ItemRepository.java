package com.groupeun.order.infrastructure.output.repository;

import com.groupeun.order.infrastructure.output.entity.ItemEntity;
import com.groupeun.order.infrastructure.output.entity.id.ItemEntityId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, ItemEntityId> {

    Set<ItemEntity> findAllByIdOrderId (String orderId);

    @Modifying
    long deleteAllByIdOrderId (String orderId);

}
