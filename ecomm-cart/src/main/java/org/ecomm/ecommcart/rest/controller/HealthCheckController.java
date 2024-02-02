package org.ecomm.ecommcart.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-check")
public class HealthCheckController {

  @GetMapping
  public String healthCheck() {

    return "Ecomm-cart is running";
  }
}
