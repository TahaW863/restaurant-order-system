package edu.lsu.restaurant.order.system.order.service.domain.entity;

import edu.lsu.restaurant.order.system.domain.entity.BaseEntity;
import edu.lsu.restaurant.order.system.domain.valueobject.Money;
import edu.lsu.restaurant.order.system.domain.valueobject.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductId>{
    private String name;
    private Money price;
    public Product(ProductId id, String name, Money price) {
        super.setId(id);
        this.name = name;
        this.price = price;
    }
    public Product(ProductId id) {
        super.setId(id);
    }

    private Product(Builder builder) {
        super.setId(builder.productId);
        name = builder.name;
        price = builder.price;
    }
    public static Builder builder() {
        return new Builder();
    }
    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public static final class Builder {
        private ProductId productId;
        private String name;
        private Money price;

        private Builder() {
        }



        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
