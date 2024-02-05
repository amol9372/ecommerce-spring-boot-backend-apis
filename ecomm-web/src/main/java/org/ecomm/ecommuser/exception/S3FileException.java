package org.ecomm.ecommuser.exception;

import org.springframework.http.HttpStatus;

public class S3FileException extends BaseException {

  public S3FileException(HttpStatus status, ErrorResponse errorResponse) {
    super(status, errorResponse);
  }
}
