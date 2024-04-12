package edu.lsu.restaurant.order.system.order.service.domain.entity;

import edu.lsu.restaurant.order.system.domain.entity.AggregateRoot;
import edu.lsu.restaurant.order.system.domain.valueobject.*;
import edu.lsu.restaurant.order.system.order.service.domain.exception.OrderDomainException;
import edu.lsu.restaurant.order.system.order.service.domain.valueobject.OrderItemId;
import edu.lsu.restaurant.order.system.order.service.domain.valueobject.StreetAddress;
import edu.lsu.restaurant.order.system.order.service.domain.valueobject.TrackingId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failuresMessages;

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failuresMessages = builder.failuresMessages;
    }

    public void validateOrder(){
        validateInitializeOrder();
        validateTotalPrice();
        validateItemsPrice();
    }
    public void pay(){
        if (Objects.isNull(this.orderStatus) || !this.orderStatus.equals(OrderStatus.PENDING)) {
            throw new OrderDomainException("Order is not in correct state for payment!");
        }
        this.orderStatus = OrderStatus.PAID;
    }

    public void approve(){
        if(this.orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order is not in correct state for approval!");
        }
        this.orderStatus = OrderStatus.APPROVED;
    }
    public void initCancel(List<String> failuresMessages){
        if(this.orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order is not in correct state for initCancel!");
        }
        this.orderStatus = OrderStatus.CANCELLING;
        updateFailuresMessages(failuresMessages);
    }

    public void cancel(List<String> failuresMessages){
        if(!(this.orderStatus == OrderStatus.CANCELLING || this.orderStatus == OrderStatus.PENDING)){
            throw new OrderDomainException("Order is not in correct state for cancel!");
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }
    private void updateFailuresMessages(List<String> failuresMessages) {
        if(Objects.nonNull(failuresMessages) && Objects.nonNull(this.failuresMessages)){
            this.failuresMessages.addAll(failuresMessages.stream().filter(Objects::nonNull).toList());
        }
        if(Objects.isNull(this.failuresMessages)){
            this.failuresMessages = failuresMessages;
        }
    }
    private void validateInitializeOrder() {
        if(Objects.nonNull(this.orderStatus) && Objects.nonNull(getId())){
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }
    private void validateTotalPrice() {
        if(Objects.isNull(this.price) || !this.price.isGreaterThanZero()){
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }
    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(OrderItem -> {
           validateItemPrice(OrderItem);
           return OrderItem.getSubtotal();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price:" + price.getAmount()
                + " is not equal to the sum of items prices:" + orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: " +
                orderItem.getPrice().getAmount() + " is not valid for Product: " +
                orderItem.getProduct().getId().getValue() + "!");
        }
    }

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        this.trackingId = new TrackingId(UUID.randomUUID());
        this.orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem item : items) {
            item.initializeOrderItem(getId(), new OrderItemId(itemId++));
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failuresMessages;

        private Builder() {
        }



        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failuresMessages(List<String> val) {
            failuresMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
