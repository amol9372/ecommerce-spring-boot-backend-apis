package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<EBrand, Integer> {

    EBrand findByName(String brand);

    EBrand findByNameAndBrandCategory(String name, String category);
}
