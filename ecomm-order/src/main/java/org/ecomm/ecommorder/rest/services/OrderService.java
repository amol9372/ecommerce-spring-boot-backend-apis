package org.ecomm.ecommorder.rest.services;

import org.ecomm.ecommorder.rest.model.OrderSummary;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

      OrderSummary generateOrderSummary();

      void createOrder();
}
