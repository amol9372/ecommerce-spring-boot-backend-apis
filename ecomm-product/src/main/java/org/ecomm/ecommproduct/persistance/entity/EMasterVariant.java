package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "master_variant")
public class EMasterVariant extends BaseEntity {

  @Column(name = "category_id")
  Integer categoryId;


  @Type(JsonType.class)
  @Column(columnDefinition = "json", name = "feature_names")
  JsonNode featureNames;
}
