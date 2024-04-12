package edu.lsu.restaurant.order.system.order.service.domain.valueobject;

import edu.lsu.restaurant.order.system.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID id) {
        super(id);
    }
}
