package com.aakbmir.expensetracker.utils;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommonUtils {

    @Autowired
    CategoryRepository categoryRepository;

    public static List<Category> categoryListCache = new ArrayList<>();
    public static Map<String, Category> categoryCache = new HashMap<>();

    public static List<String> getYears() {
        return List.of(new String[]{"2023"});
    }

    public static List<String> getMonths() {
        return List.of(new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
    }

    public static String toUtc(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcDateString = sdf.format(date);
        return utcDateString;
    }

    public List<String> fetchDistinctSubCategories() {
        return categoryRepository.fetchDistinctSubCategories();
    }

    public List<Category> fetchAllCategories() {
        if (categoryListCache.isEmpty()) {
            List<Category> categoryList = categoryRepository.findAllByOrderByParentCategoryAscSuperCategoryAscCategoryAsc();
            categoryListCache.addAll(categoryList);
            categoryCache.clear();
            for(Category cat: categoryList) {
                categoryCache.put(cat.getCategory(), cat);
            }
            return categoryList;
        } else {
            return categoryListCache;
        }
    }

    public Category fetchCategoryByID(Long id) {
        List<Category> categoryList = categoryListCache;
        return categoryList.stream().filter(item -> item.getId() == id).collect(Collectors.toList()).get(0);
    }

    public static String getMonthYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }
}
