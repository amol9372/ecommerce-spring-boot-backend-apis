package org.ecomm.ecommcart.rest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommcart.exception.ErrorCodes;
import org.ecomm.ecommcart.exception.ErrorResponse;
import org.ecomm.ecommcart.exception.ResourceNotFoundException;
import org.ecomm.ecommcart.exception.SoldOutInventoryException;
import org.ecomm.ecommcart.persistance.entity.cart.CartStatus;
import org.ecomm.ecommcart.persistance.entity.cart.ECart;
import org.ecomm.ecommcart.persistance.entity.cart.ECartItem;
import org.ecomm.ecommcart.persistance.repository.CartItemRepository;
import org.ecomm.ecommcart.persistance.repository.CartRepository;
import org.ecomm.ecommcart.rest.builder.CartBuilder;
import org.ecomm.ecommcart.rest.feign.ProductServiceClient;
import org.ecomm.ecommcart.rest.feign.UserServiceClient;
import org.ecomm.ecommcart.rest.feign.model.ProductInventory;
import org.ecomm.ecommcart.rest.model.Cart;
import org.ecomm.ecommcart.rest.model.CartItem;
import org.ecomm.ecommcart.rest.feign.model.UserResponse;
import org.ecomm.ecommcart.rest.request.AddToCartRequest;
import org.ecomm.ecommcart.utils.Utility;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

  @Autowired CartRepository cartRepository;

  @Autowired CartItemRepository cartItemRepository;

  @Autowired ProductServiceClient productServiceClient;

  @Autowired UserServiceClient userServiceClient;

  @Override
  @Transactional
  public void addProductToCart(AddToCartRequest request) {
    // check if cart already exists
    UserResponse user = getLoggedInUser();

    checkInventory(request);

    var activeCart =
        cartRepository
            .findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE)
            .orElse(createCart.apply(user.getId()));

    // check if item already exists in cart
    Utility.stream(activeCart.getCartItems())
        .filter(item -> item.getVariantId().equals(request.getVariantId()))
        .findFirst()
        .ifPresentOrElse(
            item -> {
              int quantity = item.getQuantity() + request.getQuantity();
              item.setQuantity(quantity);
              updateCartItem(activeCart, item);
            },
            () -> {
              var cartItem =
                  ECartItem.builder()
                      .cart(activeCart)
                      .productId(request.getProductId())
                      .quantity(request.getQuantity())
                      .variantId(request.getVariantId())
                      .build();

              addItemToCart(activeCart, cartItem);
            });

    log.info("Adding product to cart ::: {}", request);
    cartRepository.save(activeCart);

    log.info("Updating inventory ::: {}", request);

    updateInventory(
        ProductInventory.builder()
            .quantity(request.getQuantity())
            .variantId(request.getVariantId())
            .build());
  }

  private synchronized void checkInventory(AddToCartRequest request) {
    ProductInventory inventory =
        productServiceClient.checkInventory(request.getVariantId(), "ecomm-cart").getBody();

    if (inventory.getQuantity() <= request.getQuantity()) {
      throw new SoldOutInventoryException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          ErrorResponse.builder().code("inventory_issue").message("Inventory has been sold out").build());
    }
  }


  @Async
  public void updateInventory(ProductInventory inventory) {

    productServiceClient.updateInventory(inventory, "ecomm-cart");
  }

  @Override
  public Cart getCart() {

    UserResponse user = getLoggedInUser();

    return getActiveCartProducts(user);
  }

  @Override
  @Transactional
  public void deleteCartItem(int cartItemId) {

    UserResponse user = getLoggedInUser();

    cartRepository.deleteCartItem(cartItemId);

    // delete cart if 0 items
    var cart = cartRepository.findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE).orElseThrow();

    if (cart.getCartItems().isEmpty()) {
      cartRepository.deleteById(cart.getId());
    }
  }

  @Override
  public void submit() {
    UserResponse user = getLoggedInUser();

    var cart =
        cartRepository
            .findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE)
            .orElse(ECart.builder().build());

    // change cart status - CHECKOUT

  }

  @Override
  public Cart getCart(String email) {
    UserResponse user = userServiceClient.getUserByEmail(email, "ecomm-cart").getBody();

    assert user != null;

    return getActiveCartProducts(user);
  }

  @Override
  public void checkout() {
    UserResponse user = getLoggedInUser();

    ECart cart =
        cartRepository.findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE).orElseThrow();
    cart.setStatus(CartStatus.SUBMITTED);

    cartRepository.save(cart);
  }

  private Cart getActiveCartProducts(UserResponse user) {
    var cart =
        cartRepository
            .findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        ErrorResponse.builder()
                            .code(ErrorCodes.RESOURCE_DOES_NOT_EXIST)
                            .message("Cart does not exist")
                            .build()));

    String variantIds =
        Utility.stream(cart.getCartItems())
            .map(ECartItem::getVariantId)
            .map(String::valueOf)
            .collect(Collectors.joining(","));

    List<CartItem> productCartDetails =
        productServiceClient.getProductCartDetail(variantIds, "ecomm-cart").getBody();

    List<CartItem> cartItems =
        cart.getCartItems().stream().map(CartBuilder::with).collect(Collectors.toList());

    productCartDetails =
        Utility.stream(productCartDetails)
            .map(
                cartItem -> {
                  var item = findByVariant.apply(cartItem.getVariantId(), cartItems);
                  cartItem.setQuantity(item.getQuantity());
                  cartItem.setId(item.getId());
                  return cartItem;
                })
            .collect(Collectors.toList());

    double totalPrice =
        productCartDetails.stream()
            .collect(Collectors.summarizingDouble(CartItem::getPrice))
            .getSum();

    return Cart.builder()
        .cartItems(productCartDetails)
        .id(cart.getId())
        .status(cart.getStatus().name())
        .totalPrice(totalPrice)
        .userId(cart.getUserId())
        .build();
  }

  BiFunction<Integer, List<CartItem>, CartItem> findByVariant =
      (variant, productCartDetail) ->
          productCartDetail.stream()
              .filter(cartItem -> Objects.equals(cartItem.getVariantId(), variant))
              .findFirst()
              .orElseThrow();

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

  private static void addItemToCart(ECart activeCart, ECartItem cartItem) {
    Optional.ofNullable(activeCart.getCartItems())
        .ifPresentOrElse(
            eCartItems -> activeCart.getCartItems().add(cartItem),
            () -> {
              var cartItems = new ArrayList<ECartItem>();
              cartItems.add(cartItem);
              activeCart.setCartItems(cartItems);
            });
  }

  private static void updateCartItem(ECart activeCart, ECartItem cartItem) {
    Optional.ofNullable(activeCart.getCartItems())
        .ifPresent(
            eCartItems -> {
              activeCart.getCartItems().removeIf(item -> item.getId().equals(cartItem.getId()));
              activeCart.getCartItems().add(cartItem);
            });
  }

  Function<Integer, ECart> createCart =
      (userId) -> ECart.builder().userId(userId).status(CartStatus.ACTIVE).build();
}
