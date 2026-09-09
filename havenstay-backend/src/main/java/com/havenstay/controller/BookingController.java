package com.havenstay.controller;

import com.havenstay.dto.BookingDTO;
import com.havenstay.dto.ResponseDTO;
import com.havenstay.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    //Working as intended 👍
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
//Working as intended  and its also sending mail to user entered email 👍
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')  ")
    public ResponseEntity<ResponseDTO> createBooking(@RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

//Working as intended 👍
    @GetMapping("/{reference}")
    public ResponseEntity<ResponseDTO> findBookingByReferenceNo(@PathVariable String reference){
        return ResponseEntity.ok(bookingService.findBookingByReferenceNo(reference));
    }

//Working correctly 👍
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.updateBooking(bookingDTO));
    }













}
