package org.ecomm.ecommgateway.rest.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserRequest {
    String email;
    String password;
}
