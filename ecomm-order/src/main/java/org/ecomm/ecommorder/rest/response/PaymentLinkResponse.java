package org.ecomm.ecommorder.rest.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLinkResponse {
    String link;
}
