package org.ecomm.ecommorder.persistance.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommorder.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "order_line")
public class EOrderItem extends BaseEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  @JsonBackReference
  EOrder order;

  @Column(name = "product_id")
  Integer productId;

  @Column(name = "variant_id")
  Integer variantId;

  int quantity;
}
