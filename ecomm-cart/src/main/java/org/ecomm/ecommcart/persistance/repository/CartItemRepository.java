package org.ecomm.ecommcart.persistance.repository;

import org.ecomm.ecommcart.persistance.entity.cart.ECartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<ECartItem, Integer> {
    
}
