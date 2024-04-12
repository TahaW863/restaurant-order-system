package edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.restaurantapproval;

import edu.lsu.restaurant.order.system.domain.event.publisher.DomainEventPublisher;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
