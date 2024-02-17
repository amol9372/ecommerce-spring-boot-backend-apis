package org.ecomm.ecommproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class EcommProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(EcommProductApplication.class, args);
  }

  @Bean
  public ConcurrentLinkedDeque<?> redisQueue(){
    return new ConcurrentLinkedDeque<>();
  }

  @Bean
  public ExecutorService executorService(){
    return Executors.newFixedThreadPool(2);
  }
}

