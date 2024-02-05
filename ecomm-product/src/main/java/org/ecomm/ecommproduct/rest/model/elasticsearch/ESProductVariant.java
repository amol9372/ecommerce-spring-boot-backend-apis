package org.ecomm.ecommproduct.rest.model.elasticsearch;

import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "productv1", writeTypeHint = WriteTypeHint.FALSE, storeIdInSource = true)
public class ESProductVariant {

  @Id
  Integer id; // variant Id

  @Field(type = FieldType.Integer, name = "product_id")
  Integer productId;

  @Field(type = FieldType.Text, name = "name")
  String name;

  @Field(type = FieldType.Text, name = "description")
  String description;

  @Field(type = FieldType.Keyword, name = "brand")
  String brand;

  @Field(type = FieldType.Keyword, name = "brand_category")
  String brandCategory;

  @Field(type = FieldType.Double_Range, name = "price")
  double price;

  @Field(type = FieldType.Object, name = "category")
  ESCategory category;

  @Field(type = FieldType.Keyword, name = "category_tree")
  String categoryTree;

  @Field(type = FieldType.Object, name = "features")
  Object features;

  @Field(type = FieldType.Object, name = "inventory")
  ESInventory inventory;
}
