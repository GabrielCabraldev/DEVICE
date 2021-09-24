package com.project.util.error;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContaInvalidaException extends NestedRuntimeException {
    public ContaInvalidaException(String message) {
        super(message);
    }
}
