package com.ifsp.order_service.controller;

import com.ifsp.order_service.dto.CreateOrderRequestDTO;
import com.ifsp.order_service.dto.OrderDTO;
import com.ifsp.order_service.enumeration.OrderStatusEnum;
import com.ifsp.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatusEnum status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}