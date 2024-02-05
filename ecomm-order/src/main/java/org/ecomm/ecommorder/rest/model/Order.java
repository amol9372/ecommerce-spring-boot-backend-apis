package org.ecomm.ecommorder.rest.model;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  String orderNo;
  String status;
  String currency;
  String deliveryStatus;
  LocalDateTime deliveredOn;
  String paymentMethod;
}
