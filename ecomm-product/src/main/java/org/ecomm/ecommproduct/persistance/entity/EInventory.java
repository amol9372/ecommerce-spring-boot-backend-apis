package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "inventory")
public class EInventory extends BaseEntity {

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id")
    EProduct product;

    @Column(name = "sku")
    String sku;

    @Column(name = "quantity_available")
    int quantityAvailable;

    @Column(name = "quantity_reserved")
    int quantityReserved;

    @Column(name = "quantity_sold")
    int quantitySold;

}
