package edu.lsu.restaurant.order.system.order.service.domain.mapper;

import edu.lsu.restaurant.order.system.domain.valueobject.CustomerId;
import edu.lsu.restaurant.order.system.domain.valueobject.Money;
import edu.lsu.restaurant.order.system.domain.valueobject.ProductId;
import edu.lsu.restaurant.order.system.domain.valueobject.RestaurantId;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderCommand;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.CreateOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.dto.create.OrderAddress;
import edu.lsu.restaurant.order.system.order.service.domain.dto.track.TrackOrderResponse;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Order;
import edu.lsu.restaurant.order.system.order.service.domain.entity.OrderItem;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Product;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;
import edu.lsu.restaurant.order.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getOrderItems().stream().map(orderItem ->
                        Product.builder()
                                .productId(new ProductId(orderItem.getProductId()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemsEntity(createOrderCommand.getOrderItems()))
                .build();
    }
    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }
    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailuresMessages())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntity(List<edu.lsu.restaurant.order.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                OrderItem.builder()
                        .product(Product.builder()
                                .productId(new ProductId(orderItem.getProductId()))
                                .build())
                        .quantity(orderItem.getQuantity())
                        .price(new Money(orderItem.getPrice()))
                        .subtotal(new Money(orderItem.getSubtotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity());
    }
}
