package com.aakbmir.expensetracker.utils;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class CommonUtils {

    @Autowired
    CategoryRepository categoryRepository;

    public static List<String> getYears() {
        return List.of(new String[]{"2023"});
    }

    public static List<String> getMonths() {
        return List.of(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    }

    public List<String> fetchDistinctSubCategories() {
        return categoryRepository.fetchDistinctSubCategories();
    }

    public List<Category> fetchAllCategories(int year, int month) {
        return categoryRepository.findAllByOrderByCategoryAsc(year, month);
    }

    public Category fetchCategoryByID(Long id) {
        List<Category> categoryList = categoryRepository.findAllByOrderByMainCategoryAscSubCategoryAscCategoryAsc();
        return categoryList.stream().filter(item -> Objects.equals(item.getId(), id)).toList().get(0);
    }

    public static String getMonthYear(Instant date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        return sdf.format(Date.from(date)); // convert here
    }
}
