package com.havenstay.payment.razorpay;

import com.havenstay.dto.NotificationDTO;
import com.havenstay.entity.Booking;
import com.havenstay.entity.Payment;
import com.havenstay.exception.NotFoundException;
import com.havenstay.payment.razorpay.dto.PaymentRequest;
import com.havenstay.repository.BookingRepository;
import com.havenstay.repository.PaymentRepository;
import com.havenstay.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.RazorpayClient;
import jakarta.annotation.PostConstruct;
import com.havenstay.exception.PaymentException;
import com.razorpay.Order;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import com.havenstay.enums.PaymentGateway;
import com.havenstay.enums.PaymentStatus;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {

    private final BookingRepository bookingRepository;
    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;

    private final PaymentRepository paymentRepository;

    private final NotificationService notificationService;

    public PaymentService(BookingRepository bookingRepository,
                          PaymentRepository paymentRepository,
                          NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.notificationService = notificationService;
    }

    @PostConstruct
    private void init() throws Exception {
        try {
            razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Razorpay client", e);
        }
    }
    public String createPayment(PaymentRequest paymentRequest) {

        Booking booking = bookingRepository.findByBookingReference(
                paymentRequest.getBookingReference()
        ).orElseThrow(() ->
                new NotFoundException("Booking Not Found")
        );
        if (booking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new PaymentException("Payment already made for this booking");
        }

        try {
            JSONObject orderRequest = new JSONObject();

            orderRequest.put(
                    "amount",
                    booking.getTotalPrice()
                            .multiply(BigDecimal.valueOf(100))
                            .longValue()
            );

            orderRequest.put("currency", "INR");

            orderRequest.put(
                    "receipt",
                    "HavenStay_" + paymentRequest.getBookingReference()
            );

            Order order = razorpayClient.orders.create(orderRequest);

            return order.get("id");

        } catch (Exception e) {
            throw new PaymentException("Failed to create Razorpay order");
        }
    }
    public void updatePaymentBooking(PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findByBookingReference(
                paymentRequest.getBookingReference()
        ).orElseThrow(() ->
                new NotFoundException("Booking Not Found")
        );
        if (booking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new PaymentException("Payment already completed for this booking");
        }

        Payment payment = new Payment();

        payment.setPaymentGateway(PaymentGateway.RAZORPAY);
        payment.setAmount(booking.getTotalPrice());
        payment.setTransactionId(paymentRequest.getRazorpayPaymentId());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBookingReference(paymentRequest.getBookingReference());
        payment.setUser(booking.getUser());

        paymentRepository.save(payment);

        booking.setPaymentStatus(PaymentStatus.COMPLETED);
        bookingRepository.save(booking);

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(booking.getUser().getEmail())
                .subject("Booking Payment Successful")
                .body("Congratulations! Your payment for booking with reference: "
                        + paymentRequest.getBookingReference()
                        + " is successful.")
                .bookingReference(paymentRequest.getBookingReference())
                .build();

        notificationService.sendEmail(notificationDTO);
    }

    public boolean verifyPayment(PaymentRequest paymentRequest) {

        if (paymentRequest.getRazorpayPaymentId() == null
                || paymentRequest.getRazorpayOrderId() == null
                || paymentRequest.getRazorpaySignature() == null) {
            throw new PaymentException("Payment verification details are missing");
        }
        try {
            String data = paymentRequest.getRazorpayOrderId()
                    + "|"
                    + paymentRequest.getRazorpayPaymentId();

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    razorpaySecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(secretKeySpec);

            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder signature = new StringBuilder();

            for (byte b : digest) {
                signature.append(String.format("%02x", b));
            }

            boolean isValid = signature.toString()
                    .equals(paymentRequest.getRazorpaySignature());

            if (isValid) {
                updatePaymentBooking(paymentRequest);
            }

            if (!isValid) {
                throw new PaymentException("Invalid Razorpay payment signature");
            }

            updatePaymentBooking(paymentRequest);

            return true;
        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Razorpay payment verification failed", e);
            throw new PaymentException("Failed to verify Razorpay payment");
        }
    }

}