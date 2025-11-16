package com.ifsp.notification_service.listener;

import com.ifsp.notification_service.config.RabbitMQConfig;
import com.ifsp.notification_service.event.OrderEvent;
import com.ifsp.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderEvent(OrderEvent event) {
        log.info("Received order event: {}", event);
        notificationService.processOrderEvent(event);
    }
}
