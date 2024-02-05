package org.ecomm.ecommgateway.exception;

import org.springframework.http.HttpStatus;

public class JWTException extends BaseException  {

    public JWTException(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}
