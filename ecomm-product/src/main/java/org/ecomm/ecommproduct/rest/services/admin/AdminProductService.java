package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.springframework.stereotype.Service;

@Service
public interface AdminProductService {

    void getProduct();

    void addProduct(AddProductRequest product);

}
