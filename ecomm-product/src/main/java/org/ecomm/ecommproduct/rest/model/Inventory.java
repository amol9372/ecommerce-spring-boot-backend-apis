package org.ecomm.ecommproduct.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Inventory extends BaseModel {

  String sku;
  int quantityReserved;
  int quantityAvailable;
  int quantitySold;
  int variantId;
}
