package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.service.BudgetService;
import com.aakbmir.expensetracker.service.CategoryService;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BudgetService budgetService;

    @PostMapping("/save-category")
    public ResponseEntity saveCategory(@RequestBody Category category) {
        if(category != null && category.getCategory() != null && category.getUmbrella() != null){
            Category cat = categoryService.saveCategory(category);

            Budget budget = new Budget();
            budget.setCategory(cat.getCategory());
            budget.setDate(new Date());
            budgetService.saveBudget(budget);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message","invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save-mul-category")
    public ResponseEntity saveMulCategory(@RequestBody List<Category> categoryList) {
        for (Category cat : categoryList) {
            categoryService.saveCategory(cat);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/fetch-umbrella-category")
    public ResponseEntity fetchUmbrellaCategory() {
        List<String> catList = categoryService.fetchUmbrellaCategory();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @GetMapping("/get-category/{categoryName}")
    private ResponseEntity getCategory(@PathVariable("categoryName") String categoryName) {
        Category cat = categoryService.findByCategory(categoryName);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity getAllCategory() {
        List<Category> catList = categoryService.getAllCategories();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-category/{id}")
    private void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping("/update-category")
    public ResponseEntity updateCategory(@RequestBody Category category) {
        Optional<Category> categoryObj = categoryService.findById(category.getId());

        if (categoryObj.isPresent()) {
            Category cat = categoryObj.get();
            cat.setCategory(category.getCategory());
            cat.setUmbrella(category.getUmbrella());
            Category updateCat = categoryService.updateCategory(cat);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(category, HttpStatus.OK);
    }

}
