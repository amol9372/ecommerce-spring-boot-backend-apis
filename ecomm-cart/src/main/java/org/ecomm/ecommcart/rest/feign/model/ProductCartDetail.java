package org.ecomm.ecommcart.rest.feign.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDetail {

    int productId;
    int variantId;
    String imageUrl;
    String name;
    double price;
    String brand;

}
