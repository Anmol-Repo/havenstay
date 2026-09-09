package com.havenstay.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havenstay.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDTO {

    private Long id;

    @NotNull(message = "Subject is required")
    private String subject;

    @NotNull(message = "Recipient is required")
    private String recipient;

    private String body;

    private String bookingReference;

    private NotificationType type;

    private LocalDateTime createdAt ;

}


