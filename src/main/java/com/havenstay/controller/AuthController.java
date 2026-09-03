package com.havenstay.controller;

import com.havenstay.dto.LoginRequestDTO;
import com.havenstay.dto.RegistrationRequestDTO;
import com.havenstay.dto.ResponseDTO;
import com.havenstay.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //Working well 👍
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid RegistrationRequestDTO request){
        return ResponseEntity.ok(userService.registerUser(request)) ;
    }
    //Working well also 👍
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO request){
        return ResponseEntity.ok(userService.loginUser(request));
    }

}
