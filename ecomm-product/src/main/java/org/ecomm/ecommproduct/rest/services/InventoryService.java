package org.ecomm.ecommproduct.rest.services;

import org.ecomm.ecommproduct.rest.model.Inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    ResponseEntity<?> checkInventory(int variantId);

    void updateInventoryInRedis(Inventory inventory);

    void updateInventoryInDb(Inventory inventory);
}
