package edu.lsu.restaurant.order.system.order.service.domain;

import edu.lsu.restaurant.order.system.domain.valueobject.ProductId;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Product;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCancelledEvent;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderCreatedEvent;
import edu.lsu.restaurant.order.system.order.service.domain.event.OrderPaidEvent;
import edu.lsu.restaurant.order.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    private final static String UTC = "UTC";
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }




    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approvedOrder(Order order) {
        order.approve();
        log.info("Order with id {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order with id {} is cancelling payment", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {

    }
    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()){
            throw new OrderDomainException("Restaurant with id "+restaurant.getId().getValue()+" is not active!");
        }
    }
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        Map<ProductId, Product> productMap = new HashMap<>();
        restaurant.getProducts().forEach(product -> productMap.put(product.getId(), product));

        order.getItems().forEach(orderItem -> {
            Product currentProduct = productMap.get(orderItem.getProduct().getId());
            if (currentProduct != null) {
                currentProduct.updateWithConfirmedNameAndPrice(
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice()
                );
            }
        });
//        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(product -> {
//            Product currentProduct = orderItem.getProduct();
//            if (currentProduct.equals(product)) {
//                currentProduct.updateWithConfirmedNameAndPrice(product.getName(),
//                        product.getPrice());
//            }
//        }));
    }
}
