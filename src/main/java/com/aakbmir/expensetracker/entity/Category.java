package com.aakbmir.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@Table(name = "category_master")
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String mainCategory;

    @NotBlank
    private String subCategory;

    @NotBlank
    private String category;

    @NotBlank
    private String categoryGroup;

    @NotNull
    private Instant date;

    @Override
    public int compareTo(Category d) {
        return this.category.compareTo(d.getCategory());
    }

}
