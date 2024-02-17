package org.ecomm.ecommuser.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.request.CreateUserRequest;
import org.ecomm.ecommuser.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/user")
@Slf4j
public class AdminUserController {

  @Autowired
  UserService userService;

  @GetMapping("all")
  public ResponseEntity<List<User>> getUserByEmail(){
     return ResponseEntity.ok(userService.getAllUsers());
  }
}
