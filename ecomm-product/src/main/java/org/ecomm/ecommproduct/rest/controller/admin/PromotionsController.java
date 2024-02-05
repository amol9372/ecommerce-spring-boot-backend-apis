package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.model.Promotion;
import org.ecomm.ecommproduct.rest.services.admin.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/promotion")
public class PromotionsController {

  // flat discount on all products
  // discount on specific categories
  // *discount for time periods
  // discount on specific brands

  /*
   - Apply promotion
   - Remove promotion
  */

  @Autowired PromotionService promotionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPromotion(@RequestBody Promotion promotion) {
    promotionService.createPromotion(promotion);
  }

  @DeleteMapping("{id}")
  public void deletePromotion(@PathVariable int id) {
    promotionService.deletePromotion(id);
  }
}
