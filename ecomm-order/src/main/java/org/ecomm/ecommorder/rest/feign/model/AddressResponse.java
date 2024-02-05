package org.ecomm.ecommorder.rest.feign.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommorder.rest.model.BaseModel;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

  int id;
  String name;
  String streetAddress;
  String city;
  String state;
  String postalCode;
  String country;
  boolean defaultAddress;
}
