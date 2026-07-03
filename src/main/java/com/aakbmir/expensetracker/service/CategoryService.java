package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommonUtils commonUtils;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> addAllCategories(int year, int month ) {
        List<Category> categoryList = commonUtils.fetchAllCategories(year, month);
        List<Category> newCategoryList = new ArrayList<>();
        for (Category cat : categoryList) {
            Category categoryObj = Category.builder()
                    .category(cat.getCategory())
                    .categoryGroup(cat.getCategoryGroup())
                    .mainCategory(cat.getMainCategory())
                    .subCategory(cat.getSubCategory())
                    .date(Instant.now())
                    .build();
            newCategoryList.add(categoryObj);
        }
        newCategoryList = categoryRepository.saveAll(newCategoryList);
        return newCategoryList;
    }

    public Category findById(Long id) {
        return commonUtils.fetchCategoryByID(id);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategoriesByMonthAndYear(int year, int month) {
        return commonUtils.fetchAllCategories(year, month);
    }
}
