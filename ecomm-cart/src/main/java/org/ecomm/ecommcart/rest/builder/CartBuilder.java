package org.ecomm.ecommcart.rest.builder;

import org.ecomm.ecommcart.persistance.entity.cart.ECartItem;
import org.ecomm.ecommcart.rest.model.CartItem;

public class CartBuilder {

    public static CartItem with(ECartItem item) {
        return CartItem.builder()
                .variantId(item.getVariantId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .id(item.getId())
                .build();
    }
}
