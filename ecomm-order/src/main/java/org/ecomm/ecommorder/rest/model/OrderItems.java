package org.ecomm.ecommorder.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommorder.rest.feign.model.ProductOrderDetails;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    ProductOrderDetails productDetails;
    int quantity;

}
