package org.ecomm.ecommcart.rest.feign;

import java.util.List;

import org.ecomm.ecommcart.rest.feign.model.ProductInventory;
import org.ecomm.ecommcart.rest.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "http://${host.url}:8081/api")
public interface ProductServiceClient {

  @GetMapping("product")
  ResponseEntity<List<CartItem>> getProductCartDetail(
          @RequestParam("ids") String variantIds, @RequestHeader("service") String service);

  @PostMapping("inventory/update-redis")
  ResponseEntity<?> updateInventory(
          @RequestBody Object inventory, @RequestHeader("service") String service);

  @GetMapping("inventory/check")
  ResponseEntity<ProductInventory> checkInventory(
          @RequestParam("id") int variantId, @RequestHeader("service") String service);
}
