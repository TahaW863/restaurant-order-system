package edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.payment;

import edu.lsu.restaurant.order.system.domain.event.publisher.DomainEventPublisher;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
