package org.ecomm.ecommcart.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class AddToCartRequest {

  @JsonProperty("variant_id")
  Integer variantId;

  @JsonProperty("product_id")
  Integer productId;

  int quantity;
}
