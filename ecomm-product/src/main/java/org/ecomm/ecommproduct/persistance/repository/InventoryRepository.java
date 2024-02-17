package org.ecomm.ecommproduct.persistance.repository;

import jakarta.transaction.Transactional;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<EInventory, Integer> {

  Optional<EInventory> findByVariantId(int variantId);

  @Modifying
  @Transactional
  @Query("UPDATE EInventory SET quantityAvailable = :quantity WHERE sku = :sku")
  void updateInventory(@Param("quantity") int quantity, @Param("sku") String sku);
}
