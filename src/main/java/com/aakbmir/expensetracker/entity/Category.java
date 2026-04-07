package com.aakbmir.expensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "category_master")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mainCategory;

    private String subCategory;

    @Column(unique = true)
    private String category;

    private String categoryGroup;

    @Override
    public int compareTo(Category d) {
        return this.category.compareTo(d.getCategory());
    }

}
