package org.ecomm.ecommproduct.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProductImage extends BaseModel {
  String url;
  int order;
  String type;
}
