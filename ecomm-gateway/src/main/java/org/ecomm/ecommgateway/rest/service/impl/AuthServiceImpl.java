package org.ecomm.ecommgateway.rest.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommgateway.config.JwsUtil;
import org.ecomm.ecommgateway.persistence.entity.EUserRole;
import org.ecomm.ecommgateway.persistence.entity.UserCredentials;
import org.ecomm.ecommgateway.persistence.entity.UserRole;
import org.ecomm.ecommgateway.persistence.entity.UserStatus;
import org.ecomm.ecommgateway.persistence.repository.UserCredentialsRepository;
import org.ecomm.ecommgateway.rest.model.AuthResponse;
import org.ecomm.ecommgateway.rest.model.LoginUserRequest;
import org.ecomm.ecommgateway.rest.model.RegisterUserRequest;
import org.ecomm.ecommgateway.rest.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  @Autowired JwsUtil jwsUtil;

  @Autowired ReactiveAuthenticationManager authenticationManager;

  @Autowired UserCredentialsRepository userCredentialsRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Override
  public Mono<AuthResponse> authenticate(LoginUserRequest request) {

    Mono<Authentication> authenticationResult =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    return authenticationResult
        .publishOn(Schedulers.boundedElastic())
        .map(
            authentication -> {
              var user = userCredentialsRepository.findByEmail(request.getEmail()).orElseThrow();
              return AuthResponse.builder()
                  .token(jwsUtil.generateToken(user))
                  .userInfo(
                      UserInfo.builder()
                          .sub(user.getSubject())
                          .email(user.getEmail())
                          .nickName(user.getFirstName())
                          .name(user.getEmail())
                          .build())
                  .build();
            });
  }

  @Transactional
  @Override
  public AuthResponse register(RegisterUserRequest request) {

    userCredentialsRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            userCredentials -> {
              throw new RuntimeException("User already exists");
            });

    var user =
        UserCredentials.builder()
            .email(request.getEmail())
            .subject("app|".concat(generateRandomHexString(12)))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .password(passwordEncoder.encode(request.getPassword()))
            .status(UserStatus.ACTIVE_NOT_VERIFIED)
            .build();

    var role = EUserRole.builder().role(UserRole.USER).user(user).build();
    user.setRole(role);
    UserCredentials userCredentials = userCredentialsRepository.save(user);

    return AuthResponse.builder().token(jwsUtil.generateToken(userCredentials)).build();
  }

  private String generateRandomHexString(int numBytes) {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[numBytes];
    random.nextBytes(bytes);
    return new BigInteger(1, bytes).toString(16);
  }
}
