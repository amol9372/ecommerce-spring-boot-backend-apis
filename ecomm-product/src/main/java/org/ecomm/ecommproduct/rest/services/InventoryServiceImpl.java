package org.ecomm.ecommproduct.rest.services;

import org.ecomm.ecommproduct.exception.ErrorResponse;
import org.ecomm.ecommproduct.exception.ResourceNotFoundException;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.persistance.repository.InventoryRedisRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
  @Autowired InventoryRedisRepository inventoryRedisRepository;

  @Autowired InventoryRepository inventoryRepository;

  @Autowired RedisUtils redisUtils;

  @Override
  public ResponseEntity<?> checkInventory(int variantId) {
    if (!redisUtils.checkRedisConnection()) {
      EInventory eInventory = inventoryRepository.findByVariantId(variantId).orElseThrow();

      return ResponseEntity.ok(
          Inventory.builder()
              .quantitySold(eInventory.getQuantitySold())
              .quantityAvailable(eInventory.getQuantityAvailable())
              .quantityReserved(eInventory.getQuantityReserved())
              .build());
    }

    RInventory rInventory = inventoryRedisRepository.findById(variantId).orElseThrow();

    return ResponseEntity.ok(
        Inventory.builder()
            .quantitySold(rInventory.getInventory().getQuantitySold())
            .quantityAvailable(rInventory.getInventory().getQuantityAvailable())
            .quantityReserved(rInventory.getInventory().getQuantityReserved())
            .build());
  }

  @Override
  public void updateInventoryInRedis(Inventory inventory) {
    if (!redisUtils.checkRedisConnection()) {
      updateInventoryInDb(inventory);
    } else {
      inventoryRedisRepository
          .findById(inventory.getVariantId())
          .ifPresentOrElse(
              rInventory -> {
                int quantity =
                    rInventory.getInventory().getQuantityAvailable()
                        - inventory.getQuantityAvailable();
                rInventory.getInventory().setQuantityAvailable(quantity);
                inventoryRedisRepository.save(rInventory);
                redisUtils.addChangeToQueue(rInventory);
              },
              () -> {
                RInventory rInventory =
                    RInventory.builder()
                        .inventory(
                            Inventory.builder()
                                .quantityAvailable(inventory.getQuantityAvailable())
                                .sku(inventory.getSku())
                                .id(inventory.getId())
                                .variantId(inventory.getVariantId())
                                .quantitySold(inventory.getQuantitySold())
                                .build())
                        .variantId(inventory.getVariantId())
                        .build();

                inventoryRedisRepository.save(rInventory);
                redisUtils.addChangeToQueue(rInventory);
              });
    }
  }

  @Override
  public void updateInventoryInDb(Inventory inventory) {
    inventoryRepository
        .findByVariantId(inventory.getVariantId())
        .ifPresentOrElse(
            eInventory -> {
              int quantity = eInventory.getQuantityAvailable() - inventory.getQuantityAvailable();
              eInventory.setQuantityAvailable(quantity);
              inventoryRepository.save(eInventory);
            },
            () -> {
              throw new ResourceNotFoundException(
                  HttpStatus.UNPROCESSABLE_ENTITY,
                  ErrorResponse.builder()
                      .code("inventory_issue")
                      .message("Inventory does not exist for the product")
                      .build());
            });
  }
}
