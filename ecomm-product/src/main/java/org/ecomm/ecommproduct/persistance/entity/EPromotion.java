package org.ecomm.ecommproduct.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "promotion")
public class EPromotion extends BaseEntity {

  String name;

  String description;

  @Column(name = "applied_to_type")
  int appliedTo; // actual product, category, brand

  @Enumerated(EnumType.STRING)
  @Column(name = "applied_to")
  PromoAppliedType appliedToType; // category, products, brand

  @Column(name = "expires_in")
  int expiresIn; // days

  @Enumerated(EnumType.STRING)
  PromotionStatus status; // ACTIVE, EXPIRED

  @Column(name = "percentage_discount")
  int percentageDiscount; // percentage
}
