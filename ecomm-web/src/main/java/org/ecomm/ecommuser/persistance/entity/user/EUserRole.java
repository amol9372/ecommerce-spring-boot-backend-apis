package org.ecomm.ecommuser.persistance.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommuser.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "user_roles")
public class EUserRole extends BaseEntity {


  @Enumerated(EnumType.STRING)
  UserRole role;

  @JsonBackReference
  @OneToOne
  @JoinColumn(name = "user_id")
  EUser user;
}
