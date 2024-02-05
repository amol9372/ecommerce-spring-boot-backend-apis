package org.ecomm.ecommuser.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {

  private ErrorCodes() {}

  // invalid schema - multiple codes
  public static final String INVALID_SCHEMA = "invalid_schema";

  public static final String INTERNAL_SERVER_ERROR = "internal_server_error";

  public static final String PARTNER_EXISTS_EXCEPTION = "partner_already_exists";

  public static final String INVALID_PARTNER = "invalid_partner";

  public static final String INVALID_REQUEST_PARAMS = "invalid_request_parameters";

  public static final String MISSING_EMAIL_HEADER = "missing_email_header";

  public static final String CONSTRAINT_VIOLATION_EXCEPTION = "constraint_violation_exception";

  public static final String USER_ALREADY_EXISTS = "user_already_exists";

  public static final String USER_DOES_NOT_EXIST = "user_not_exists";

  public static final String S3_EXCEPTION = "s3_exception";

  public static final String MISSING_PARTNER_HEADER = "missing_partner_header";

  protected static final Map<String, String> errorCodesMap = new HashMap<>();

  static {
    errorCodesMap.put(INVALID_SCHEMA, "Schema is not valid, Please go through the fields again");
    errorCodesMap.put(INTERNAL_SERVER_ERROR, "An error has occurred in the application");
    errorCodesMap.put(
        PARTNER_EXISTS_EXCEPTION, "Partner already exists, Please choose a different name");
    errorCodesMap.put(
            INVALID_PARTNER, "Partner does not exist");
    errorCodesMap.put(
        CONSTRAINT_VIOLATION_EXCEPTION,
        "constraint violation exception, please change the request");
    errorCodesMap.put(
        USER_ALREADY_EXISTS,
        "User already exists with the same email, please use a different email");
    errorCodesMap.put(S3_EXCEPTION, "An exception occurred in S3");
    errorCodesMap.put(MISSING_PARTNER_HEADER, "Partner header is missing");
    errorCodesMap.put(MISSING_EMAIL_HEADER, "Email header is missing");
    errorCodesMap.put(USER_DOES_NOT_EXIST, "User does not exists");
  }
}
