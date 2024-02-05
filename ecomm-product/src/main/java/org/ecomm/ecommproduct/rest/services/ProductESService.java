package org.ecomm.ecommproduct.rest.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.rest.model.ProductVariantResponse;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductESService {
    
    void saveProduct(EProduct product);

    PagedResponse<ProductVariantResponse> searchProducts(SearchRequest request);

    List<ObjectNode> getCartProductDetails(String variantIds);
}
