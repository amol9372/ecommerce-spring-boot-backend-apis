package org.ecomm.ecommcart.rest.controller;

import org.ecomm.ecommcart.rest.request.AddToCartRequest;
import org.ecomm.ecommcart.rest.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("cart")
public class CartController {

  @Autowired CartService cartService;

  @PostMapping
  public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest request) {
    cartService.addProductToCart(request);
    return ResponseEntity.created(URI.create("/api/cart")).build();
  }

  @GetMapping
  public ResponseEntity<Object> getCart() {
    var cart = cartService.getCart();
    return ResponseEntity.ok(cart);
  }


  @PostMapping("submit")
  public ResponseEntity<Object> submit(){
    cartService.submit();
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("{cartItemId}")
  public ResponseEntity<?> deleteCartItem(@PathVariable int cartItemId) {
    cartService.deleteCartItem(cartItemId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("checkout")
  public void checkout(){
    cartService.checkout();
  }

}
