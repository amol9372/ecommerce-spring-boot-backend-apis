package org.ecomm.ecommorder.rest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.PaymentLink;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommorder.persistance.entity.order.DeliveryStatus;
import org.ecomm.ecommorder.persistance.entity.order.EOrder;
import org.ecomm.ecommorder.persistance.entity.order.EOrderItem;
import org.ecomm.ecommorder.persistance.entity.order.OrderStatus;
import org.ecomm.ecommorder.persistance.repository.OrderRepository;
import org.ecomm.ecommorder.rest.feign.CartServiceClient;
import org.ecomm.ecommorder.rest.feign.UserServiceClient;
import org.ecomm.ecommorder.rest.feign.model.AddressResponse;
import org.ecomm.ecommorder.rest.feign.model.CartResponse;
import org.ecomm.ecommorder.rest.feign.model.ProductOrderDetails;
import org.ecomm.ecommorder.rest.feign.model.UserResponse;
import org.ecomm.ecommorder.rest.model.OrderSummary;
import org.ecomm.ecommorder.rest.model.StripeInput;
import org.ecomm.ecommorder.utils.TaxUtils;
import org.ecomm.ecommorder.utils.Utility;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired CartServiceClient cartServiceClient;

  @Autowired UserServiceClient userServiceClient;

  @Autowired OrderRepository orderRepository;

  @Autowired StripeService stripeService;

  @Override
  public OrderSummary generateOrderSummary() {

    /*
    Get cart details from ecomm-cart
     */
    UserResponse user = getLoggedInUser();

    ResponseEntity<CartResponse> cartResponseEntity =
        cartServiceClient.getActiveCart(user.getEmail(), "order");

    CartResponse response = cartResponseEntity.getBody();
    return getSummary(response);
  }

  /**
   * <ul>
   *   <li>Get order items [order_line]
   *   <li>Get user address [active]
   *   <li>Payment method - [Stripe payment link]
   *   <li>Get Order summary
   *   <li>Clear cart
   * </ul>
   *
   * @return
   */
  @Override
  @Transactional
  public PaymentLink createOrder() {
    UserResponse user = getLoggedInUser();

    ResponseEntity<CartResponse> cartResponseEntity =
        cartServiceClient.getActiveCart(user.getEmail(), "order");

    CartResponse cartResponse = cartResponseEntity.getBody();

    OrderSummary orderSummary = getSummary(cartResponse);

    ResponseEntity<AddressResponse> addressResponse =
        userServiceClient.getActiveAddressByEmail(user.getEmail(), "order");

    EOrder eOrder = createOrder(user, orderSummary, addressResponse);

    List<EOrderItem> orderItems =
        Utility.stream(cartResponse.getProductCartDetails())
            .map(item -> getOrderItem(item, eOrder))
            .collect(Collectors.toList());

    eOrder.setOrderItems(orderItems);

    orderRepository.save(eOrder);
    cartServiceClient.checkout(user.getEmail(), "ecomm-cart");

    // create stripe payment link
    return stripeService.createPaymentLink(
            StripeInput.builder()
                    .userAddressPair(Pair.of(user, addressResponse.getBody()))
                    .orderDetails(cartResponse.getProductCartDetails())
                    .build());

  }

  private static EOrder createOrder(
      UserResponse user,
      OrderSummary orderSummary,
      ResponseEntity<AddressResponse> addressResponse) {
    return EOrder.builder()
        .orderNo(UUID.randomUUID())
        .orderStatus(OrderStatus.ORDER_PLACED)
        .deliveryStatus(DeliveryStatus.NOT_SHIPPED)
        .paymentMethod("link")
        .userId(user.getId())
        .totalPrice(orderSummary.getItemsSubTotal() + orderSummary.getTax())
        .addressId(addressResponse.getBody().getId())
        .orderPlacedOn(LocalDateTime.now())
        .build();
  }

  private static OrderSummary getSummary(CartResponse response) {
    double subTotal =
        response.getProductCartDetails().stream()
            .collect(Collectors.summarizingDouble(ProductOrderDetails::getPrice))
            .getSum();

    return OrderSummary.builder()
        .items(response.getProductCartDetails().size())
        .itemsSubTotal(subTotal)
        .tax(TaxUtils.calculateTax(subTotal))
        .shipping(0.0) // not implemented yet
        .build();
  }

  private static UserResponse getLoggedInUser() {
    ObjectMapper objectMapper = new ObjectMapper();

    UserResponse user;
    try {
      user = objectMapper.readValue(MDC.get("user"), UserResponse.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return user;
  }

  private static EOrderItem getOrderItem(ProductOrderDetails item, EOrder eOrder) {
    return EOrderItem.builder()
        .order(eOrder)
        .productId(item.getProductId())
        .variantId(item.getVariantId())
        .quantity(item.getQuantity())
        .build();
  }
}
