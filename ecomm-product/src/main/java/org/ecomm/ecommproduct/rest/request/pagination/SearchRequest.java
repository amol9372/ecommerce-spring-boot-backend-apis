package org.ecomm.ecommproduct.rest.request.pagination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequest {

  String searchTerm;
  List<Filter> filters;
  List<Sort> sorts;
  Pagination pagination;

  public Pagination getPage() {
    if (Objects.isNull(pagination)) {
      return new Pagination();
    }

    return pagination;
  }
}
