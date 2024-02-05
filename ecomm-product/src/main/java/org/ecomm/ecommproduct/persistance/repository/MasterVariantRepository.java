package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EMasterVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterVariantRepository extends JpaRepository<EMasterVariant, Integer> {
    EMasterVariant findByCategoryId(Integer category);
}
