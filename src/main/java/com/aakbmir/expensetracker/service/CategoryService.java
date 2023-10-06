package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommonUtils commonUtils;

    public Category saveCategory(Category category) {
        Category cat = categoryRepository.save(category);
        CommonUtils.categoryListCache.clear();
        return cat;
    }

    public Category findById(Long id) {
        return commonUtils.fetchCategoryByID(id);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        CommonUtils.categoryListCache.clear();
    }

    public List<Category> getAllCategories() {
        return commonUtils.fetchAllCategories();
    }
}
