package edu.lsu.restaurant.order.system.domain.valueobject;

import lombok.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseId<T> {
    private final T value;
}
