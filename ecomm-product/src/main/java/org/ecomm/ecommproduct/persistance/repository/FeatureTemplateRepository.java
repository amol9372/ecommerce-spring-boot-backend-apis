package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.EFeatureTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureTemplateRepository extends JpaRepository<EFeatureTemplate, Integer> {


    EFeatureTemplate findByCategoryId(Integer categoryId);
}
