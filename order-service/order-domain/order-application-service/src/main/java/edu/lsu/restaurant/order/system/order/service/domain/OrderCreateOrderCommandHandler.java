package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Customer;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCreatedEvent;
import edu.lsu.restaurant.order.system.order.service.domain.exception.OrderDomainException;
import edu.lsu.restaurant.order.system.order.service.domain.mapper.OrderDataMapper;
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
    // we need to call domain service to interact with the domain
    private final OrderDomainService orderDomainService;
    // to save the order we need to interact with order repository
    private final OrderRepository orderRepository;
    // to complete business logic we need to interact with customer and restaurant
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    // we need data mapper to map the command to the domain model
    private final OrderDataMapper orderDataMapper;

    // we need to use Transactional annotation to make sure that the operation is atomic
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order savedOrder = saveOrder(order);
        log.info("Order with id {} created", savedOrder.getId());
        return orderDataMapper.orderToCreateOrderResponse(savedOrder);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        return optionalRestaurant.orElseThrow(() -> {
            log.warn("Restaurant with id {} not found", createOrderCommand.getRestaurantId());
            return new OrderDomainException("Restaurant with id " + createOrderCommand.getRestaurantId() + " not found");
        });
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        /*
        to note that if we had to do more business
         checks on the customer we need to pass the
          customer object to the domain service to
           do business logic checks there
         */
        if (customer.isEmpty()) {
            log.warn("Customer with id {} not found", customerId);
            throw new OrderDomainException("Customer with id " + customerId + " not found");
        }
    }
    private Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        if(Objects.isNull(savedOrder)){
            log.error("Order with id {} not saved", order.getId());
            throw new OrderDomainException("Order with id " + order.getId() + " not saved");
        }
        log.info("Order with id {} saved", order.getId());
        return savedOrder;
    }
}
