package com.aakbmir.expensetracker.DTO;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ExpenseDTO {

    private Long id;

    private String category;

    private double price;

    private Date date;

    private String note;

    private String parentCategory;

    private String superCategory;

}
