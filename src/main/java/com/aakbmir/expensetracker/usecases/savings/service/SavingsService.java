package com.aakbmir.expensetracker.usecases.savings.service;

import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.savings.api.dto.SavingsDTO;
import com.aakbmir.expensetracker.usecases.savings.repository.SavingsRepository;
import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;
import com.aakbmir.expensetracker.usecases.savings.service.mapper.SavingsMapper;
import com.aakbmir.expensetracker.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aakbmir.expensetracker.usecases.savings.service.mapper.SavingsMapper.mapToSavings;
import static com.aakbmir.expensetracker.usecases.savings.service.mapper.SavingsMapper.mapToSavingsDTO;

@Service
@RequiredArgsConstructor
public class SavingsService {

    private final SavingsRepository savingsRepository;

    private final CategoryRepository categoryRepository;

    private final CommonUtils commonUtils;

    public SavingsDTO saveSavings(SavingsDTO savingsDTO) {
        Category category = categoryRepository.findById(savingsDTO.categoryId()).get();
        Savings savings = mapToSavings(savingsDTO, category);
        savings = savingsRepository.save(savings);
        return mapToSavingsDTO(savings);
    }

    /*  public List<Savings> findByCategory(String savings) {
          if (savings == null || savings.equalsIgnoreCase("")) {
              return savingsRepository.findAllByCategory();
          } else {
              return savingsRepository.findCategory(savings);
          }
      }

      public Optional<Savings> findById(Long id) {
          return savingsRepository.findById(id);
      }
  */

    public void deleteSavings(Long id) {
        savingsRepository.deleteById(id);
    }

    public List<SavingsDTO> findByMonthAndYear(int year, int month) {
        List<Savings> savingsDTOList = savingsRepository.findSavingsAndCatByMonthAndYear(year, month);
        return savingsDTOList.stream()
                .map(SavingsMapper::mapToSavingsDTO)
                .toList();
    }

    public void updateSavings(SavingsDTO savingsDTO) {
        Savings savings = savingsRepository.findById(savingsDTO.savingsId())
                .orElseThrow(() -> new RuntimeException("Savings not found"));

        Category category = categoryRepository.findById(savingsDTO.categoryApiDTO().categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        savings.setAmount(savingsDTO.amount());
        savings.setDate(savingsDTO.date().toInstant());
        savings.setDescription(savingsDTO.description());
        if (category.getCategoryId().compareTo(savings.getCategory().getCategoryId()) != 0) {
            savings.setCategory(category);
        }
        savingsRepository.save(savings);
    }
}