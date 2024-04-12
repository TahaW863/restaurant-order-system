package edu.lsu.restaurant.order.system.order.service.domain.exception;

import edu.lsu.restaurant.order.system.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
