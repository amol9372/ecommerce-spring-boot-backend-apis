package org.ecomm.ecommuser.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences extends BaseModel {

  String language;
  boolean twoFactorEnabled;
  String twoFactorMode;
  String currency;
}
