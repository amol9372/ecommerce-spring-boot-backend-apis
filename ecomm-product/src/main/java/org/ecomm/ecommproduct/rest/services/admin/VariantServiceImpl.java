package org.ecomm.ecommproduct.rest.services.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecomm.ecommproduct.persistance.entity.EMasterVariant;
import org.ecomm.ecommproduct.persistance.repository.MasterVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariantServiceImpl implements VariantService {

  @Autowired MasterVariantRepository masterVariantRepository;

  @Autowired ObjectMapper mapper = new ObjectMapper();

  @Override
  public Object getFeaturesVariant(int category) {
    EMasterVariant masterVariant = masterVariantRepository.findByCategoryId(category);

    try {
      return mapper.treeToValue(masterVariant.getFeatureNames(), Object.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
