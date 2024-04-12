package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Customer;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCreatedEvent;
import edu.lsu.restaurant.order.system.order.service.domain.exception.OrderDomainException;
import edu.lsu.restaurant.order.system.order.service.domain.mapper.OrderDataMapper;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.CustomerRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.OrderRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// we created this to delegate the command handling to the application service
@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateOrderCommandHandler {
    private final OrderCreatedHelper orderCreatedHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreatedHelper.persistOrder(createOrderCommand);
        log.info("Order with id {} created", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());
    }
}
