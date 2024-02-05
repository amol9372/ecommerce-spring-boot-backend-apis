package org.ecomm.ecommorder.rest.feign;

import org.ecomm.ecommorder.rest.feign.model.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cart-service", url = "http://${host.url}:8082/api/cart")
public interface CartServiceClient {

  @GetMapping
  ResponseEntity<CartResponse> getActiveCart(
      @RequestHeader("email") String email, @RequestHeader("service") String service);

  @PostMapping("checkout")
  void checkout(@RequestHeader("email") String email, @RequestHeader("service") String service);
}
