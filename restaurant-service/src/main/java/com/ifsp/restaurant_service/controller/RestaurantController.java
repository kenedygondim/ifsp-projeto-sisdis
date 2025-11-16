package com.ifsp.restaurant_service.controller;

import com.ifsp.restaurant_service.dto.RestaurantDTO;
import com.ifsp.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
}