package org.ecomm.ecommgateway.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    String sub;
    @JsonProperty("nickname")
    String nickName;
    String name;
    String picture;
    String email;
    @JsonProperty("email_verified")
    boolean emailVerified;
    String role;
}
