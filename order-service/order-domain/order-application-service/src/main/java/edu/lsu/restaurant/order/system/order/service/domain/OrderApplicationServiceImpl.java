package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackingOrderQuery;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackingOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
@Slf4j
@Service
@Validated
@AllArgsConstructor
// we don't to expose the implementation of the service, the interface is enough
class OrderApplicationServiceImpl implements  OrderApplicationService {
    private final OrderCreateOrderCommandHandler orderCreateOrderCommandHandler;
    private final OrderTrackQueryHandler orderTrackQueryHandler;
    @Override
    public CreateOrderResponse CreateOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateOrderCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackingOrderResponse trackOrder(TrackingOrderQuery trackingOrderQuery) {
        return orderTrackQueryHandler.trackOrder(trackingOrderQuery);
    }
}
