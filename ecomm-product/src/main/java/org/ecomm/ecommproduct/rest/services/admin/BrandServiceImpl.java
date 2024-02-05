package org.ecomm.ecommproduct.rest.services.admin;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.EBrand;
import org.ecomm.ecommproduct.persistance.repository.BrandRepository;
import org.ecomm.ecommproduct.rest.request.admin.Brand;
import org.ecomm.ecommproduct.rest.response.BrandResponse;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BrandServiceImpl {

  @Autowired BrandRepository brandRepository;

  public void createBrand(Brand brand) {
    brandRepository.save(
        EBrand.builder().brandCategory(brand.getCategory()).name(brand.getName()).build());
  }

  public List<BrandResponse> getAllBrands() {

    Map<String, List<String>> brandCategoryMap =
        Utility.stream(brandRepository.findAll())
            .collect(
                Collectors.groupingBy(
                    EBrand::getName,
                    Collectors.mapping(EBrand::getBrandCategory, Collectors.toList())));

    return brandCategoryMap.entrySet().stream()
        .map(s -> BrandResponse.builder().categories(s.getValue()).name(s.getKey()).build())
        .collect(Collectors.toList());
  }
}
