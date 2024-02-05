package org.ecomm.ecommorder.rest.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {

    int items;
    double itemsSubTotal;
    double shipping;
    double tax;
}
