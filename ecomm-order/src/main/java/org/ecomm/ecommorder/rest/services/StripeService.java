package org.ecomm.ecommorder.rest.services;

import com.stripe.model.PaymentLink;
import org.ecomm.ecommorder.rest.model.StripeInput;
import org.springframework.stereotype.Service;

@Service
public interface StripeService {

    PaymentLink createPaymentLink(StripeInput stripeInput);

}
