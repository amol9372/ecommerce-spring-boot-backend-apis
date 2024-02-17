package org.ecomm.ecommuser.rest.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommuser.exception.ErrorCodes;
import org.ecomm.ecommuser.exception.ErrorResponse;
import org.ecomm.ecommuser.exception.UserNotFound;
import org.ecomm.ecommuser.persistance.entity.user.EUser;
import org.ecomm.ecommuser.persistance.entity.user.EUserRole;
import org.ecomm.ecommuser.persistance.entity.user.UserRole;
import org.ecomm.ecommuser.persistance.repository.UserRepository;
import org.ecomm.ecommuser.persistance.repository.UserRoleRepository;
import org.ecomm.ecommuser.rest.builder.UserBuilder;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.request.CreateUserRequest;
import org.ecomm.ecommuser.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  @Autowired UserRoleRepository userRoleRepository;

  @Transactional
  @Override
  public User createAppUser(CreateUserRequest request) {

    log.info("Incoming request ::: {}", request.toString());

    EUser eUser = UserBuilder.with(request);
    EUser savedUser = userRepository.save(eUser);
    userRoleRepository.save(EUserRole.builder().role(UserRole.USER).user(savedUser).build());

    return User.builder().id(savedUser.getId()).build();
  }

  @Override
  public User getBasicUserInfo() {
    return Utility.getLoggedInUser();
  }

  @Override
  public User getBasicUserInfo(String email) {

    Optional<EUser> userOptional = userRepository.findByEmail(email);

    var euser =
        userOptional.orElseThrow(
            () ->
                new UserNotFound(
                    HttpStatus.UNAUTHORIZED,
                    ErrorResponse.builder()
                        .code(ErrorCodes.USER_DOES_NOT_EXIST)
                        .message("No user exists with the email")
                        .build()));

    return UserBuilder.with(euser);
  }

  @Override
  public List<User> getAllUsers() {
    var eUsers = userRepository.findAll();
    
    return eUsers.stream().map(UserBuilder::with).collect(Collectors.toList());
  }
}
