package org.ecomm.ecommcart.exception;

import org.springframework.http.HttpStatus;

public class UserNotFound extends BaseException  {

    public UserNotFound(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}