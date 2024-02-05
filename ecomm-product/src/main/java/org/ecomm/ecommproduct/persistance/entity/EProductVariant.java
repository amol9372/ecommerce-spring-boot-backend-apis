package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "product_variant")
public class EProductVariant extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "product_id")
  EProduct product;

  @Column(name = "variant_id")
  Integer variantId;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  JsonNode featureValues;

  double price;

  @Column(name = "sku")
  String sku;

}
