package org.ecomm.ecommproduct.rest.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.model.BaseModel;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BrandResponse extends BaseModel {

    String name;
    List<String> categories;

}
