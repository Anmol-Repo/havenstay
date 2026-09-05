package com.havenstay.entity;

import com.havenstay.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Room Number must be at least 1")
    @Column(unique = true)
    @NotNull(message = "Room Number is Required")
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Room Type is required")
    private RoomType Type;

    @DecimalMin(value = "0.1", message = "Price per night is required")
    @NotNull(message = "Price is required")
    private BigDecimal pricePerNight;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "capacity must be at least 1")
    private  Integer capacity;

    private String description;

    private String imageUrl;




}
