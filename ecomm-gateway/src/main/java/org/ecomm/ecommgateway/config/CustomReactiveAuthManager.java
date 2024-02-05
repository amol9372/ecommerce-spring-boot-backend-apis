package org.ecomm.ecommgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommgateway.persistence.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustomReactiveAuthManager implements ReactiveAuthenticationManager {

  private final ReactiveUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public CustomReactiveAuthManager(
      ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    return userDetailsService
        .findByUsername(username)
        .flatMap(
            userDetails -> {
              if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return Mono.just(
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()));
              } else {
                return Mono.error(new BadCredentialsException("Invalid Credentials"));
              }
            });
  }
}
