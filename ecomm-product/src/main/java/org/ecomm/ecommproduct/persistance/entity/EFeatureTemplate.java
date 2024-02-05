package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
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
@Table(name = "feature_template")
public class EFeatureTemplate extends BaseEntity {

  @Column(name = "category_id")
  Integer categoryId;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  JsonNode features;
}
