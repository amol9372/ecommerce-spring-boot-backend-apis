package org.ecomm.ecommorder.rest.model;

import lombok.*;
import org.ecomm.ecommorder.rest.feign.model.AddressResponse;
import org.ecomm.ecommorder.rest.feign.model.ProductOrderDetails;
import org.ecomm.ecommorder.rest.feign.model.UserResponse;
import org.springframework.data.util.Pair;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StripeInput {

    Pair<UserResponse, AddressResponse> userAddressPair;
    List<ProductOrderDetails> orderDetails;

}
