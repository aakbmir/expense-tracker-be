package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Category findByCategory(String category) {
        return categoryRepository.findByCategory(category);
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

    public List<String> fetchParentCategory() {
        return categoryRepository.fetchParentCategory();
    }

    public List<String> fetchSubCategory() {
        return categoryRepository.fetchDistinctSubCategories();
    }

    public List<String> getDistinctCategories() {
        return categoryRepository.findDistinctCategories();
    }
}
