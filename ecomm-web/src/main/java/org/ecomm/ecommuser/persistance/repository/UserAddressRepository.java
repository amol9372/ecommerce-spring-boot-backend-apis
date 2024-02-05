package org.ecomm.ecommuser.persistance.repository;

import org.ecomm.ecommuser.persistance.entity.user.EAddress;
import org.ecomm.ecommuser.persistance.entity.user.EUserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<EUserAddress, Integer> {

    Optional<List<EUserAddress>> findByUserId(Integer userId);

    Optional<EUserAddress> findByUserIdAndDefaultAddress(Integer userId, Boolean defaultAddress);
}
