package org.ecomm.ecommproduct.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.request.admin.Brand;
import org.ecomm.ecommproduct.rest.request.admin.ProductVariant;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails extends BaseModel {

  String name;
  String description;
  Brand brand;
  Category category;
  String categoryTree;
  Object features;
  List<ProductImage> images;
  // inventory
  List<ProductVariant> variants;
}
