package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String categoryName);

    List<Category> findAllByOrderByParentCategoryAscSuperCategoryAscCategoryAsc();

    List<Category> findAllByOrderByCategoryAsc();

    List<Category> findAllByOrderByParentCategoryAsc();

    /*List<Category> findByMonthYear(String concatField);*/

    @Query("SELECT DISTINCT p.parentCategory FROM Category p order by parentCategory asc")
    List<String> fetchParentCategory();

    @Query("SELECT DISTINCT p.superCategory FROM Category p order by parentCategory asc")
    List<String> fetchSubCategory();


    @Query("SELECT p FROM Category p where p.parentCategory=:parentCategory order by parentCategory asc")
    List<Category> fetchParentCategory(String parentCategory);

    @Query("SELECT DISTINCT e.category FROM Category e order by e.category asc")
    List<String> findDistinctCategoriesValue();
}
