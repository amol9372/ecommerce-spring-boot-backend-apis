package org.ecomm.ecommgateway.persistence.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserRole {
  ADMIN("admin"),
  USER("user");

  UserRole(String category) {}

  static final Map<String, UserRole> typeValueMap =
      Arrays.stream(UserRole.values()).collect(Collectors.toMap(Enum::name, type -> type));

  public static UserRole getPromoValue(String value) {
    return typeValueMap.get(value);
  }
}
