package org.ecomm.ecommuser.rest.builder;

import org.ecomm.ecommuser.persistance.entity.user.EUser;
import org.ecomm.ecommuser.persistance.entity.user.UserStatus;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.request.CreateUserRequest;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserBuilder {

  public static EUser with(CreateUserRequest request) {
    return EUser.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .mobile(request.getMobile())
        .provider(request.getProvider())
        .status(UserStatus.ACTIVE_NOT_VERIFIED)
        .createdAt(LocalDateTime.now())
        // .role(EUserRole.builder().name(UserRole.USER).build())
        .build();
  }

  public static User with(EUser eUser) {
    return User.builder()
        .id(eUser.getId())
        .email(eUser.getEmail())
        .firstName(eUser.getFirstName())
        .lastName(eUser.getLastName())
        .mobile(eUser.getMobile())
        .gender(eUser.getMobile())
        .role(Objects.nonNull(eUser.getRole()) ? eUser.getRole().getRole().name() : null)
        .build();
  }
}
