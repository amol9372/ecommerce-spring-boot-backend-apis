package org.ecomm.ecommgateway.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {

  public static final String NOT_FOUND = "not_found";

  private ErrorCodes() {}

  // invalid schema - multiple codes
  public static final String INVALID_SCHEMA = "invalid_schema";

  public static final String INTERNAL_SERVER_ERROR = "internal_server_error";

  public static final String CONSTRAINT_VIOLATION_EXCEPTION = "constraint_violation_exception";

  public static final String USER_ALREADY_EXISTS = "user_already_exists";

  public static final String S3_EXCEPTION = "s3_exception";

  public static final String UNAUTHORIZED_ACCESS = "unauthorized_access";

  protected static final Map<String, String> errorCodesMap = new HashMap<>();

  static {
    errorCodesMap.put(INVALID_SCHEMA, "Schema is not valid, Please go through the fields again");
    errorCodesMap.put(INTERNAL_SERVER_ERROR, "An error has occurred in the application");

    errorCodesMap.put(
        CONSTRAINT_VIOLATION_EXCEPTION,
        "constraint violation exception, please change the request");
    errorCodesMap.put(
        USER_ALREADY_EXISTS,
        "User already exists with the same email, please use a different email");
    errorCodesMap.put(S3_EXCEPTION, "An exception occurred in S3");
    errorCodesMap.put(NOT_FOUND, "Not found");
    errorCodesMap.put(UNAUTHORIZED_ACCESS, "User is not authorized to access the application");
  }
}
