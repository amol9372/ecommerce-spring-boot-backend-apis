package org.ecomm.ecommcart.rest.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseModel {

  Integer userId;
  String status;
  double totalPrice;
  List<CartItem> cartItems;
}
