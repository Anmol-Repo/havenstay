package com.havenstay.payment.razorpay;

import com.havenstay.payment.razorpay.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    //both working 👍👍👍
    private final PaymentService paymentService;
    //excellent working 👍👍
    @PostMapping("/pay")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }

    //excellent working 👍👍
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPayment(
            @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(
                paymentService.verifyPayment(paymentRequest)
        );
    }
}