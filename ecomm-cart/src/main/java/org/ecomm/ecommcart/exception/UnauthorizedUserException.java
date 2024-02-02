package org.ecomm.ecommcart.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends BaseException  {

    public UnauthorizedUserException(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}