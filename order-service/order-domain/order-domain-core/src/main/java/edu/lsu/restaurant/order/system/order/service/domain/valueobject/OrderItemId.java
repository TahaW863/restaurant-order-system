package edu.lsu.restaurant.order.system.order.service.domain.valueobject;

import edu.lsu.restaurant.order.system.domain.valueobject.BaseId;
import edu.lsu.restaurant.order.system.domain.valueobject.Money;
import edu.lsu.restaurant.order.system.domain.valueobject.OrderId;
import edu.lsu.restaurant.order.system.domain.valueobject.ProductId;
import edu.lsu.restaurant.order.system.order.service.domain.entity.OrderItem;
import edu.lsu.restaurant.order.system.order.service.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;


public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
