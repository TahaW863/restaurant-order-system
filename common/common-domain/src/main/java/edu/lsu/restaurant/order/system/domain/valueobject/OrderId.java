package edu.lsu.restaurant.order.system.domain.valueobject;

import lombok.Data;

import java.util.UUID;

public class OrderId extends BaseId<UUID>{
    public OrderId(UUID value) {
        super(value);
    }
}
