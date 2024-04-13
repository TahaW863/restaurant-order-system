package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.CustomerRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.OrderRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "edu.lsu.restaurant.order.system")
public class OrderTestConfiguration {
    // create spring beans as mock beans
    // lets start with publishers
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }
    // below are the output ports
    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }
    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }
    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }
    // below create real bean
    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

}
