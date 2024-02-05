package org.ecomm.ecommorder.rest.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    
    @JsonProperty("cartItems")
    List<ProductOrderDetails> productCartDetails;
    
}
