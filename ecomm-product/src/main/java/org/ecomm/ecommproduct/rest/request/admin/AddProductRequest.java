package org.ecomm.ecommproduct.rest.request.admin;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.model.ProductImage;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AddProductRequest {

  Integer id;
  String name;
  String description;
  double price;
  Brand brand;
  Integer category;
  Object features;
  List<ProductVariant> variants;
  List<ProductImage> images;

}
