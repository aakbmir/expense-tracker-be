package com.aakbmir.expensetracker.usecases.expense.service;

import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.expense.repository.ExpenseRepository;
import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;
import com.aakbmir.expensetracker.usecases.expense.service.mapper.ExpenseMapper;
import com.aakbmir.expensetracker.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aakbmir.expensetracker.usecases.expense.service.mapper.ExpenseMapper.mapToExpense;
import static com.aakbmir.expensetracker.usecases.expense.service.mapper.ExpenseMapper.mapToExpenseDTO;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final CommonUtils commonUtils;

    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
        Category category = categoryRepository.findById(expenseDTO.categoryId()).get();
        Expense expense = mapToExpense(expenseDTO, category);
        expense = expenseRepository.save(expense);
        return mapToExpenseDTO(expense);
    }

    /*  public List<Expense> findByCategory(String expense) {
          if (expense == null || expense.equalsIgnoreCase("")) {
              return expenseRepository.findAllByCategory();
          } else {
              return expenseRepository.findCategory(expense);
          }
      }

      public Optional<Expense> findById(Long id) {
          return expenseRepository.findById(id);
      }
  */

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<ExpenseDTO> findByMonthAndYear(int year, int month) {
        List<Expense> expenseDTOList = expenseRepository.findExpenseAndCatByMonthAndYear(year, month);
        return expenseDTOList.stream()
                .map(ExpenseMapper::mapToExpenseDTO)
                .toList();
    }

    public void updateExpense(ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(expenseDTO.expenseId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        Category category = categoryRepository.findById(expenseDTO.categoryApiDTO().categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        expense.setAmount(expenseDTO.amount());
        expense.setDate(expenseDTO.date().toInstant());
        expense.setDescription(expenseDTO.description());
        if (category.getCategoryId().compareTo(expense.getCategory().getCategoryId()) != 0) {
            expense.setCategory(category);
        }
        expenseRepository.save(expense);
    }
}