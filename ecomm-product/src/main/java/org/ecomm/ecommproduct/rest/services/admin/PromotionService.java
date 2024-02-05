package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public interface PromotionService {

    void createPromotion(Promotion promotion);

    void deletePromotion(int id);
}
