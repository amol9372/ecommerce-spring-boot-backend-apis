package org.ecomm.ecommuser.config;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommuser.exception.ErrorCodes;
import org.ecomm.ecommuser.exception.ErrorResponse;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.services.UserService;

import org.ecomm.ecommuser.exception.UnauthorizedUserException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(2)
@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter {

  private final List<String> permittedUrls = List.of("/user");

  @Autowired UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (Objects.nonNull(request.getHeader("service"))) {

      String email = request.getHeader("email");
      putUserInMDC(email);
      filterChain.doFilter(request, response);
    } else {
      String userEmail = request.getHeader("x-auth0-user-email");
      if (isEmpty(userEmail)) {
        log.error("User email header missing");

        var errorResponse =
            ErrorResponse.builder()
                .code(ErrorCodes.MISSING_EMAIL_HEADER)
                .message(Constants.UNAUTHORIZED_USER)
                .build();
        throw new UnauthorizedUserException(HttpStatus.UNAUTHORIZED, errorResponse);
      }

      putUserInMDC(userEmail);
      filterChain.doFilter(request, response);
    }
  }

  private void putUserInMDC(String userEmail) throws JsonProcessingException {
    User basicUserInfo = userService.getBasicUserInfo(userEmail);
    ObjectMapper objectMapper = new ObjectMapper();

    // Convert Java object to JSON string
    String userString = objectMapper.writeValueAsString(basicUserInfo);
    MDC.put("user", userString);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    if(request.getRequestURI().contains("api-docs") || request.getRequestURI().contains("swagger")){
      return true;
    }

    return false;
  }
}
