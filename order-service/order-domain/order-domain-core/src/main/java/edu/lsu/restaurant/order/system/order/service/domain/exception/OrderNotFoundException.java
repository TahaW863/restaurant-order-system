package edu.lsu.restaurant.order.system.order.service.domain.exception;

import edu.lsu.restaurant.order.system.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
