package org.ecomm.ecommproduct.rest.request.pagination;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {

    List<T> items;
    int page;
    int pageSize;
    int resultCount;

}
