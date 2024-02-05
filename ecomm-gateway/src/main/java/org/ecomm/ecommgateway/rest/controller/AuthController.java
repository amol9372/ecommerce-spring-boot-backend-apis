package org.ecomm.ecommgateway.rest.controller;

import java.net.URI;
import org.ecomm.ecommgateway.rest.model.LoginUserRequest;
import org.ecomm.ecommgateway.rest.model.RegisterUserRequest;
import org.ecomm.ecommgateway.rest.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody LoginUserRequest authRequest) {

    return ResponseEntity.ok(authService.authenticate(authRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterUserRequest request){
    return ResponseEntity.created(URI.create("/auth/register")).body(authService.register(request));
  }
}
