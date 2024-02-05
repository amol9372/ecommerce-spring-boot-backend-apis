package org.ecomm.ecommgateway.persistence.repository;

import java.util.Optional;

import org.ecomm.ecommgateway.persistence.entity.UserCredentials;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    Optional<UserCredentials> findByEmail(String email);

}
