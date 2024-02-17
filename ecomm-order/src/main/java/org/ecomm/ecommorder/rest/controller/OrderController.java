package org.ecomm.ecommorder.rest.controller;

import org.ecomm.ecommorder.rest.model.OrderSummary;
import org.ecomm.ecommorder.rest.response.PaymentLinkResponse;
import org.ecomm.ecommorder.rest.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("order")
public class OrderController {

  @Autowired OrderService orderService;

  @GetMapping("summary")
  public ResponseEntity<OrderSummary> orderSummary() {
    return ResponseEntity.ok(orderService.generateOrderSummary());
  }

  @PostMapping
  public ResponseEntity<?> order() {
    var paymentLink = orderService.createOrder();

    return ResponseEntity.created(URI.create("/api/order")).body(PaymentLinkResponse.builder().link(paymentLink.getUrl()).build());
  }
}
