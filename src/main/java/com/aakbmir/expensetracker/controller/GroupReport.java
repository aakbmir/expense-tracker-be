package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.DTO.ParentCategoryDTO;
import com.aakbmir.expensetracker.entity.Income;
import lombok.Data;

import java.util.List;

@Data
public class GroupReport {

    List<Object> expenses;

    List<ParentCategoryDTO> parentCategoryDTOList;

    Income income;
}
