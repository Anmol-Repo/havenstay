package com.havenstay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havenstay.entity.User;
import com.havenstay.enums.PaymentGateway;
import com.havenstay.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

    private Long id;
    private BookingDTO booking;

    private String transactionId;

    private BigDecimal amount;

    private PaymentGateway paymentMethod; // RazorPay, Stripe

    private LocalDateTime paymentDate;

    private PaymentStatus status;

    private String bookingReference;
    private String failureReason;

    private String approvalLink;

}
