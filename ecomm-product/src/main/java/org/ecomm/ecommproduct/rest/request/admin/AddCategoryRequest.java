package org.ecomm.ecommproduct.rest.request.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddCategoryRequest {
  String name;
  Integer parentId;
}
