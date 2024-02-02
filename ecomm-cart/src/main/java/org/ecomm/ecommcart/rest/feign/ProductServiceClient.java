package org.ecomm.ecommcart.rest.feign;

import java.util.List;
import org.ecomm.ecommcart.rest.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "http://${host.url}:8081/api/product")
public interface ProductServiceClient {

  @GetMapping
  ResponseEntity<List<CartItem>> getProductCartDetail(
          @RequestParam("ids") String variantIds, @RequestHeader("service") String service);
}
