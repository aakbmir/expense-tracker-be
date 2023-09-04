package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String categoryName);

    List<Category> findAllByOrderByParentAscCategoryAsc();

    /*List<Category> findByMonthYear(String concatField);*/

    @Query("SELECT DISTINCT p.parent FROM Category p")
    List<String> fetchParentCategory();
}
