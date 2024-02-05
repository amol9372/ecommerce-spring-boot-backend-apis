package org.ecomm.ecommorder.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

  String code; // application specific code
  String message; // ex.getMessage();
}
