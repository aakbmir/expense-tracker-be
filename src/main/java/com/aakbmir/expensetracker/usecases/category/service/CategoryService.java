package com.aakbmir.expensetracker.usecases.category.service;

import com.aakbmir.expensetracker.usecases.budget.service.BudgetService;
import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.builder.CategoryResponse;
import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper;
import com.aakbmir.expensetracker.utils.CommonUtils;
import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategory;
import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategoryApiDTO;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final BudgetService budgetService;

    private final CommonUtils commonUtils;

    @Transactional
    public CategoryApiDTO saveCategoryAndBudget(@NotNull @Valid CategoryApiDTO categoryApiDTO) {
        categoryApiDTO = saveCategory(categoryApiDTO);
        //budgetService.saveBudgetWithCategory(categoryApiDTO);
        return categoryApiDTO;
    }

    public @NotNull @Valid CategoryApiDTO updateCategory(@NotNull @Valid CategoryApiDTO categoryApiDTO) throws Exception {
        return saveCategory(categoryApiDTO);
    }

    public CategoryApiDTO saveCategory(@NotNull @Valid CategoryApiDTO categoryApiDTO) {
        Category category = mapToCategory(categoryApiDTO);
        category = categoryRepository.save(category);
        return mapToCategoryApiDTO(category);
    }

    public List<CategoryApiDTO> addAllCategories(@NotBlank int year, @NotBlank int month) {
        List<Category> categoryList = commonUtils.fetchAllCategories(true, year, month);
        List<Category> newCategoryList = categoryList.stream()
                .map(cat -> cat.toBuilder()
                        .categoryId(null)
                        .date(Instant.now().plus(15, ChronoUnit.DAYS))
                        .build())
                .toList();
        newCategoryList = categoryRepository.saveAll(newCategoryList);
        return newCategoryList.stream()
                .map(CategoryMapper::mapToCategoryApiDTO)
                .toList();
    }

    public void deleteCategory(@NotNull Long id, @NotNull String action) {
        if (action.equalsIgnoreCase("Active")) {
            categoryRepository.updateCategoryStatus(id, CategoryStatus.ACTIVE);
        } else if (action.equalsIgnoreCase("Inactive")) {
            categoryRepository.updateCategoryStatus(id, CategoryStatus.INACTIVE);
        } else if (action.equalsIgnoreCase("Delete")) {
            categoryRepository.deleteById(id);
        }
    }

    public CategoryResponse getAllCategoriesByMonthAndYear(@NotNull boolean showInactive,
                                                           @NotBlank int year, @NotBlank int month) {
        List<Category> categories = commonUtils.fetchAllCategories(showInactive, year, month);
        if (categories.isEmpty()) {
            return CategoryResponse.builder().build();
        }
        return transformCategories(categories);
    }

    private CategoryResponse transformCategories(List<Category> categories) {

        Instant date = categories.get(0).getDate();
        return buildCategoryList(date, categories);
    }

    public CategoryResponse buildCategoryList(Instant date, List<Category> categories) {

        Map<FinancialType, Map<String, Map<String, List<Category>>>>
                grouped = categories.stream()
                .collect(Collectors.groupingBy(
                        Category::getFinancialType,
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                Category::getMainCategory,
                                LinkedHashMap::new,
                                Collectors.groupingBy(
                                        Category::getSuperCategory,
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                )
                        )
                ));

        List<CategoryResponse.FinancialTypeResponse> financialTypes =
                grouped.entrySet()
                        .stream()
                        .map(financialTypeEntry -> {

                            List<CategoryResponse.FinancialTypeResponse.MainCategoryResponse> mainCategories =
                                    financialTypeEntry.getValue()
                                            .entrySet()
                                            .stream()
                                            .map(mainCategoryEntry -> {

                                                List<CategoryResponse.FinancialTypeResponse.MainCategoryResponse.SuperCategoryResponse>
                                                        superCategories =
                                                        mainCategoryEntry.getValue()
                                                                .entrySet()
                                                                .stream()
                                                                .map(superCategoryEntry -> {

                                                                    List<Category> categoryList =
                                                                            superCategoryEntry.getValue();

                                                                    List<CategoryResponse.FinancialTypeResponse.MainCategoryResponse.SuperCategoryResponse.CategoryItemResponse>
                                                                            categoryItems =
                                                                            categoryList.stream()
                                                                                    .map(category ->
                                                                                            CategoryResponse.FinancialTypeResponse.MainCategoryResponse.SuperCategoryResponse.CategoryItemResponse.builder()
                                                                                                    .categoryGroup(category.getCategoryGroup())
                                                                                                    .categoryId(category.getCategoryId())
                                                                                                    .categoryName(category.getCategory())
                                                                                                    .status(category.getStatus())
                                                                                                    .budgetAmount(category.getBudgetAmount())
                                                                                                    .build()
                                                                                    )
                                                                                    .toList();

                                                                    BigDecimal totalBudgetAmount =
                                                                            categoryList.stream()
                                                                                    .map(Category::getBudgetAmount)
                                                                                    .filter(Objects::nonNull)
                                                                                    .reduce(
                                                                                            BigDecimal.ZERO,
                                                                                            BigDecimal::add
                                                                                    );

                                                                    return CategoryResponse.FinancialTypeResponse.MainCategoryResponse.SuperCategoryResponse.builder()
                                                                            .superCategory(superCategoryEntry.getKey())
                                                                            .categories(categoryItems)
                                                                            .totalBudgetAmount(totalBudgetAmount)
                                                                            .build();

                                                                })
                                                                .toList();

                                                return CategoryResponse.FinancialTypeResponse.MainCategoryResponse.builder()
                                                        .mainCategory(mainCategoryEntry.getKey())
                                                        .superCategories(superCategories)
                                                        .build();

                                            })
                                            .toList();

                            return CategoryResponse.FinancialTypeResponse.builder()
                                    .financialType(financialTypeEntry.getKey())
                                    .mainCategories(mainCategories)
                                    .build();

                        })
                        .toList();

        return CategoryResponse.builder()
                .date(date)
                .financialTypes(financialTypes)
                .build();
    }
}