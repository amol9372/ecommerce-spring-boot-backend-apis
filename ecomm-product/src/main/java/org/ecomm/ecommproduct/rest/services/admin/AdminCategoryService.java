package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.request.admin.AddCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminCategoryService {


    List<Category> getCategories();

    void addCategory(AddCategoryRequest category);

    void deleteCategory(Integer id);
}
