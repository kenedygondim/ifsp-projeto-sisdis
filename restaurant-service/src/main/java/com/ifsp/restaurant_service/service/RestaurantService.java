package com.ifsp.restaurant_service.service;

import com.ifsp.restaurant_service.dto.RestaurantDTO;
import com.ifsp.restaurant_service.entity.Restaurant;
import com.ifsp.restaurant_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantDTO createRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = Restaurant.builder()
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .ativo(true)
                .build();

        Restaurant saved = restaurantRepository.save(restaurant);
        return toDTO(saved);
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return toDTO(restaurant);
    }

    private RestaurantDTO toDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .nome(restaurant.getNome())
                .endereco(restaurant.getEndereco())
                .build();
    }
}
