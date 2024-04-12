package edu.lsu.restaurant.order.system.order.service.domain.event;

import edu.lsu.restaurant.order.system.domain.event.DomainEvent;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import lombok.Getter;

import java.time.ZonedDateTime;
@Getter
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;
    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }
}
