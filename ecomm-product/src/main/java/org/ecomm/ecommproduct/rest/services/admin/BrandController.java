package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.request.admin.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class BrandController {

  @Autowired BrandServiceImpl brandServiceImpl;

  @PostMapping("admin/brand")
  public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
    brandServiceImpl.createBrand(brand);
    return ResponseEntity.created(URI.create("/admin/brand")).build();
  }

  @GetMapping("brand")
  public ResponseEntity<?> getAllBrands() {
    return ResponseEntity.ok(brandServiceImpl.getAllBrands());
  }
}
