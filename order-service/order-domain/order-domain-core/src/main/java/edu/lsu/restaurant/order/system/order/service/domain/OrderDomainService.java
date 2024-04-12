package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCancelledEvent;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCreatedEvent;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approvedOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
