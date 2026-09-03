package com.havenstay.service.impl;

import com.havenstay.dto.NotificationDTO;
import com.havenstay.entity.Notification;
import com.havenstay.enums.NotificationType;
import com.havenstay.repository.NotificationRepository;
import com.havenstay.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
   private final JavaMailSender javaMailSender;
   private final NotificationRepository notificationRepository;

    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO) {
        log.info("Inside send email");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(notificationDTO.getRecipient());
        message.setSubject(notificationDTO.getSubject());
        message.setText(notificationDTO.getBody());

        javaMailSender.send(message);
        //Now lets save it database
        Notification notificationToSave = Notification.builder()
                .recipient(notificationDTO.getRecipient())
                .subject(notificationDTO.getSubject())
                .body(notificationDTO.getBody())
                .bookingReference(notificationDTO.getBookingReference())
                .type(NotificationType.EMAIL)
                .build();

        notificationRepository.save(notificationToSave);
    }

    @Override
    public void sendSms() {

    }

    @Override
    public void sendWhatsapp() {

    }
}
