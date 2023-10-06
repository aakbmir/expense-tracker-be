package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByParentCategoryAscSuperCategoryAscCategoryAsc();

    @Query("SELECT DISTINCT p.superCategory FROM Category p order by superCategory asc")
    List<String> fetchDistinctSubCategories();

}
