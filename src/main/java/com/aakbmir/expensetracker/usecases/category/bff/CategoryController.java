package com.aakbmir.expensetracker.usecases.category.bff;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryActionRequest;
import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.builder.CategoryResponse;
import com.aakbmir.expensetracker.usecases.category.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
@Validated
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<CategoryApiDTO> saveCategory(@NotNull @Valid @RequestBody CategoryApiDTO categoryApiDTO) {

        categoryApiDTO = categoryService.saveCategoryAndBudget(categoryApiDTO);
        return new ResponseEntity<>(categoryApiDTO, HttpStatus.OK);
    }

    @PostMapping("/update-category")
    public ResponseEntity<CategoryApiDTO> updateCategory(@NotNull @Valid @RequestBody CategoryApiDTO categoryApiDTO)
            throws Exception {

        categoryApiDTO = categoryService.updateCategory(categoryApiDTO);
        return new ResponseEntity<>(categoryApiDTO, HttpStatus.OK);
    }

    @GetMapping("/add-all-categories")
    public ResponseEntity<List<CategoryApiDTO>> addAllCategories(@NotBlank @RequestParam(name = "month") String month,
                                                                 @NotBlank @RequestParam(name = "year") String year) {

        List<CategoryApiDTO> categoryApiDTOList = categoryService
                .addAllCategories(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(categoryApiDTOList, HttpStatus.OK);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<CategoryResponse> getAllCategory(@NotNull @RequestParam(name = "showInactive") Boolean showInactive,
                                                           @NotBlank @RequestParam(name = "month") String month,
                                                           @NotBlank @RequestParam(name = "year") String year) {

        CategoryResponse catList = categoryService.getAllCategoriesByMonthAndYear(showInactive,
                Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-category/{id}")
    public void deleteCategory(@NotNull @PathVariable("id") Long id, @NotNull @RequestBody CategoryActionRequest request) {
        categoryService.deleteCategory(id, request.getAction());
    }
}
