package org.ecomm.ecommgateway.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ecomm.ecommgateway.rest.controller.PostsWebSocketHandler;
import org.ecomm.ecommgateway.rest.model.AuthResponse;
import org.ecomm.ecommgateway.rest.model.LoginUserRequest;
import org.ecomm.ecommgateway.rest.model.RegisterUserRequest;
import org.ecomm.ecommgateway.rest.model.WSEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@Order(1)
public class UserEventsAspect {

  @Autowired PostsWebSocketHandler postsWebSocketHandler;

  @Around(
      "execution(* org.ecomm.ecommgateway.rest.service.impl.AuthServiceImpl.authenticate(..)) & args(request)")
  public Mono<AuthResponse> executeAuthenticationPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Executing Delete CartItem Request Validator");

    LoginUserRequest request =
        (LoginUserRequest) Arrays.stream(joinPoint.getArgs()).findFirst().orElseThrow();

    Mono<AuthResponse> authResponseMono = (Mono<AuthResponse>) joinPoint.proceed();

    // Successful login
    authResponseMono.subscribe(authResponse -> postsWebSocketHandler.broadcast(
            WSEvent.builder()
                    .message(String.format("%s trying to authenticate", request.getEmail()))
                    .eventType("Authentication")
                    .eventSubType(Objects.nonNull(authResponse.getToken()) ? "success": "failed")
                    .build()));

    return authResponseMono;
  }

  @Around(
          "execution(* org.ecomm.ecommgateway.rest.service.impl.AuthServiceImpl.register(..)) & args(request)")
  public AuthResponse executeRegisterPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Executing Delete CartItem Request Validator");

    RegisterUserRequest request =
            (RegisterUserRequest) Arrays.stream(joinPoint.getArgs()).findFirst().orElseThrow();

    AuthResponse authResponseMono = (AuthResponse) joinPoint.proceed();

    // Successful registration
    postsWebSocketHandler.broadcast(
            WSEvent.builder()
                    .message(String.format("%s trying to register", request.getEmail()))
                    .eventType("Registration")
                    .eventSubType("success")
                    .build());

    return authResponseMono;
  }
}
