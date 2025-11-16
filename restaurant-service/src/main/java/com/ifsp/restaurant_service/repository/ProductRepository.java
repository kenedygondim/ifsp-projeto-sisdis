package com.ifsp.restaurant_service.repository;

import com.ifsp.restaurant_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRestauranteId(Long restauranteId);
}