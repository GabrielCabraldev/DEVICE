package com.project.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFieldsException extends RuntimeException{
    public InvalidFieldsException(String message) {
        super(message);
    }
}
