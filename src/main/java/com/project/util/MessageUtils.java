package com.project.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
  public ResponseEntity<String> messagesResponseEntity(String message, HttpStatus status) {
        return new ResponseEntity<>(message, status);
    }

}
