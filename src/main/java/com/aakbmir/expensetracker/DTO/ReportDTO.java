package com.aakbmir.expensetracker.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {

    private String category;

    private String parentCategory;

    private String superCategory;

    private double budget;

    private String completed;

    private  double expense;
}
