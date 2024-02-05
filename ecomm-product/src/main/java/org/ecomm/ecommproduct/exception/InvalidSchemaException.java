package org.ecomm.ecommproduct.exception;

import org.springframework.http.HttpStatus;

public class InvalidSchemaException extends BaseException  {

    public InvalidSchemaException(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}
