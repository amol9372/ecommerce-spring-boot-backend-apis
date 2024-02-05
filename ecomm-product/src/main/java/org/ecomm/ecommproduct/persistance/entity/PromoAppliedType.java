package org.ecomm.ecommproduct.persistance.entity;

import org.springframework.data.util.QTypeContributor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PromoAppliedType {
  CATEGORY("category"),
  PRODUCT("product"),
  BRAND("brand"),
  ALL("all");

  PromoAppliedType(String category) {}

  static final Map<String, PromoAppliedType> typeValueMap =
      Arrays.stream(PromoAppliedType.values()).collect(Collectors.toMap(Enum::name, type -> type));

  public static PromoAppliedType getPromoValue(String value) {
    return typeValueMap.get(value);
  }
}
