package com.havenstay.controller;

import com.havenstay.dto.ResponseDTO;
import com.havenstay.dto.UserDTO;
import com.havenstay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
// Working nicely
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }
// Working
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateOwnAccount(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateOwnAccount(userDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteOwnAccount(){

        return ResponseEntity.ok(userService.deleteOwnAccount());
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseDTO> getOwnAccountDetails(){
        return ResponseEntity.ok(userService.getOwnAccountDetails());
    }


    @GetMapping("/bookings")
    public ResponseEntity<ResponseDTO> getMyBookingHistory(){
        return ResponseEntity.ok(userService.getMyBookingHistory());
    }




}
