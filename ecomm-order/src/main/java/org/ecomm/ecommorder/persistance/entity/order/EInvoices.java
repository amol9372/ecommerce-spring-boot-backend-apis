package org.ecomm.ecommorder.persistance.entity.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommorder.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "invoices")
public class EInvoices extends BaseEntity {

  @Column(name = "order_id")
  Integer orderId;

  @Column(name = "invoice_no")
  String invoiceNo;

  String url;
}
