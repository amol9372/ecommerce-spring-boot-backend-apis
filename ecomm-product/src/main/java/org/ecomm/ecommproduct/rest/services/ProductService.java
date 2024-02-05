package org.ecomm.ecommproduct.rest.services;

import org.ecomm.ecommproduct.rest.model.ProductDetails;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductDetails getProductDetails(int productId);

}
