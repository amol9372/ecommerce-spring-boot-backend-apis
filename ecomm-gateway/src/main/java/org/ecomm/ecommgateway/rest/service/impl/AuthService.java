package org.ecomm.ecommgateway.rest.service.impl;

import org.ecomm.ecommgateway.rest.model.AuthResponse;
import org.ecomm.ecommgateway.rest.model.LoginUserRequest;
import org.ecomm.ecommgateway.rest.model.RegisterUserRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuthService {


    Mono<AuthResponse> authenticate(LoginUserRequest request);

    AuthResponse register(RegisterUserRequest request);
}
