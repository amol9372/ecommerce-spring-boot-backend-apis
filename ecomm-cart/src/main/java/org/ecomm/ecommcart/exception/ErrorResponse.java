package org.ecomm.ecommcart.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

  private String code; // application specific code
  private String message; // ex.getMessage();
}
