package edu.lsu.restaurant.order.system.order.service.domain.ports.output.respository;

import edu.lsu.restaurant.order.system.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);

}
