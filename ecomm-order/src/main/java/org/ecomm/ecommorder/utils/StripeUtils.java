package org.ecomm.ecommorder.utils;

import static org.ecomm.ecommorder.utils.Utility.stream;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommorder.rest.feign.model.AddressResponse;
import org.ecomm.ecommorder.rest.feign.model.ProductOrderDetails;
import org.ecomm.ecommorder.rest.feign.model.UserResponse;
import org.ecomm.ecommorder.rest.model.StripeInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StripeUtils {

  @Value("${stripe.api-key}")
  String apiKey;

  @PostConstruct
  public void initializeStripe() {
    Stripe.apiKey = apiKey;
  }

  public PaymentLink createPaymentLink(StripeInput stripeInput) {

    PaymentLink paymentLink = null;
    try {
      Customer customer =
          createCustomer(
              Pair.of(
                  stripeInput.getUserAddressPair().getFirst(),
                  stripeInput.getUserAddressPair().getSecond()));

      List<Price> prices = getPrices(stripeInput.getOrderDetails());
      List<PaymentLinkCreateParams.LineItem> lineItems = getLineItems(prices);
      PaymentLinkCreateParams paymentLinkCreateParams = getPaymentLinkCreateParams(lineItems);
      paymentLink = PaymentLink.create(paymentLinkCreateParams);

      log.info("Payment link ::: {} , Customer ::: {}", paymentLink, customer);
    } catch (StripeException e) {
      log.info("Stripe Exception {}", e.getCode());
      throw new RuntimeException(e);
    }

    return paymentLink;
  }

  private Customer createCustomer(Pair<UserResponse, AddressResponse> userAddressPair) {

    try {
      Customer customer =
          Customer.create(
              CustomerCreateParams.builder()
                  .setName(userAddressPair.getFirst().getFirstName())
                  .setEmail(userAddressPair.getFirst().getEmail())
                  // .setPhone("7121273123")
                  .setAddress(
                      CustomerCreateParams.Address.builder()
                          .setCity(userAddressPair.getSecond().getCity())
                          .setCountry(userAddressPair.getSecond().getCountry())
                          .setPostalCode(userAddressPair.getSecond().getPostalCode())
                          .setState(userAddressPair.getSecond().getState())
                          .setLine1(userAddressPair.getSecond().getStreetAddress())
                          .build())
                  .build());

      return customer;
    } catch (StripeException e) {
      throw new RuntimeException(e);
    }
  }

  private Product getProduct(ProductOrderDetails item) {
    try {
      return Product.retrieve(String.valueOf(item.getVariantId()));

    } catch (StripeException e) {
      try {
        return Product.create(
            ProductCreateParams.builder()
                .setId(String.valueOf(item.getVariantId()))
                .setName(item.getName())
                .setType(ProductCreateParams.Type.GOOD)
                .build());
      } catch (StripeException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private PaymentLinkCreateParams getPaymentLinkCreateParams(
      List<PaymentLinkCreateParams.LineItem> lineItems) {
    PaymentLinkCreateParams paymentLinkCreateParams =
        PaymentLinkCreateParams.builder()
            .setAfterCompletion(
                PaymentLinkCreateParams.AfterCompletion.builder()
                    .setRedirect(
                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                            .setUrl("https://ecomm-ui-kappa.vercel.app/order-submitted")
                            // .putAllExtraParam(Map.of())
                            .build())
                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                    .build())
            .setCustomerCreation(PaymentLinkCreateParams.CustomerCreation.IF_REQUIRED)
            .setAutomaticTax(
                PaymentLinkCreateParams.AutomaticTax.builder().setEnabled(true).build())
            .addAllLineItem(lineItems)
            .build();
    return paymentLinkCreateParams;
  }

  private List<Price> getPrices(List<ProductOrderDetails> orderDetails) {
    List<Price> prices =
        stream(orderDetails)
            .map(
                item -> {
                  try {
                    return Price.create(
                        PriceCreateParams.builder()
                            .setCurrency("cad")
                            .setUnitAmount((long) item.getPrice() * 100)
                            .setProduct(getProduct(item).getId())
                            .build());
                  } catch (StripeException e) {
                    throw new RuntimeException(e);
                  }
                })
            .collect(Collectors.toList());
    return prices;
  }

  private List<PaymentLinkCreateParams.LineItem> getLineItems(List<Price> prices) {

    return prices.stream()
        .map(
            price ->
                PaymentLinkCreateParams.LineItem.builder()
                    .setPrice(price.getId())
                    .setQuantity(1L)
                    .build())
        .collect(Collectors.toList());
  }
}
