package com.havenstay.payment.razorpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String bookingReference;
    private BigDecimal amount;

    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;

}

