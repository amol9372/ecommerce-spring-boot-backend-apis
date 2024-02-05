package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<EPromotion, Integer> {

}
