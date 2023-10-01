package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByParentCategoryAscSuperCategoryAscCategoryAsc();
    }

    public List<String> fetchParentCategory() {
        return categoryRepository.fetchParentCategory();
    }

    public List<String> fetchSubCategory() {
        return categoryRepository.fetchSubCategory();
    }

    public List<String> getDistinctCategories() {
        return categoryRepository.findDistinctCategoriesValue();
    }
}
