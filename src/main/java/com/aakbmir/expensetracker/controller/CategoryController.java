package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.DTO.CategoryMasterDTO;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.service.BudgetService;
import com.aakbmir.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
@Validated
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BudgetService budgetService;

    @GetMapping("/add-all-categories")
    public ResponseEntity<List<Category>> addAllCategories(@NotNull @RequestParam(name = "month") String month, @NotNull @RequestParam(name = "year") String year) {
        List<Category> isAdded = categoryService.addAllCategories(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(isAdded, HttpStatus.OK);
    }

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@NotNull @Valid @RequestBody Category category) {
        category = categoryService.saveCategory(category);
        saveBudget(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    private void saveBudget(Category cat) {
        Budget budget = Budget.builder()
                .category(cat.getCategory())
                .mainCategory(cat.getMainCategory())
                .subCategory(cat.getSubCategory())
                .categoryGroup(cat.getCategoryGroup())
                .date(cat.getDate())
                .build();
        budgetService.saveBudget(budget);
    }

    private void updateBudget(Budget budgetObj, Category category) {
        Budget budget = Budget.builder()
                .id(budgetObj.getId())
                .price(budgetObj.getPrice())
                .category(category.getCategory())
                .mainCategory(category.getMainCategory())
                .subCategory(category.getSubCategory())
                .categoryGroup(budgetObj.getCategoryGroup())
                .date(budgetObj.getDate())
                .build();
        budgetService.saveBudget(budget);
    }

    @PostMapping("/save-mul-category")
    public ResponseEntity<?> saveMulCategory(@NotEmpty @NotNull @RequestBody List<Category> categoryList) {
        for (Category cat : categoryList) {
            saveCategory(cat);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<?> getAllCategory(@NotNull @RequestParam(name = "month") String month, @NotNull @RequestParam(name = "year") String year) {
        List<Category> catList = categoryService.getAllCategoriesByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-category/{id}")
    public void deleteCategory(@NotNull @PathVariable("id") Long id, int year, int month) {
        categoryService.deleteCategory(id, year, month);
    }

    @PostMapping("/update-category")
    public ResponseEntity<?> updateCategory(@NotNull @Valid @RequestBody CategoryMasterDTO category) {
        Category cat = categoryService.findById(category.getId());
        if (cat != null) {
            List<Budget> budgetList = budgetService.findByBudget(category.getOldCategory());
            for (Budget budget : budgetList) {
                cat.setSubCategory(category.getSubCategory());
                cat.setCategory(category.getCategory());
                cat.setMainCategory(category.getMainCategory());
                categoryService.saveCategory(cat);
                updateBudget(budget, cat);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
