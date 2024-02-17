package org.ecomm.ecommorder.rest.services;

import com.stripe.model.PaymentLink;
import org.ecomm.ecommorder.rest.model.StripeInput;
import org.ecomm.ecommorder.utils.StripeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService{

    @Autowired
    StripeUtils stripeUtils;

    @Override
    public PaymentLink createPaymentLink(StripeInput stripeInput) {
        return stripeUtils.createPaymentLink(stripeInput);
    }
}
