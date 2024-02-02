package org.ecomm.ecommcart.persistance.entity.cart;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommcart.persistance.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "cart")
public class ECart  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(name = "user_id")
  Integer userId;

  @Enumerated(EnumType.STRING)
  CartStatus status;

  @Column(name = "total_price")
  Double totalPrice;

  Double discount;

  @Column(name = "created_at")
  LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  LocalDateTime updatedAt = LocalDateTime.now();

  @JsonManagedReference
  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  List<ECartItem> cartItems;
}
