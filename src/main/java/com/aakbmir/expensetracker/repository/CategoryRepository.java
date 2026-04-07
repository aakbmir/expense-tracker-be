package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByMainCategoryAscSubCategoryAscCategoryAsc();

    @Query("SELECT DISTINCT p.subCategory FROM Category p order by subCategory asc")
    List<String> fetchDistinctSubCategories();

    List<Category> findAllByOrderByCategoryAsc();
}
