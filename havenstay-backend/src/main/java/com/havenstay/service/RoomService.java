package com.havenstay.service;

import com.havenstay.dto.ResponseDTO;
import com.havenstay.dto.RoomDTO;
import com.havenstay.enums.RoomType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    ResponseDTO addRoom(RoomDTO roomDTO, MultipartFile imageFile);

    ResponseDTO updateRoom(RoomDTO roomDTO, MultipartFile imageFile);

    ResponseDTO getAllRooms();

    ResponseDTO getRoomById(Long id);

    ResponseDTO deleteRoom(Long id);

    ResponseDTO getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType);

    List<RoomType> getAllRoomTypes();

    ResponseDTO searchRoom(String input);

}
