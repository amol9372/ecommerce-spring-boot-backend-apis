package org.ecomm.ecommproduct.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.request.admin.Brand;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse extends BaseModel {

  Integer productId;
  String name;
  String description;
  Brand brand;
  double price;
  Category category;
  String categoryTree;
  Object features;
  List<ProductImage> images;
  // inventory
  Inventory inventory;
}
