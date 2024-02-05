package org.ecomm.ecommuser.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    HttpStatus status;
    ErrorResponse errorResponse;
    String[] args;


    protected BaseException(HttpStatus status,ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.status = status;
        this.errorResponse = errorResponse;
    }

}
