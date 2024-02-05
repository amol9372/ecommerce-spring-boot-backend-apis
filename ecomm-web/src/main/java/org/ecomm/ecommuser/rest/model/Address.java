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
public class Address extends BaseModel {

  String name; // name of the person
  String streetAddress;
  String city;
  String state;
  String postalCode;
  String country;
  boolean defaultAddress;
}
