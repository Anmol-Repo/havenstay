package com.havenstay;

import com.havenstay.dto.NotificationDTO;
import com.havenstay.enums.NotificationType;
import com.havenstay.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HavenstayApplication implements CommandLineRunner {

	@Autowired
	private NotificationService  notificationService;
	public static void main(String[] args) {
		SpringApplication.run(HavenstayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		NotificationDTO notificationDTO = NotificationDTO.builder()
				.type(NotificationType.EMAIL)
				.recipient("chiragranamr.gamer@gmail.com")
				.body("I am testing this from a command line 👍")
				.subject("Testing Email Sending")
				.build();
		notificationService.sendEmail(notificationDTO);
	}
}
