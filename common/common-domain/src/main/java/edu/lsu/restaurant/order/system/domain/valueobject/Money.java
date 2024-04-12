package edu.lsu.restaurant.order.system.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Money {
    private final BigDecimal amount;
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public boolean isGreaterThanZero() {
        return isNotEmpty() && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }
    public boolean isGreaterThan(Money other) {
        return isNotEmpty() && this.amount.compareTo(other.amount) > 0;
    }
    public Money add(Money other) {
        return new Money(setScale(this.amount.add(other.amount)));
    }
    public Money subtract(Money other) {
        return new Money(setScale(this.amount.subtract(other.amount)));
    }
    public Money multiply(int value) {
        return new Money(setScale(this.amount.multiply(BigDecimal.valueOf(value))));
    }
    private boolean isNotEmpty() {
        return Objects.nonNull(this.amount);
    }
    private BigDecimal setScale(BigDecimal value) {
        int scale = 2;
        return value.setScale(scale, RoundingMode.HALF_EVEN);
    }
}
