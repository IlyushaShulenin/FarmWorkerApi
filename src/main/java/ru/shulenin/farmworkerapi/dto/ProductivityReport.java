package ru.shulenin.farmworkerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductivityReport {
    protected String worker;
    protected String product;
    protected Double reportAmount;
    protected Double planAmount;
}
