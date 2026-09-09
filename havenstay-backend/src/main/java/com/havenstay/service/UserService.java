package com.havenstay.service;

import com.havenstay.dto.LoginRequestDTO;
import com.havenstay.dto.RegistrationRequestDTO;
import com.havenstay.dto.ResponseDTO;
import com.havenstay.dto.UserDTO;
import com.havenstay.entity.User;

public interface UserService {
    ResponseDTO registerUser(RegistrationRequestDTO registrationRequest);
    ResponseDTO loginUser(LoginRequestDTO loginRequestDTO );
    ResponseDTO getAllUser();
    ResponseDTO getOwnAccountDetails();
    User getCurrentLoggedInUser();
    ResponseDTO updateOwnAccount(UserDTO userDTO);
    ResponseDTO deleteOwnAccount();
    ResponseDTO getMyBookingHistory();

}
