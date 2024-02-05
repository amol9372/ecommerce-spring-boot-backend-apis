package org.ecomm.ecommproduct.rest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ecomm.ecommproduct.rest.model.ProductDetails;
import org.ecomm.ecommproduct.rest.model.ProductVariantResponse;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.ecomm.ecommproduct.rest.services.ProductESService;
import org.ecomm.ecommproduct.rest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired ProductESService productESService;

  @Autowired ProductService productService;

  @PostMapping("search")
  public PagedResponse<ProductVariantResponse> searchProducts(@RequestBody SearchRequest request) {

    return productESService.searchProducts(request);
  }

  @GetMapping("{id}")
  public ProductDetails getProductDetails(@PathVariable int id) {
    return productService.getProductDetails(id);
  }

  @GetMapping
  public ResponseEntity<List<ObjectNode>> getCartProductDetails(
      @RequestParam("ids") String variantIds) {
    return ResponseEntity.ok(productESService.getCartProductDetails(variantIds));
  }
}
