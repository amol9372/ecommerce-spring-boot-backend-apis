package org.ecomm.ecommgateway.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
  public UserAlreadyExistsException(HttpStatus status, ErrorResponse errorResponse) {
    super(status, errorResponse);
  }
}
