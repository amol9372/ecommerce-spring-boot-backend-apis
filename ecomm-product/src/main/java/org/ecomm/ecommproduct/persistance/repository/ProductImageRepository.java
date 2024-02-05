package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<EProductImage, Integer> {



}
