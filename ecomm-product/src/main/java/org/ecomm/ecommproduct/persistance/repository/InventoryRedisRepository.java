package org.ecomm.ecommproduct.persistance.repository;

import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRedisRepository extends CrudRepository<RInventory, Integer> {

}
