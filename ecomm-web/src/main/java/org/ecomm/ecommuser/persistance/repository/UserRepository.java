package org.ecomm.ecommuser.persistance.repository;

import org.ecomm.ecommuser.persistance.entity.user.EUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<EUser, Integer> {

    Optional<EUser> findByEmail(String email);

}
