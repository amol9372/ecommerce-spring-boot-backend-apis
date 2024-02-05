package org.ecomm.ecommuser.rest.controller;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.services.UserService;
import org.ecomm.ecommuser.rest.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email){
     return ResponseEntity.ok(userService.getBasicUserInfo(email));
  }


  @PostMapping
  public ResponseEntity<Object> createAppUser(
          @RequestBody CreateUserRequest request, HttpServletRequest httpServletRequest) {
    var user = userService.createAppUser(request);

    Iterator<String> iterator = httpServletRequest.getHeaderNames().asIterator();

    log.info("Query string ::: {}", httpServletRequest.getQueryString());

    while (iterator.hasNext()) {
      String name = iterator.next();
      log.info("header is {}", name);
      log.info("header value is {}", httpServletRequest.getHeader(name));
    }

    return ResponseEntity.created(URI.create("/user")).body(Map.of("id", user.getId()));
  }

  @GetMapping
  public ResponseEntity<Object> getBasicUserInfo() {
    var user = userService.getBasicUserInfo();
    return ResponseEntity.ok(user);
  }
}
