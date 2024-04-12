package edu.lsu.restaurant.order.system.order.service.domain.event;

import edu.lsu.restaurant.order.system.domain.event.DomainEvent;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import lombok.Getter;

import java.time.ZonedDateTime;
@Getter
public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
