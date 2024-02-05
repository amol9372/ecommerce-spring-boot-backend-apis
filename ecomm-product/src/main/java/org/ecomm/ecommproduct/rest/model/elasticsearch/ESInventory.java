package org.ecomm.ecommproduct.rest.model.elasticsearch;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ESInventory {

  Integer id;
  String sku;

  @Field(name = "quantity_reserved")
  int quantityReserved;

  @Field(name = "quantity_available")
  int quantityAvailable;

  @Field(name = "quantity_sold")
  int quantitySold;
}
