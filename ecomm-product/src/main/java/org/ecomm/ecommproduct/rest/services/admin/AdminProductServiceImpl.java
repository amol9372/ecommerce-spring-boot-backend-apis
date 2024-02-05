package org.ecomm.ecommproduct.rest.services.admin;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.*;
import org.ecomm.ecommproduct.persistance.repository.*;
import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.ecomm.ecommproduct.rest.services.ProductESService;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

  @Autowired CategoryRepository categoryRepository;

  @Autowired ObjectMapper objectMapper = new ObjectMapper();

  @Autowired ProductRepository productRepository;

  @Autowired BrandRepository brandRepository;

  //  @Autowired FeatureTemplateRepository featureTemplateRepository;

  @Autowired MasterVariantRepository masterVariantRepository;

  @Autowired ProductESService productESService;

  @Override
  public void getProduct() {
    List<EProduct> products = productRepository.findAll();
    log.info("Products from DB ::: {}", products);
  }

  @Override
  @Transactional
  public void addProduct(AddProductRequest request) {
    /*
    - TODO - name validation [with ElasticSearch]
    - TODO - features validation
    - update inventory
    - save product
    - TODO - save images & get s3 url
    - save the entity in elasticsearch
    */
    var category =
        categoryRepository
            .findById(request.getCategory())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Product category does not exist, please use GET /admin/category API to fetch all categories"));

    List<ECategory> eCategories = categoryRepository.findAll();
    String categoryTree = getCategoryTree(category, eCategories);
    // validateFeatureTemplate(product, features);

    List<EProductImage> productImages =
        Utility.stream(request.getImages())
            .map(
                item ->
                    EProductImage.builder()
                        .imageUrl(item.getUrl())
                        .type(ProductImageType.PRODUCT_IMAGES)
                        .build())
            .collect(toList());

    List<EInventory> inventories =
        Utility.stream(request.getVariants())
            .map(
                item ->
                    EInventory.builder()
                        .sku(item.getSku())
                        .quantityAvailable(item.getQuantity())
                        .build())
            .collect(toList());

    List<EProductVariant> variants = getProductVariants(request, eCategories);

    EBrand eBrand =
        brandRepository.findByNameAndBrandCategory(
            request.getBrand().getName(), request.getBrand().getCategory());

    var eProduct =
        EProduct.builder()
            .name(request.getName())
            .description(request.getDescription())
            .category(category)
            .brand(eBrand)
            .categoryTree(categoryTree)
            .features(objectMapper.valueToTree(request.getFeatures()))
            .productImages(productImages)
            .variants(variants)
            .inventories(inventories)
            .build();

    variants.forEach(item -> item.setProduct(eProduct));
    productImages.forEach(item -> item.setProduct(eProduct));
    inventories.forEach(item -> item.setProduct(eProduct));

    EProduct savedProduct = productRepository.save(eProduct);

    // setting the brand
    savedProduct.setBrand(eBrand);

    log.info("Saved Product in DB ::: {}", savedProduct);
    productESService.saveProduct(savedProduct);
  }

  private List<EProductVariant> getProductVariants(
      AddProductRequest request, List<ECategory> categories) {
    EMasterVariant masterVariant =
        findCategoryWithMasterVariant(
            categories, null, ECategory.builder().id(request.getCategory()).build());

    List<EProductVariant> variants =
        Utility.stream(request.getVariants())
            .map(
                item ->
                    EProductVariant.builder()
                        .variantId(masterVariant.getId())
                        .featureValues(objectMapper.valueToTree(item.getFeatures()))
                        .price(item.getPrice())
                        .sku(item.getSku())
                        .build())
            .collect(Collectors.toList());
    return variants;
  }

  private String getCategoryTree(ECategory category, List<ECategory> eCategories) {
    var categoryTreeBuilder = createCategoryTree(eCategories, category, new StringBuilder());

    var categoryTree = Arrays.stream(categoryTreeBuilder.toString().split(" > ")).collect(toList());
    Collections.reverse(categoryTree);

    return categoryTree.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(" > "));
  }

  public EMasterVariant findCategoryWithMasterVariant(
      List<ECategory> categories, EMasterVariant masterVariant, ECategory category) {

    if (masterVariant != null) {
      return masterVariant;
    }

    ECategory entity =
        categories.stream()
            .filter(item -> category.getId().equals(item.getId()))
            .findFirst()
            .orElseThrow();

    EMasterVariant variant = masterVariantRepository.findByCategoryId(entity.getId());
    return findCategoryWithMasterVariant(
        categories, variant, ECategory.builder().id(entity.getParentId()).build());
  }

  private StringBuilder createCategoryTree(
      List<ECategory> entities, ECategory eCategory, StringBuilder categoryTree) {

    if (null == eCategory.getId()) {
      return categoryTree;
    }

    ECategory entity =
        entities.stream()
            .filter(item -> eCategory.getId().equals(item.getId()))
            .findFirst()
            .orElseThrow();

    createCategoryTree(
        entities,
        ECategory.builder().id(entity.getParentId()).build(),
        categoryTree.append(entity.getName()).append(" > "));

    return categoryTree;
  }

  //  private void validateFeatureTemplate(AddProductRequest request, JsonNode features) {
  //    var template = featureTemplateRepository.findByCategoryId(request.getCategory());
  //
  //    // match template features with request features
  //    var templateFeatures = template.getFeatures();
  //    boolean isRequestConsistentWithTemplate =
  //        templateFeatures.fieldNames().equals(features.fieldNames());
  //
  //    if (!isRequestConsistentWithTemplate) {
  //      throw new InvalidSchemaException(
  //          HttpStatus.UNPROCESSABLE_ENTITY,
  //          ErrorResponse.builder()
  //              .code("product-invalid-schema")
  //              .message(
  //                  "Please use the correct template for features, use the GET
  // /admin/template/{category-id} API to get the template")
  //              .build());
  //    }
  //  }
}
