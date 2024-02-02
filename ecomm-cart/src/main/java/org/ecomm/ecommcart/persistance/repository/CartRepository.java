package org.ecomm.ecommcart.persistance.repository;

import jakarta.transaction.Transactional;
import org.ecomm.ecommcart.persistance.entity.cart.CartStatus;
import org.ecomm.ecommcart.persistance.entity.cart.ECart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<ECart, Integer> {

  Optional<ECart> findByUserIdAndStatus(int userId, CartStatus cartStatus);

  @Modifying
  @Transactional
  @Query("DELETE FROM ECartItem WHERE ID = :cartItemId")
  void deleteCartItem(@Param("cartItemId") int cartItemId);
}
