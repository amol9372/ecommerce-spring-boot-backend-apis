package org.ecomm.ecommproduct.rest.services;

import org.ecomm.ecommproduct.exception.ErrorCodes;
import org.ecomm.ecommproduct.exception.ErrorResponse;
import org.ecomm.ecommproduct.exception.ResourceNotFoundException;
import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.persistance.repository.InventoryRedisRepository;
import org.ecomm.ecommproduct.persistance.repository.ProductRepository;
import org.ecomm.ecommproduct.rest.builder.ProductBuilder;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.rest.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired ProductRepository productRepository;

  @Override
  public ProductDetails getProductDetails(int productId) {

    var product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        ErrorResponse.builder()
                            .message("Requested resource is not found")
                            .code(ErrorCodes.NOT_FOUND)
                            .build()));

    return ProductBuilder.with(product);
  }
}
