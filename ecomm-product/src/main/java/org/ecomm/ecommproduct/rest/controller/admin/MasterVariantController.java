package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.services.admin.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/variant")
public class MasterVariantController {

  @Autowired VariantService variantService;

  @GetMapping("{categoryId}")
  public ResponseEntity<?> getVariantFeatures(@PathVariable int categoryId) {
    return ResponseEntity.ok(variantService.getFeaturesVariant(categoryId));
  }
}
