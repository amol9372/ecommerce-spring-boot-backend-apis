package org.ecomm.ecommgateway.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {

    private HttpStatus status;
    private ErrorResponse errorResponse;
    private String[] args;


    protected BaseException(HttpStatus status,ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.status = status;
        this.errorResponse = errorResponse;
    }

}
