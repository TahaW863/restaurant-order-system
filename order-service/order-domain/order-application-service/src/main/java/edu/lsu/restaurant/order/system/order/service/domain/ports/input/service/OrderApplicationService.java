package edu.lsu.restaurant.order.system.order.service.domain.ports.input.service;

import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackingOrderQuery;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse CreateOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackingOrderQuery trackingOrderQuery);
}
