package com.havenstay.controller;

import com.havenstay.dto.ResponseDTO;
import com.havenstay.dto.RoomDTO;
import com.havenstay.enums.RoomType;
import com.havenstay.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
//Have tested all these apis all work fine
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
   private final RoomService roomService;

   @PostMapping("/add")
   @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> addRoom(
            @RequestParam Integer roomNumber,
            @RequestParam RoomType type,
            @RequestParam BigDecimal pricePerNight,
            @RequestParam Integer capacity,
            @RequestParam String description,
            @RequestParam MultipartFile imageFile
   ){
       RoomDTO roomDTO = RoomDTO.builder()
               .type(type)
               .pricePerNight(pricePerNight)
               .capacity(capacity)
               .description(description)
               .roomNumber(roomNumber)
               .build();
       return ResponseEntity.ok(roomService.addRoom(roomDTO, imageFile));
   }

// Working
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateRoom(
            @RequestParam(value = "roomNumber", required = false) Integer roomNumber,
            @RequestParam(value = "type", required = false) RoomType type,
            @RequestParam(value = "pricePerNight", required = false) BigDecimal pricePerNight,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "id", required = true) Long id
    ){
        RoomDTO roomDTO = RoomDTO.builder()
                .id(id)
                .type(type)
                .pricePerNight(pricePerNight)
                .capacity(capacity)
                .roomNumber(roomNumber)
                .description(description)
                .build();
        return ResponseEntity.ok(roomService.updateRoom(roomDTO, imageFile));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllRooms(){
       return ResponseEntity.ok(roomService.getAllRooms());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getRoomById(@PathVariable Long id){
       return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteRoom(@PathVariable Long id){
       return ResponseEntity.ok(roomService.deleteRoom(id));
    }


    @GetMapping("/available")
    public ResponseEntity<ResponseDTO> getAvailableRooms(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) RoomType roomType
            ){
        return ResponseEntity.ok(roomService.getAvailableRooms(checkInDate, checkOutDate,roomType));
    }


    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes(){
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }



    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchRoom(@RequestParam String input){
        return ResponseEntity.ok(roomService.searchRoom(input));
    }









}
