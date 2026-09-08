package com.havenstay.service;


import com.havenstay.dto.BookingDTO;
import com.havenstay.dto.ResponseDTO;

public interface BookingService {


    ResponseDTO getAllBookings();
    ResponseDTO createBooking(BookingDTO bookingDTO);

    ResponseDTO findBookingByReferenceNo(String  bookingReference);

    ResponseDTO updateBooking(BookingDTO bookingDTO);

}
