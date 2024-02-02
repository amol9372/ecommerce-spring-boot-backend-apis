package org.ecomm.ecommcart.persistance.entity.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommcart.persistance.entity.BaseEntity;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "cart_item")
@NoArgsConstructor
public class ECartItem  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  @JsonBackReference
  ECart cart;

  @Column(name = "product_id")
  Integer productId;

  @Column(name = "variant_id")
  Integer variantId;

  int quantity;
}
