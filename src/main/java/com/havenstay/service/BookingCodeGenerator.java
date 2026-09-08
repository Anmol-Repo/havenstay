package com.havenstay.service;

import com.havenstay.entity.BookingReference;
import com.havenstay.repository.BookingReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {
    private final BookingReferenceRepository bookingReferenceRepository;

    public String generateBookingReference(){
        String bookingReference;

        do {
            bookingReference = generatedRandomAlphaNumericCode(10);
        }while(isBookingReferenceExist(bookingReference));

        saveBookingReferenceToDatabase(bookingReference);

        return  bookingReference;
    }

    private String generatedRandomAlphaNumericCode(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random =  new Random();
        StringBuilder stringBuilder = new StringBuilder(length) ;

        for (int i=0; i< length; i++){
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();

    }

    private boolean isBookingReferenceExist(String bookingReference){
        return bookingReferenceRepository.findByReferenceNo(bookingReference).isPresent();
    }

    private void saveBookingReferenceToDatabase(String bookingReference){
        BookingReference newBookingReference = BookingReference.builder()
                .referenceNo(bookingReference).build();
        bookingReferenceRepository.save(newBookingReference);
    }
}
