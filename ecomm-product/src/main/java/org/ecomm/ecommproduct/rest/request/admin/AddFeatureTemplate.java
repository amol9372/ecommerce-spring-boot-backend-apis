package org.ecomm.ecommproduct.rest.request.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFeatureTemplate {

  Integer categoryId;

  @JsonProperty("feature_fields")
  Object featureFields;
}
