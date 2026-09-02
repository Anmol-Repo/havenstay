package com.havenstay.exception;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message){
        super(message);
    }
}

