package org.ecomm.ecommorder.persistance.repository;

import org.ecomm.ecommorder.persistance.entity.order.EOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<EOrder, Integer> {

}
