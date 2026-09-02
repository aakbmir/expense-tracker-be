package com.aakbmir.expensetracker.usecases.savings.api;

import com.aakbmir.expensetracker.usecases.savings.api.dto.SavingsDTO;
import com.aakbmir.expensetracker.usecases.savings.service.SavingsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savings")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsService savingsService;

    @PostMapping("/save-savings")
    public ResponseEntity<?> saveSavings(@NotNull @Valid @RequestBody SavingsDTO savingsDTO) {
        savingsDTO = savingsService.saveSavings(savingsDTO);
        return new ResponseEntity<>(savingsDTO, HttpStatus.OK);
    }

//    @GetMapping("/get-savings")
//    public ResponseEntity<?> getSavings(@RequestParam(name = "savingsName") String savingsName) {
//        List<Savings> savings = savingsService.findByCategory(savingsName);
//        return new ResponseEntity<>(savings, HttpStatus.OK);
//    }

    @GetMapping("/get-current-savings")
    public ResponseEntity<?> getCurrentSavings(@RequestParam(name = "month") String month,
                                               @RequestParam(name = "year") String year) {

        List<SavingsDTO> savingssForMonth = savingsService.findByMonthAndYear(Integer.parseInt(year),
                Integer.parseInt(month));
        return new ResponseEntity<>(savingssForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-savings/{id}")
    public void deleteSavings(@PathVariable("id") Long id) {
        savingsService.deleteSavings(id);
    }

    @PostMapping("/update-savings")
    public ResponseEntity<?> updateSavings(@RequestBody SavingsDTO savingsDTO) {
        savingsService.updateSavings(savingsDTO);
        return new ResponseEntity<>(savingsDTO, HttpStatus.OK);
    }
}
