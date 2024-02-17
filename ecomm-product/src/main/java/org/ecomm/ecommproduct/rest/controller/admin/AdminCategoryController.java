package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.request.admin.AddCategoryRequest;
import org.ecomm.ecommproduct.rest.services.admin.AdminCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminCategoryController {

  @Autowired AdminCategoryService adminCategoryService;

  @GetMapping("category")
  public List<Category> getCategories() {
    return adminCategoryService.getCategories();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("admin/category")
  public void createCategory(@RequestBody AddCategoryRequest request) {
    adminCategoryService.addCategory(request);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("admin/category/{id}")
  public void deleteCategory(@PathVariable Integer id) {
    adminCategoryService.deleteCategory(id);
  }
}
