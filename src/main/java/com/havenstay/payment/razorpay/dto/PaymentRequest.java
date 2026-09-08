package com.havenstay.payment.razorpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//import com.havenstay.exception.PaymentException;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;

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

