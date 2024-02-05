package org.ecomm.ecommorder.rest.feign;

import org.ecomm.ecommorder.rest.feign.model.AddressResponse;
import org.ecomm.ecommorder.rest.feign.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "user-service", url = "http://localhost:8080/api")
@FeignClient(name = "user-service", url = "http://${host.url}:8080/api")
public interface UserServiceClient {

  @GetMapping(value = "user")
  ResponseEntity<UserResponse> getUserByEmail(
      @RequestHeader("email") String email, @RequestHeader("service") String service);

  @GetMapping(value = "address/default")
  ResponseEntity<AddressResponse> getActiveAddressByEmail(
      @RequestHeader("email") String email, @RequestHeader("service") String service);
}
