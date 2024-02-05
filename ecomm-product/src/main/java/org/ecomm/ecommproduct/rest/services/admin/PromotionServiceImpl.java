package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.persistance.entity.EPromotion;
import org.ecomm.ecommproduct.persistance.entity.PromoAppliedType;
import org.ecomm.ecommproduct.persistance.entity.PromotionStatus;
import org.ecomm.ecommproduct.persistance.repository.PromotionRepository;
import org.ecomm.ecommproduct.rest.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

  @Autowired PromotionRepository promotionRepository;

  @Override
  public void createPromotion(Promotion promotion) {
    EPromotion ePromotion =
        EPromotion.builder()
            .name(promotion.getName())
            .description(promotion.getDescription())
            .expiresIn(promotion.getExpiresIn())
            .appliedToType(PromoAppliedType.CATEGORY)
            .appliedTo(promotion.getAppliedTo())
            .status(PromotionStatus.ACTIVE)
            .build();

    promotionRepository.save(ePromotion);
  }

  @Override
  public void deletePromotion(int id) {
    promotionRepository.deleteById(id);
  }
}
