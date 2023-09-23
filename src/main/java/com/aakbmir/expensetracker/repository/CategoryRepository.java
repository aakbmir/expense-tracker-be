package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String categoryName);

    List<Category> findAllByOrderByParentAscCategoryAsc();

    List<Category> findAllByOrderByCategoryAsc();

    List<Category> findAllByOrderByParentAsc();

    /*List<Category> findByMonthYear(String concatField);*/

    @Query("SELECT DISTINCT p.parent FROM Category p order by parent asc")
    List<String> fetchParentCategory();

    @Query("SELECT p FROM Category p where p.parent=:parent order by parent asc")
    List<Category> fetchParentCategory(String parent);

    @Query("SELECT DISTINCT e.category FROM Category e order by e.category asc")
    List<String> findDistinctCategoriesValue();
}
