package org.ecomm.ecommgateway.rest.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {

    String firstName;
    String lastName;
    String email;
    String password;

}
