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
@Table(name = "category")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentCategory;

    private String superCategory;

    @Column(unique = true)
    private String category;

    @Override
    public int compareTo(Category d) {
        return this.category.compareTo(d.getCategory());
    }

}
