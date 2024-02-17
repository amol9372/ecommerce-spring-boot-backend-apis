package org.ecomm.ecommcart.exception;

import org.springframework.http.HttpStatus;

public class SoldOutInventoryException extends BaseException  {

    public SoldOutInventoryException(HttpStatus status, ErrorResponse errorResponse) {
        super(status, errorResponse);
    }
}
