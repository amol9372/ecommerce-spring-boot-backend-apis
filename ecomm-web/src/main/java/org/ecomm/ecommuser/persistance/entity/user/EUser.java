package org.ecomm.ecommuser.persistance.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
@Table(name = "users")
public class EUser extends BaseEntity {

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  String email;

  String mobile;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Column(name = "updated_at")
  LocalDateTime updatedAt;

  String provider;

  @Enumerated(EnumType.STRING)
  UserStatus status;

  @JsonManagedReference
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  EUserRole role;
}
