package org.ecomm.ecommuser.persistance.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommuser.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "user_address")
public class EUserAddress extends BaseEntity {

  @Column(name = "user_id")
  Integer userId;

  @ManyToOne
  @JoinColumn(name = "address_id")
  EAddress address;

  @Column(name = "default_address")
  boolean defaultAddress;
}
