package org.ecomm.ecommgateway.rest.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    String token;
    UserInfo userInfo;

}
