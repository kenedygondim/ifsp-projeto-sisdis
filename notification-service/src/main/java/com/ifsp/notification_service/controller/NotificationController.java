package com.ifsp.notification_service.controller;

import com.ifsp.notification_service.entity.Notification;
import com.ifsp.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Notification>> getNotificationsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(notificationService.getNotificationsByOrder(orderId));
    }
}