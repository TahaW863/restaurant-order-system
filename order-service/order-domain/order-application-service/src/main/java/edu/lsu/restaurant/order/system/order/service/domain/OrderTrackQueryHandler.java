package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackingOrderQuery;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.exception.OrderNotFoundException;
import edu.lsu.restaurant.order.system.order.service.domain.mapper.OrderDataMapper;
import edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository.OrderRepository;
import edu.lsu.restaurant.order.system.order.service.domain.valueobject.TrackingId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderTrackQueryHandler {
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackingOrderQuery trackingOrderQuery) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(new TrackingId(trackingOrderQuery.getOrderTrackingId()));
        if (optionalOrder.isEmpty()) {
            log.warn("Order with tracking id {} not found", trackingOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Order not found");
        }
        return orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
    }
}
