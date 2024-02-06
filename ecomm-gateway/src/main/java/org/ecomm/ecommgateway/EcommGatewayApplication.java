package org.ecomm.ecommgateway;

import org.ecomm.ecommgateway.persistence.repository.UserCredentialsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableWebFlux
public class EcommGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(EcommGatewayApplication.class, args);
  }

  @Bean
  public WebProperties.Resources resources() {
    return new WebProperties.Resources();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ReactiveUserDetailsService userDetailsService(UserCredentialsRepository repository) {

    return email ->
        Mono.just(
            repository
                .findByEmail(email)
                .map(
                    u ->
                        User.withUsername(u.getUsername())
                            .password(u.getPassword())
                            .authorities(u.getAuthorities())
                            //                        .accountExpired(!u.isAccountNonExpired())
                            //                        .credentialsExpired(!u.isActive())
                            //                        .disabled(!u.isActive())
                            .accountLocked(false)
                            .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist")));
  }
}
