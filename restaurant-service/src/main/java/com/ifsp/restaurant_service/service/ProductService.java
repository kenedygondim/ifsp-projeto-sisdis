package com.ifsp.restaurant_service.service;

import com.ifsp.restaurant_service.dto.ProductDTO;
import com.ifsp.restaurant_service.entity.Product;
import com.ifsp.restaurant_service.repository.ProductRepository;
import com.ifsp.restaurant_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ProductDTO createProduct(ProductDTO dto) {
        restaurantRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Product product = Product.builder()
                .nome(dto.getNome())
                .valor(dto.getValor())
                .categoria(dto.getCategoria())
                .restauranteId(dto.getRestauranteId())
                .build();

        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByRestaurant(Long restaurantId) {
        return productRepository.findByRestauranteId(restaurantId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDTO(product);
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .nome(product.getNome())
                .valor(product.getValor())
                .categoria(product.getCategoria())
                .restauranteId(product.getRestauranteId())
                .build();
    }
}
