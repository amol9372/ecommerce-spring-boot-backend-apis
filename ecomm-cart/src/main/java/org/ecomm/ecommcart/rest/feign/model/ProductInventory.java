package org.ecomm.ecommcart.rest.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {
    @JsonProperty("quantityAvailable")
    int quantity;

    int variantId;
}
