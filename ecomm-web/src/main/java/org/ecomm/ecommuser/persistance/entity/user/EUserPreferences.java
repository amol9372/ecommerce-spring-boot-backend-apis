package org.ecomm.ecommuser.persistance.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "user_preferences")
public class EUserPreferences extends BaseEntity {

  @Column(name = "user_id")
  Integer userId;

  String language;

  @Column(name = "two_factor_enabled")
  boolean twoFactorEnabled;

  @Column(name = "two_factor_mode")
  String twoFactorMode;

  String currency;
}
