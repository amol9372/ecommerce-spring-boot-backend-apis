package org.ecomm.ecommgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class CorsConfig {

//  @Bean
//  public CorsWebFilter corsFilter() {
//    CorsConfiguration corsConfiguration = new CorsConfiguration();
//    corsConfiguration.setAllowCredentials(true);
//    corsConfiguration.addAllowedOrigin("http://localhost:3000");
//    corsConfiguration.addAllowedOrigin("https://ecomm-ui-kappa.vercel.app");
//    corsConfiguration.addAllowedOrigin("https://ecomm-ui-git-master-conservativeasslovers-projects.vercel.app");
//    corsConfiguration.setAllowedMethods(
//        Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
//    corsConfiguration.addAllowedHeader("origin");
//     corsConfiguration.addAllowedHeader("x-requested-with");
//    corsConfiguration.addAllowedHeader("x-token-type");
//    corsConfiguration.addAllowedHeader("content-type");
//    corsConfiguration.addAllowedHeader("accept");
//    corsConfiguration.addAllowedHeader("authorization");
//    corsConfiguration.addAllowedHeader("cookie");
//    corsConfiguration.addAllowedHeader("Authorization");
//    corsConfiguration.addAllowedHeader("Access-Control-Allow-Origin");
//    corsConfiguration.addAllowedHeader("Access-Control-Allow-Headers");
//    corsConfiguration.addAllowedHeader("Access-Control-Allow-Credentials");
//    corsConfiguration.addAllowedHeader("*");
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", corsConfiguration);
//    return new CorsWebFilter(source);
//  }
}
