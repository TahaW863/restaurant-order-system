package edu.lsu.restaurant.order.system.domain.event.publisher;

import edu.lsu.restaurant.order.system.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent>{
    void publish(T domainEvent);
}
