package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "product")
@ToString
public class EProduct extends BaseEntity {

  String name;
  String description;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  EBrand brand;

  @OneToOne
  @JoinColumn(name = "category_id")
  ECategory category;

  @Column(name = "category_tree")
  String categoryTree;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  JsonNode features;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  List<EProductImage> productImages;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  List<EProductVariant> variants;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  List<EInventory> inventories;
}
