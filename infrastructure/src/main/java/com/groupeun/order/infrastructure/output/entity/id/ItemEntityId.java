package com.groupeun.order.infrastructure.output.entity.id;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ItemEntityId implements Serializable {

    @Column
    private String itemId;
    @Column
    private String orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntityId recipeStepId = (ItemEntityId) o;
        return itemId.equals(recipeStepId.itemId) && orderId.equals(recipeStepId.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, orderId);
    }

    @Override
    public String toString() {
        return "ItemEntityId{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                '}';
    }
}
