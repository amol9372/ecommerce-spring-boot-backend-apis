package org.ecomm.ecommproduct.rest.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.model.BaseModel;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProductVariant extends BaseModel {
  Object features;
  double price;
  String sku;
  int quantity;
}
