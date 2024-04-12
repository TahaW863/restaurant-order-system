package edu.lsu.restaurant.order.system.order.service.domain.valueobject;

import edu.lsu.restaurant.order.system.domain.valueobject.BaseId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "id")
public class StreetAddress {
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;
}
