package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<EProduct, Integer> {



}
