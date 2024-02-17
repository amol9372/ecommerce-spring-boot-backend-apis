package org.ecomm.ecommproduct.rest.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.exception.ErrorResponse;
import org.ecomm.ecommproduct.exception.ResourceNotFoundException;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.persistance.repository.InventoryRedisRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.rest.services.InventoryService;
import org.ecomm.ecommproduct.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

@RestController
@RequestMapping("inventory")
@Slf4j
public class InventoryController {

  @Autowired InventoryService inventoryService;

  @GetMapping("check")
  public ResponseEntity<?> checkInventoryInRedis(@RequestParam("id") int variantId) {

    return inventoryService.checkInventory(variantId);
  }

  @PostMapping("update-redis")
  public void updateInventoryInRedis(@RequestBody Inventory inventory) {
    inventoryService.updateInventoryInRedis(inventory);
  }

  @PostMapping("update")
  public void updateInventoryInDb(@RequestBody Inventory inventory) {
    inventoryService.updateInventoryInDb(inventory);
  }
}
