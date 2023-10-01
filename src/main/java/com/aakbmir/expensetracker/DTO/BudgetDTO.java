package com.aakbmir.expensetracker.DTO;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BudgetDTO {

    private Long id;

    private String category;

    private String parentCategory;

    private String superCategory;

    private double price;

    private Date date;
}
