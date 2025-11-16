package com.ifsp.restaurant_service.controller;

import com.ifsp.restaurant_service.dto.ProductDTO;
import com.ifsp.restaurant_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ProductDTO>> getProductsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getProductsByRestaurant(restaurantId));
    }
}