package org.ecomm.ecommuser.persistance.repository;

import org.ecomm.ecommuser.persistance.entity.user.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<EUserRole, Integer> {

}
