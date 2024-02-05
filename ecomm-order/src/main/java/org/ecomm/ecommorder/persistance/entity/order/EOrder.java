package org.ecomm.ecommorder.persistance.entity.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommorder.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "orders")
public class EOrder extends BaseEntity {

  @Column(name = "order_no")
  UUID orderNo;

  @Column(name = "user_id")
  Integer userId;

  @Column(name = "order_placed_on")
  LocalDateTime orderPlacedOn;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  OrderStatus orderStatus;

  @Column(name = "total_price")
  double totalPrice;

  String currency;

  @Column(name = "delivered_on")
  LocalDateTime deliveredOn;

  @Column(name = "address_id")
  Integer addressId;

  /*
  Not Implemented as of now
   */
  @Column(name = "payment_method")
  String paymentMethod;

  @Enumerated(EnumType.STRING)
  DeliveryStatus deliveryStatus;

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
  List<EOrderItem> orderItems;
}
