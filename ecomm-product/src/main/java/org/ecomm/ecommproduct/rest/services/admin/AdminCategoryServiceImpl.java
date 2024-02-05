package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.persistance.entity.ECategory;
import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.persistance.repository.CategoryRepository;
import org.ecomm.ecommproduct.rest.request.admin.AddCategoryRequest;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

  @Autowired CategoryRepository categoryRepository;

  @Override
  public List<Category> getCategories() {

    List<Category> categories = new ArrayList<>();
    List<ECategory> eCategories = categoryRepository.findAll();

    // Add root nodes
    Utility.stream(eCategories)
        .filter(eCategory -> Objects.isNull(eCategory.getParentId()))
        .forEach(
            eCategory ->
                categories.add(
                    Category.builder().id(eCategory.getId()).name(eCategory.getName()).build()));

    // add child nodes
    categories.forEach(
        category ->
            addChildCategories(
                eCategories.stream()
                    .filter(eCategory -> !Objects.isNull(eCategory.getParentId()))
                    .toList(),
                category));

    return categories;
  }

  @Override
  public void addCategory(AddCategoryRequest category) {
    ECategory eCategory =
        ECategory.builder().name(category.getName()).parentId(category.getParentId()).build();

    categoryRepository.save(eCategory);
  }

  @Override
  public void deleteCategory(Integer id) {
    categoryRepository.deleteById(id);
  }

  private void addChildCategories(List<ECategory> entities, Category category) {
    for (ECategory entity : entities) {
      if (entity.getParentId().equals(category.getId())) {
        Category subCategory = Category.builder().name(entity.getName()).id(entity.getId()).build();

        List<Category> subs = category.getCategories();
        if (subs == null) {
          subs = new ArrayList<>();
        }
        subs.add(subCategory);
        category.setCategories(subs);
        addChildCategories(entities, subCategory);
      }
    }
  }
}
