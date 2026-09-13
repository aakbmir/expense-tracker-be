package com.aakbmir.expensetracker.utils;

import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

  @Autowired CategoryRepository categoryRepository;

  public static List<String> getYears() {
    return List.of(new String[] {"2023"});
  }

  public static List<String> getMonths() {
    return List.of(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
  }

  public List<Category> fetchAllCategories(
      @NotNull boolean showAll, @NotBlank int year, @NotBlank int month) {
    if (showAll) {
      return categoryRepository.findAllByOrderByCategoryAsc(year, month);
    } else {
      return categoryRepository.findActiveByOrderByCategoryAsc(CategoryStatus.ACTIVE, year, month);
    }
  }
}
