package org.ecomm.ecommproduct.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Promotion extends BaseModel {

  String name;
  String description;
  @JsonProperty("applied_to")
  int appliedTo;
  @JsonProperty("applied_to_type")
  String appliedToType;
  @JsonProperty("expires_in")
  int expiresIn;
  int discount;
}
