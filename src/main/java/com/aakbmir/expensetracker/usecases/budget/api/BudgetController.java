package com.aakbmir.expensetracker.usecases.budget.api;

import com.aakbmir.expensetracker.usecases.budget.api.dto.BudgetDTO;
import com.aakbmir.expensetracker.usecases.budget.service.BudgetService;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.category.service.CategoryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    private final CategoryService categoryService;

    @GetMapping("/add-all-budgets")
    public ResponseEntity<List<BudgetDTO>> addAllBudgets(@NotBlank @RequestParam String month,
                                                         @NotBlank @RequestParam String year) {

//        List<Category> categoryList = categoryService.getAllCategoriesByMonthAndYear(true, Integer.parseInt(year),
//                Integer.parseInt(month));
        List<BudgetDTO> budgetDTOList = budgetService.addAllBudgets(new ArrayList<>());
        return new ResponseEntity<>(budgetDTOList, HttpStatus.OK);
    }

    @GetMapping("/get-current-budget")
    public ResponseEntity<List<BudgetDTO>> getCurrentBudget(@NotBlank @RequestParam(name = "month") String month,
                                                            @NotBlank @RequestParam(name = "year") String year) {

        List<BudgetDTO> budgetsForMonth = budgetService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(budgetsForMonth, HttpStatus.OK);
    }

    @PostMapping("/update-budget")
    public ResponseEntity<BudgetDTO> updateBudget(@RequestBody BudgetDTO budgetDTO) {

        budgetService.updateBudget(budgetDTO);
        return new ResponseEntity<>(budgetDTO, HttpStatus.OK);
    }
}
