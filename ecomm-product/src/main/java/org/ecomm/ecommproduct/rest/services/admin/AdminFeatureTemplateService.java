package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.request.admin.AddFeatureTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminFeatureTemplateService {

     void addFeatureTemplate(AddFeatureTemplate template);

     List<?> getFeatureTemplate(int categoryId);
}
