package com.aakbmir.expensetracker.usecases.budget.repository.entity;

import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "budget",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_budget_category",
                        columnNames = "category_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_budget_category")
    )
    private Category category;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal budgetAmount = new BigDecimal("0.0");

    @Column(name = "date", nullable = false)
    private Instant date;

}