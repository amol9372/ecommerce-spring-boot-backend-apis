package org.ecomm.ecommorder.rest.services;

import com.stripe.model.PaymentLink;
import org.ecomm.ecommorder.rest.model.OrderSummary;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

      OrderSummary generateOrderSummary();

      PaymentLink createOrder();
}
