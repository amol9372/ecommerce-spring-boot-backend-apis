package org.ecomm.ecommuser.persistance.repository;

import org.ecomm.ecommuser.persistance.entity.user.EAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<EAddress, Integer> {

}
