package org.ecomm.ecommcart.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseModel {

    Integer productId;
    Integer variantId;
    int quantity;
    String name;
    String imageUrl;
    double price;
    String brand;
    int inventoryAvailable;
}
