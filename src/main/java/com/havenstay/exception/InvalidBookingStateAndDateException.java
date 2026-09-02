package com.havenstay.exception;

public class InvalidBookingStateAndDateException extends RuntimeException{
    public InvalidBookingStateAndDateException(String message){
        super(message);
    }
}
