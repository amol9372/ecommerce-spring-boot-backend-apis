package org.ecomm.ecommorder.config;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommorder.exception.ErrorCodes;
import org.ecomm.ecommorder.exception.ErrorResponse;
import org.ecomm.ecommorder.exception.UnauthorizedUserException;
import org.ecomm.ecommorder.rest.feign.UserServiceClient;
import org.ecomm.ecommorder.rest.feign.model.UserResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(2)
@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter {

  @Autowired
  UserServiceClient userServiceClient;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String userEmail = request.getHeader("x-auth0-user-email");
    if (isEmpty(userEmail)) {
      log.error("User email header missing [x-auth0-user-email]");

      var errorResponse =
          ErrorResponse.builder()
              .code(ErrorCodes.MISSING_EMAIL_HEADER)
              .message(Constants.UNAUTHORIZED_USER)
              .build();
      throw new UnauthorizedUserException(HttpStatus.UNAUTHORIZED, errorResponse);
    }

    ResponseEntity<UserResponse> basicUserInfo =
        userServiceClient.getUserByEmail(userEmail, "ecomm-order");

    if (basicUserInfo.getStatusCode().is2xxSuccessful()) {
      ObjectMapper objectMapper = new ObjectMapper();

      //  Convert Java object to JSON string
      String userString = objectMapper.writeValueAsString(basicUserInfo.getBody());
      MDC.put("user", userString);
    } else {
      var errorResponse =
          ErrorResponse.builder()
              .code(ErrorCodes.USER_DOES_NOT_EXIST)
              .message(Constants.UNAUTHORIZED_USER)
              .build();
      throw new UnauthorizedUserException(HttpStatus.UNAUTHORIZED, errorResponse);
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();

    return path.contains("/stripe");
  }
}
