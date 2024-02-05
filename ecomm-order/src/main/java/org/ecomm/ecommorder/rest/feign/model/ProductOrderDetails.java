package org.ecomm.ecommorder.rest.feign.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetails {

  String name;
  int variantId;
  int productId;
  double price;
  String brand;
  String imageUrl;
  int quantity;
}
