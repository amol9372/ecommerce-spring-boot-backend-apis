package org.ecomm.ecommgateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
@Order(0)
public class SecurityConfig {

  @Autowired CustomWebFilter webFilter;

  private final ReactiveAuthenticationManager authenticationManager;

  public SecurityConfig(
      ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.authenticationManager = new CustomReactiveAuthManager(userDetailsService, passwordEncoder);
  }

  @Bean
  public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
    return http.csrf()
        .disable()
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .authenticationManager(authenticationManager)
        .httpBasic()
        .disable()
        .authorizeExchange(
            authorizeExchangeSpec ->
                authorizeExchangeSpec
                    .pathMatchers("/ws/events")
                    //.hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .permitAll()
                    .pathMatchers(
                        "/auth/**", "/stripe/**", "/swagger-ui/**", "/api-docs/**", "/webjars/**")
                    .permitAll()
                    .pathMatchers("/admin/**")
                    .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                    .anyExchange()
                    .authenticated()
                    .and()
                    .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                    .addFilterBefore(webFilter, SecurityWebFiltersOrder.HTTP_BASIC))
        .build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(
        Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedOrigin("http://localhost:3000");
    corsConfiguration.addAllowedOrigin("https://ecomm-ui-kappa.vercel.app");
    corsConfiguration.addAllowedOrigin(
        "https://ecomm-ui-git-master-conservativeasslovers-projects.vercel.app");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
