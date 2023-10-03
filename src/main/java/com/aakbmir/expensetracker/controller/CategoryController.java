package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.service.BudgetService;
import com.aakbmir.expensetracker.service.CategoryService;
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
        if (category != null && category.getCategory() != null && category.getParentCategory() != null && category.getSuperCategory() != null) {
            category.setCategory(category.getCategory());
            category.setSuperCategory(category.getSuperCategory());
            category.setParentCategory(category.getParentCategory());
            Category cat = categoryService.saveCategory(category);
            saveBudget(cat);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    private void saveBudget(Category cat) {
        Budget budget = new Budget();
        budget.setCategory(cat.getCategory());
        budget.setParentCategory(cat.getParentCategory());
        budget.setSuperCategory(cat.getSuperCategory());
        budget.setDate(new Date());
        budgetService.saveBudget(budget);
    }

    private void updateBudget(Budget budgetObj, Category category) {
        Budget budget = new Budget();
        budget.setId(budgetObj.getId());
        budget.setPrice(budgetObj.getPrice());
        budget.setCategory(category.getCategory());
        budget.setParentCategory(category.getParentCategory());
        budget.setSuperCategory(category.getSuperCategory());
        budget.setDate(new Date());
        budgetService.saveBudget(budget);
    }

    @PostMapping("/save-mul-category")
    public ResponseEntity saveMulCategory(@RequestBody List<Category> categoryList) {
        for (Category cat : categoryList) {
            saveCategory(cat);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity getAllCategory() {
        List<Category> catList = categoryService.getAllCategories();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-category/{id}")
    private void deleteCategory(@PathVariable("id") Long id) {
        Category categoryObj = categoryService.findById(id);
        String categoryName = categoryObj.getCategory();
        categoryService.deleteCategory(id);
        Budget budgetObj = budgetService.findByBudget(categoryName);
        budgetService.deleteBudget(budgetObj.getId());
    }

    @PostMapping("/update-category")
    public ResponseEntity updateCategory(@RequestBody Category category) {
        Category cat = categoryService.findById(category.getId());
        if (cat != null) {
            Budget budgetObj = budgetService.findByBudget(cat.getCategory());
            cat.setSuperCategory(category.getSuperCategory());
            cat.setCategory(category.getCategory());
            cat.setParentCategory(category.getParentCategory());
            Category updateCat = categoryService.saveCategory(cat);
            updateBudget(budgetObj, category);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(category, HttpStatus.OK);
    }
}
