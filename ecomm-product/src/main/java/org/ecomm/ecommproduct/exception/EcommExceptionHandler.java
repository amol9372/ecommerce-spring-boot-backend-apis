package org.ecomm.ecommproduct.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class EcommExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleBaseException(BaseException ex) {

        var errorResponse =
                ErrorResponse.builder()
                        .code(ex.getErrorResponse().getCode())
                        .message(ex.getErrorResponse().getMessage())
                        .build();

        //    var apiResponse =
        //        ApiResponse.errorResponse(List.of(errorResponse), "An exception occurred in the API");

        //    log.error("[{}] Error while processing requests ::: ", MDC.get("requestId"), ex);
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleGenericError(Throwable ex) {
        log.error("Unhandled Exception", ex);
        return ErrorResponse.builder()
                .code(ErrorCodes.INTERNAL_SERVER_ERROR)
                .message(ErrorCodes.errorCodesMap.get(ErrorCodes.INTERNAL_SERVER_ERROR))
                .build();
    }
}
