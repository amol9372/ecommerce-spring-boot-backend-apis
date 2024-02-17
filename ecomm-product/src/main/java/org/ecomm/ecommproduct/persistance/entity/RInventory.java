package org.ecomm.ecommproduct.persistance.entity;

import lombok.*;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RInventory implements Serializable {

    @Id
  Integer variantId;
  Inventory inventory;
}
