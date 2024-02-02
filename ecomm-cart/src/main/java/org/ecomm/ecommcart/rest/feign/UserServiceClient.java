package org.ecomm.ecommcart.rest.feign;

import org.ecomm.ecommcart.rest.feign.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://${host.url}:8080/api/user")
public interface UserServiceClient {

  @GetMapping
  ResponseEntity<UserResponse> getUserByEmail(
      @RequestHeader("email") String email, @RequestHeader("service") String service);
}
