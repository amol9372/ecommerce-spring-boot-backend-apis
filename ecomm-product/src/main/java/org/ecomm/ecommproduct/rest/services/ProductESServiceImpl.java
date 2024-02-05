package org.ecomm.ecommproduct.rest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.rest.builder.ProductESBuilder;
import org.ecomm.ecommproduct.rest.model.ProductVariantResponse;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProductVariant;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.ecomm.ecommproduct.utils.ElasticSearchQueryBuilder;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductESServiceImpl implements ProductESService {

  @Autowired ElasticsearchOperations elasticsearchOperations;


  @Override
  public void saveProduct(EProduct product) {

    List<ESProductVariant> esProductVariant = ProductESBuilder.with(product);

    log.info("Saving product entities in the index ::: {}", (long) esProductVariant.size());
    elasticsearchOperations.save(esProductVariant);
  }

  @Override
  public PagedResponse<ProductVariantResponse> searchProducts(SearchRequest request) {

    NativeQuery searchQuery = ElasticSearchQueryBuilder.createSearchQuery(request);
    SearchHits<ESProductVariant> searchHits = elasticsearchOperations.search(searchQuery, ESProductVariant.class);

    List<ESProductVariant> esProductVariants =
        searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

    var products = ProductESBuilder.with(esProductVariants);

    PagedResponse<ProductVariantResponse> pagedResponse =
        PagedResponse.<ProductVariantResponse>builder()
            .page(request.getPage().getPageNo())
            .pageSize(request.getPage().getPageSize())
            .items(products)
            .resultCount((int) searchHits.stream().count())
            .build();

    log.info("Criteria search results are ::: {}", searchHits.stream().count());

    return pagedResponse;
  }

  @Override
  public List<ObjectNode> getCartProductDetails(String variantIds) {

    List<ESProductVariant> productVariants =
        Arrays.stream(variantIds.split(","))
            .map(id -> elasticsearchOperations.get(id, ESProductVariant.class))
            .filter(Objects::nonNull)
            .toList();

    ObjectMapper mapper = new ObjectMapper();

    return Utility.stream(productVariants).map(esProductVariant -> {

      ObjectNode jsonObject = mapper.createObjectNode();
      jsonObject.put("variantId", esProductVariant.getId());
      jsonObject.put("productId", esProductVariant.getProductId());
      jsonObject.put("brand", esProductVariant.getBrand());
      jsonObject.put("name", esProductVariant.getName());
      jsonObject.put("imageUrl", "");
      jsonObject.put("price", esProductVariant.getPrice());
      jsonObject.put("inventoryAvailable", esProductVariant.getInventory().getQuantityAvailable());

      return jsonObject;
    }).collect(Collectors.toList());

  }
}
