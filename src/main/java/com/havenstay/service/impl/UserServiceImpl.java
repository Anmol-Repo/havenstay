package com.havenstay.service.impl;

import com.havenstay.dto.*;
import com.havenstay.entity.Booking;
import com.havenstay.entity.User;
import com.havenstay.enums.UserRole;
import com.havenstay.exception.InvalidCredentialException;
import com.havenstay.exception.NotFoundException;
import com.havenstay.repository.BookingRepository;
import com.havenstay.repository.UserRepository;
import com.havenstay.security.JwtUtils;
import com.havenstay.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;


    @Override
    public ResponseDTO registerUser(RegistrationRequestDTO registrationRequest) {
        UserRole role = UserRole.CUSTOMER;

        if(registrationRequest.getRole() != null){
            role = registrationRequest.getRole();
        }

        User userToSave = User.builder()
                              .firstName(registrationRequest.getFirstName())
                              .lastName(registrationRequest.getLastName())
                              .email(registrationRequest.getEmail())
                              .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .isActive(Boolean.TRUE)
                .build();
        userRepository.save(userToSave);
        return ResponseDTO.builder()
                .status(200)
                .message("user created successfully")
                .build();

    }

    @Override
    public ResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(()-> new NotFoundException("Email Not Found"));
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialException("Password Doesn't Match");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseDTO.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .isActive(user.getIsActive())
                .expirationTime("^ months")
                 .build();
    }

    @Override
    public ResponseDTO getAllUser() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDTO> userDTOList = modelMapper.map(users, new TypeToken<List<UserDTO>>(){

        }.getType());
        return ResponseDTO.builder()
                .status(200)
                .message("success")
                .users(userDTOList)
                .build();
    }

    @Override
    public ResponseDTO getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseDTO.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();

    }


    @Override
    public User getCurrentLoggedInUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
    }

    @Override
    public ResponseDTO updateOwnAccount(UserDTO userDTO) {
        User existingUser = getCurrentLoggedInUser();
        log.info("Inside update user");

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getFirstName() != null) existingUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) existingUser.setLastName(userDTO.getLastName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);

        return ResponseDTO.builder()
                .status(200)
                .message("user updated successfully")
                .build();
    }

    @Override
    public ResponseDTO deleteOwnAccount() {
        User user = getCurrentLoggedInUser();
        userRepository.delete(user);

        return ResponseDTO.builder()
                .status(200)
                .message("user deleted successfully")
                .build();
    }

    @Override
    public ResponseDTO getMyBookingHistory() {
        User user = getCurrentLoggedInUser();

        List<Booking> bookingList = bookingRepository.findByUserId(user.getId());


        List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>(){}.getType());

        return ResponseDTO.builder()
                .status(200)
                .message("success")
                .bookings(bookingDTOList)
                .build();


    }
}
