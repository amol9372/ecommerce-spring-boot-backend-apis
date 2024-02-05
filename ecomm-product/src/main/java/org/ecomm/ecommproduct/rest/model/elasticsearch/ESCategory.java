package org.ecomm.ecommproduct.rest.model.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;


@Setter
@Getter
@Builder
public class ESCategory {

    Integer id;
    String name;

    @Field(name = "parent_id")
    Integer parentId;

}
