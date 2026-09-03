package com.havenstay.service;

import com.havenstay.dto.NotificationDTO;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO);

    void sendSms(); // maybe in future ? will try

    void sendWhatsapp(); //in future maybe
}
