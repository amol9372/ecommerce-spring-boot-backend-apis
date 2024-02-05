package org.ecomm.ecommuser.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends BaseException  {

    public ResourceNotFound(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}