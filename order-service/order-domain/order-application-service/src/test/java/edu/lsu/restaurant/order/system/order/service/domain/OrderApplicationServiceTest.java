package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.mapper.OrderDataMapper;
import edu.lsu.restaurant.order.system.order.service.domain.ports.input.service.OrderApplicationService;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.CustomerRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.OrderRepository;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
@AllArgsConstructor
public class OrderApplicationServiceTest {
    private final OrderApplicationService orderApplicationService;
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final CreateOrderCommand createOrderCommand;
    private final CreateOrderCommand createOrderCommandWrongPrice;
}
