package org.ecomm.ecommuser.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    String firstName;
    String lastName;
    String email;
    String mobile;
    int age;
    String gender;
    UserPreferences preferences;
    String role;

}
