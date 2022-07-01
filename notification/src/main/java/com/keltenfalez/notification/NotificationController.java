package com.keltenfalez.notification;

import com.keltenfalez.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        Integer customerId = notificationRequest.toCustomerId();
        log.info("New notification sent to customer: {}", customerId);
        notificationService.sendNotification(notificationRequest);
    }
}
