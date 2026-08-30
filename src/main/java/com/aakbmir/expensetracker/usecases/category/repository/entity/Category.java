package com.aakbmir.expensetracker.usecases.category.repository.entity;

import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "category_master2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long categoryId;

    @Column(name = "category_group", nullable = false, length = 50)
    private String categoryGroup;

    @Column(name = "main_category", nullable = false, length = 50)
    private String mainCategory;

    @Column(name = "super_category", nullable = false, length = 50)
    private String superCategory;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @Column(name = "category_date", nullable = false)
    private Instant date;

    @Column(name = "financialType", nullable = false)
    @Enumerated(EnumType.STRING)
    private FinancialType financialType;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal budgetAmount;

    @Override
    public int compareTo(Category d) {
        return this.category.compareTo(d.getCategory());
    }
}
