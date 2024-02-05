package org.ecomm.ecommorder.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class EcommCartExceptionHandler {

  /*
  Handle Internal Server error exceptions
   */
  @ExceptionHandler(BaseException.class)
  protected ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {

    var errorResponse =
            ErrorResponse.builder()
                    .code(ex.getErrorResponse().getCode())
                    .message(ex.getErrorResponse().getMessage())
                    .build();

    return new ResponseEntity<>(errorResponse, ex.getStatus());
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<ErrorResponse> handleGenericError2(Throwable ex) {
    log.error("Unhandled Exception", ex);
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .code(ErrorCodes.INTERNAL_SERVER_ERROR)
            //            .message(ErrorCodes.errorCodesMap.get(ErrorCodes.INTERNAL_SERVER_ERROR))
            .message(ex.getLocalizedMessage())
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value
          = { IllegalArgumentException.class, IllegalStateException.class, ConstraintViolationException.class })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<ErrorResponse> handleGenericError(Throwable ex) {
    log.error("Unhandled Exception", ex);
    ErrorResponse errorResponse = ErrorResponse.builder()
            .code(ErrorCodes.INTERNAL_SERVER_ERROR)
            .message(ErrorCodes.errorCodesMap.get(ErrorCodes.INTERNAL_SERVER_ERROR))
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
