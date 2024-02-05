package org.ecomm.ecommuser.rest.services;

import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.request.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User createAppUser(CreateUserRequest request);

    User getBasicUserInfo();

    User getBasicUserInfo(String email);

}
