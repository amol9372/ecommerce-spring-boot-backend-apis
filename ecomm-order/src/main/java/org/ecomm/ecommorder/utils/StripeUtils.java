package org.ecomm.ecommorder.utils;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StripeUtils {

  public static void main(String[] args) throws StripeException {
    createPaymentLink();
  }

  public static void createPaymentLink() throws StripeException {
    // Set your secret key. Remember to switch to your live secret key in production.
    // See your keys here: https://dashboard.stripe.com/apikeys
    Stripe.apiKey =
        "sk_test_51OQ0X5BRoiMqV5F6b6jUDlk3iSld8Pt47Q2rXx7zGsu6xToEVeGgCFd0chFiu9cjA6tUx0rH08lRnq9bIjwlBwQM00tBDmpE0E";

    Product product =
        Product.create(
            ProductCreateParams.builder().setId("1790").setName("Sample product").build());

    PriceCreateParams priceCreateParams =
        PriceCreateParams.builder()
            .setCurrency("cad")
            .setUnitAmount(1000L)
            .setProduct(product.getId())
            .build();

    Price price = Price.create(priceCreateParams);

    PaymentLinkCreateParams paymentLinkCreateParams =
        PaymentLinkCreateParams.builder()
            .addLineItem(
                PaymentLinkCreateParams.LineItem.builder()
                    .setPrice(price.getId())
                    .setQuantity(1L)
                    .build())
            .build();

    PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);

    log.info("Payment link ::: {}", paymentLink);
  }
}
